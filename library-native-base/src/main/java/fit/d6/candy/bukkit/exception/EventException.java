package fit.d6.candy.bukkit.exception;

public class EventException extends RuntimeException {

    public EventException(String message) {
        super(message);
    }

    public EventException(Throwable e) {
        super(e);
    }

}
