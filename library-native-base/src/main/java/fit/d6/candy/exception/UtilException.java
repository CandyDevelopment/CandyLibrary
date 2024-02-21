package fit.d6.candy.exception;

public class UtilException extends RuntimeException {

    public UtilException(String message) {
        super(message);
    }

    public UtilException(Throwable e) {
        super(e);
    }

}
