package by.clever.reflection.service.exception;

import java.io.Serial;

public class ServiceValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1066757603430758825L;

    public ServiceValidationException() {
        super();
    }

    public ServiceValidationException(String message) {
        super(message);
    }

    public ServiceValidationException(Exception e) {
        super(e);
    }

    public ServiceValidationException(String message, Exception e) {
        super(message, e);
    }

}
