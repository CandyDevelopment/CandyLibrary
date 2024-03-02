package fit.d6.candy.bukkit.scheduler;

import fit.d6.candy.api.scheduler.ScheduledTask;
import fit.d6.candy.api.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BukkitScheduler implements Scheduler {

    private final Plugin plugin;
    private final org.bukkit.scheduler.BukkitScheduler scheduler;

    public BukkitScheduler(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = Bukkit.getScheduler();
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void runTask(@NotNull Consumer<ScheduledTask> consumer) {
        this.scheduler.runTask(this.plugin, bukkitTask -> consumer.accept(new BukkitScheduledTask(
                this.plugin,
                bukkitTask,
                false
        )));
    }

    @Override
    public void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed) {
        this.scheduler.runTaskLater(this.plugin, bukkitTask -> consumer.accept(new BukkitScheduledTask(
                this.plugin,
                bukkitTask,
                false
        )), delayed);
    }

    @Override
    public void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period) {
        this.scheduler.runTaskTimer(this.plugin, bukkitTask -> consumer.accept(new BukkitScheduledTask(
                this.plugin,
                bukkitTask,
                true
        )), delayed, period);
    }

}
