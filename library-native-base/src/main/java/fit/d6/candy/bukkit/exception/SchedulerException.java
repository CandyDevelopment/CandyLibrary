package fit.d6.candy.bukkit.exception;

public class SchedulerException extends RuntimeException {

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(Throwable e) {
        super(e);
    }

}
