package by.clever.reflection.dao.connectionpool;

import java.io.Serial;

/**
 * Class to handle exceptions in connection pool. Just as it named
 */
public class ConnectionPoolException extends Exception {

    @Serial
    private static final long serialVersionUID = 2160294707646225541L;

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionPoolException() {

    }

    public ConnectionPoolException(String message) {
        super(message);
    }
}
