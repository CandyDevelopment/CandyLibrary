package fit.d6.candy.exception;

public class CollectionException extends RuntimeException {

    public CollectionException(String message) {
        super(message);
    }

    public CollectionException(Throwable e) {
        super(e);
    }

}
