package fit.d6.candy.exception;

public class PlayerException extends RuntimeException {

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(Throwable e) {
        super(e);
    }

}
