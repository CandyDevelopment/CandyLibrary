package fit.d6.candy.api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Scheduler {

    @NotNull
    Plugin getPlugin();

    void runTask(@NotNull Consumer<ScheduledTask> consumer);

    void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed);

    void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period);

}
