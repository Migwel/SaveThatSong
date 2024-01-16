package dev.migwel.sts.domain.model;

import javax.annotation.CheckForNull;

public class ToResult {
    private final boolean success;
    private final String errorMessage;

    private ToResult(boolean success, @CheckForNull String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static ToResult success() {
        return new ToResult(true, null);
    }

    public static ToResult failure(String errorMessage) {
        return new ToResult(false, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
