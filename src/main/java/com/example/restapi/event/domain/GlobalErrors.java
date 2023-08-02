package com.example.restapi.event.domain;

import org.springframework.validation.AbstractErrors;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class GlobalErrors extends AbstractErrors {
    @Override
    public String getObjectName() {
        return null;
    }

    @Override
    public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

    }

    @Override
    public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

    }

    @Override
    public void addAllErrors(Errors errors) {

    }

    @Override
    public List<ObjectError> getGlobalErrors() {
        return null;
    }

    @Override
    public List<FieldError> getFieldErrors() {
        return null;
    }

    @Override
    public Object getFieldValue(String field) {
        return null;
    }
}
