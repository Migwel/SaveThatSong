package dev.migwel.sts.exception;

public class SaveFromException extends RuntimeException {
    public SaveFromException(String message) {
        super(message);
    }

    public SaveFromException(String message, Throwable cause) {
        super(message, cause);
    }
}
