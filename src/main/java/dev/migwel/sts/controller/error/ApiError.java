package dev.migwel.sts.controller.error;

import java.util.ArrayList;
import java.util.List;

public class ApiError {
    private List<FieldError> fieldErrors = new ArrayList<>();

    public ApiError() {}

    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return List.copyOf(fieldErrors);
    }
}
