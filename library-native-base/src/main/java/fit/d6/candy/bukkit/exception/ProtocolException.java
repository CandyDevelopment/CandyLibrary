package fit.d6.candy.bukkit.exception;

public class ProtocolException extends RuntimeException {

    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(Throwable e) {
        super(e);
    }

}
