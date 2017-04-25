package com.rentIT.validation;

public interface Validator<T> {
    boolean isValid(T objectToValidate);
}
