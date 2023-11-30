package by.clever.reflection.service.exception;

public class PrintServiceException extends RuntimeException {
    private static final long serialVersionUID = 9135799603430758825L;

    public PrintServiceException() {
        super();
    }

    public PrintServiceException(String message) {
        super(message);
    }

    public PrintServiceException(Exception e) {
        super(e);
    }

    public PrintServiceException(String message, Exception e) {
        super(message, e);
    }
}
