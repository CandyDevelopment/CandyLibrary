package fit.d6.candy.exception;

public class WorldException extends RuntimeException {

    public WorldException(String message) {
        super(message);
    }

    public WorldException(Throwable e) {
        super(e);
    }

}
