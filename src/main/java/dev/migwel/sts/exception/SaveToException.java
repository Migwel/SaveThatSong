package dev.migwel.sts.exception;

public class SaveToException extends RuntimeException {
    public SaveToException(String message) {
        super(message);
    }

    public SaveToException(String message, Throwable cause) {
        super(message, cause);
    }
}
