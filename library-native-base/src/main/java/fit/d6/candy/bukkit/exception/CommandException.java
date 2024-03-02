package fit.d6.candy.bukkit.exception;

public class CommandException extends RuntimeException {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable e) {
        super(e);
    }

}
