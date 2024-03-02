package fit.d6.candy.bukkit.exception;

public class ServiceNotExistsException extends RuntimeException {

    public ServiceNotExistsException(String message) {
        super(message);
    }

    public ServiceNotExistsException(Throwable e) {
        super(e);
    }

}
