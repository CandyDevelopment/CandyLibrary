package fit.d6.candy.exception;

public class EventException extends RuntimeException {

    public EventException(String message) {
        super(message);
    }

    public EventException(Throwable e) {
        super(e);
    }

}
