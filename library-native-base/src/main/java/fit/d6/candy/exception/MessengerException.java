package fit.d6.candy.exception;

public class MessengerException extends RuntimeException {

    public MessengerException(String message) {
        super(message);
    }

    public MessengerException(Throwable e) {
        super(e);
    }

}
