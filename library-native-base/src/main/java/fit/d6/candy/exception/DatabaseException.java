package fit.d6.candy.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable e) {
        super(e);
    }

}
