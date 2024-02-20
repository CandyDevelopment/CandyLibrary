package fit.d6.candy.scheduler;

import fit.d6.candy.api.scheduler.ScheduledTask;
import fit.d6.candy.api.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BukkitAsyncScheduler implements Scheduler {

    private final Plugin plugin;
    private final org.bukkit.scheduler.BukkitScheduler scheduler;

    public BukkitAsyncScheduler(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = Bukkit.getScheduler();
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void runTask(@NotNull Consumer<ScheduledTask> consumer) {
        this.scheduler.runTaskAsynchronously(this.plugin, bukkitTask -> consumer.accept(new BukkitScheduledTask(
                this.plugin,
                bukkitTask,
                false
        )));
    }

    @Override
    public void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed) {
        this.scheduler.runTaskLaterAsynchronously(this.plugin, bukkitTask -> consumer.accept(new BukkitScheduledTask(
                this.plugin,
                bukkitTask,
                false
        )), delayed);
    }

    @Override
    public void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period) {
        this.scheduler.runTaskTimerAsynchronously(this.plugin, bukkitTask -> consumer.accept(new BukkitScheduledTask(
                this.plugin,
                bukkitTask,
                true
        )), delayed, period);
    }

}
