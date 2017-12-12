package com.vsvet.example.onlinebanking.exception;

import com.vsvet.example.onlinebanking.view.ExceptionResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    private static final String GENERIC_ERROR = "Generic error please contact customer service.";

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<ExceptionResponseView> handleConstraintViolationException(ConstraintViolationException constraintException) {
        ExceptionResponseView view = constraintException.getConstraintViolations()
                .stream()
                .peek(this::log)
                .map(ConstraintViolation::getMessage)
                .reduce(new ExceptionResponseView(), (v, error) -> v.add(error), (v1, v2) -> v1);//it's not run in parallel stream, so it's ok to return first
        return new ResponseEntity<>(view, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<ExceptionResponseView> handleConstraintViolationException(ValidationException validationException) {
        LOGGER.error(GENERIC_ERROR, validationException);
        return new ResponseEntity<>(new ExceptionResponseView().add(validationException.getMessage()), HttpStatus.PRECONDITION_FAILED);
    }


    private void log(ConstraintViolation constraintViolation) {
        LOGGER.debug("{}: {}", constraintViolation.getMessage(), constraintViolation.getPropertyPath());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponseView> handleException(Exception e) {
        LOGGER.error(GENERIC_ERROR, e);
        return new ResponseEntity<>(new ExceptionResponseView().add(GENERIC_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ExceptionResponseView> handleNotFoundException(EntityNotFoundException e) {
        LOGGER.error(GENERIC_ERROR, e);
        return new ResponseEntity<>(new ExceptionResponseView().add(e.getLocalizedMessage()), HttpStatus.NOT_FOUND);

    }

}
