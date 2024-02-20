package fit.d6.candy.api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface ScheduledTask {

    @NotNull
    Plugin getPlugin();

    void cancel();

    boolean isCancelled();

    boolean isRepeatingTask();

}
