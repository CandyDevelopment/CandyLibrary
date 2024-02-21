package fit.d6.candy.exception;

public class NbtException extends RuntimeException {

    public NbtException(String message) {
        super(message);
    }

    public NbtException(Throwable e) {
        super(e);
    }

}
