package fit.d6.candy.bukkit.exception;

public class GuiException extends RuntimeException {

    public GuiException(String message) {
        super(message);
    }

    public GuiException(Throwable e) {
        super(e);
    }

}
