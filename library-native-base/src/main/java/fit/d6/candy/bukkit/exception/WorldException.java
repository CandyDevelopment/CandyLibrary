package fit.d6.candy.bukkit.exception;

public class WorldException extends RuntimeException {

    public WorldException(String message) {
        super(message);
    }

    public WorldException(Throwable e) {
        super(e);
    }

}
