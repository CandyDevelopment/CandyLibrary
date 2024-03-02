package fit.d6.candy.bukkit.exception;

public class PlayerException extends RuntimeException {

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(Throwable e) {
        super(e);
    }

}
