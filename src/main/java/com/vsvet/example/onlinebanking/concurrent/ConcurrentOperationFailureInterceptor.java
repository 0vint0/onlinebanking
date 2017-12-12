package com.vsvet.example.onlinebanking.concurrent;

import java.util.Arrays;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Advice that traps exceptions out of annotated calls and retries the call if appropriate.
 *
 * @author vsvet
 */
@Aspect
@Component
public class ConcurrentOperationFailureInterceptor implements Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentOperationFailureInterceptor.class);

    private static final int DEFAULT_MAX_RETRIES = 2;

    private int maxRetries = DEFAULT_MAX_RETRIES;

    private int order = 1;

    /**
     * Advice that traps an exception specified by an annotation so that the operation can be retried.
     *
     * @param pjp                      wrapper around method being executed
     * @param retryConcurrentOperation annotation indicating method should be wrapped
     * @return return value of wrapped call
     * @throws Exception if retries exceed maximum, rethrows exception configured in RetryConcurrentOperation annotation
     * @throws Throwable any other things the wrapped call throws will pass through
     */
    @Around("@annotation(retryConcurrentOperation)")
    public Object performOperation(ProceedingJoinPoint pjp, RetryConcurrentOperation retryConcurrentOperation) throws Throwable {
        Class<?>[] exceptionClasses = retryConcurrentOperation.exception();
        int retries = getNumberOfRetries(retryConcurrentOperation);
        int numAttempts = 0;
        do {
            numAttempts++;
            try {
                LOG.debug("Attempting operation with potential for {} with maximum {} retries", exceptionClasses, retries);
                return pjp.proceed();
            } catch (Exception ex) {
                handleException(exceptionClasses, ex, numAttempts, retries);
            }
        } while (numAttempts <= retries);
        // this will never execute - we will have either successfully returned or rethrown an exception
        return null;
    }

    private int getNumberOfRetries(RetryConcurrentOperation retryConcurrentOperation) {
        int retries = retryConcurrentOperation.retries();
        if (retries <= 0) {
            return maxRetries;
        } else {
            return retries;
        }
    }

    private void handleException(Class<?>[] exceptionClasses, Exception ex, int numAttempts, int retries) throws Exception {
        // if the exception is not what we're looking for, pass it through
        LOG.debug("Caught next exception {}", ex.getClass(), ex);
        Optional<Class<?>> exceptionClass = getInstance(exceptionClasses, ex);
        if (!exceptionClass.isPresent()) {
            throw ex;
        }
        // we caught the configured exception, retry unless we've reached the maximum
        if (numAttempts > retries) {
            LOG.warn("Caught {} and exceeded maximum retries ({}), rethrowing.", exceptionClass.get().getCanonicalName(), retries);
            throw ex;
        }
        LOG.debug("Caught {} and will retry, attempts: {}", exceptionClass.get().getCanonicalName(), numAttempts);
    }

    private Optional<Class<?>> getInstance(Class<?>[] exceptionClasses, Exception ex) {
        return Arrays.stream(exceptionClasses).filter(e -> e.isInstance(ex)).findAny();
    }

    @Override
    public int getOrder() {
        return order;
    }

    /**
     * Allow overriding of the default order.
     *
     * @param order aspect order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Allow overriding of the default maximum number of retries.
     *
     * @param maxRetries maximum number of retries
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
}
