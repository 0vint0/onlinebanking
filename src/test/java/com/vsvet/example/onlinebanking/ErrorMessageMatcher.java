package com.vsvet.example.onlinebanking;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;


public final class ErrorMessageMatcher
        extends TypeSafeMatcher<ConstraintViolationException> {

    private final Class<? extends ConstraintViolationException> type;

    private final String errorMessage;

    private List<String> actualErrors;

    public static ErrorMessageMatcher matches(String errorMessage) {
        return new ErrorMessageMatcher(errorMessage);
    }

    private ErrorMessageMatcher(String errorMessage) {
        type = ConstraintViolationException.class;
        this.errorMessage = errorMessage;
    }

    private List<String> getErrorMessages(ConstraintViolationException item) {
        return item.getConstraintViolations()
                .stream()
                .map(e -> e.getMessage())
                .collect(Collectors.toList());
    }

    @Override
    protected boolean matchesSafely(ConstraintViolationException item) {
        actualErrors = getErrorMessages(item);
        return item.getClass().isAssignableFrom(type)
                && actualErrors.contains(errorMessage);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Expect type ").appendValue(type).appendText(" and a error message: '")
                .appendValue(errorMessage)
                .appendText("', but got next errors: " + actualErrors);
    }

}
