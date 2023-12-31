package dev.migwel.sts.controller.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ValidationErrorHandler {

    private static final Logger logger = LogManager.getLogger(ValidationErrorHandler.class);

    private final MessageSource messageSource;

    @Autowired
    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError argumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ApiError apiError = new ApiError();
        for (FieldError fieldError : fieldErrors) {
            apiError.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return apiError;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError messageNotReadableException(HttpMessageNotReadableException ex) {
        ApiError apiError = new ApiError();
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidTypeIdException invalidTypeIdException) {
            List<JsonMappingException.Reference> path = invalidTypeIdException.getPath();
            if (path != null && !path.isEmpty()) {
                apiError.addFieldError(
                        path.getFirst().getFieldName() + ".type", "Invalid type provided");
            }
        } else {
            apiError.addFieldError("", "Message could not be read");
        }
        logger.warn("Message could not be read. Reason: " + ex.getMessage());
        return apiError;
    }
}
