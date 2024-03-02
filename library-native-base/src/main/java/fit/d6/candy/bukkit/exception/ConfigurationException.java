package fit.d6.candy.bukkit.exception;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(Throwable e) {
        super(e);
    }

}
