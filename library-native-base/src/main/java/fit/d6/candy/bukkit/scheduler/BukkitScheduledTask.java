package fit.d6.candy.bukkit.scheduler;

import fit.d6.candy.api.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class BukkitScheduledTask implements ScheduledTask {

    private final Plugin plugin;
    private final BukkitTask bukkitTask;
    private final boolean isRepeating;

    public BukkitScheduledTask(Plugin plugin, BukkitTask bukkitTask, boolean isRepeating) {
        this.plugin = plugin;
        this.bukkitTask = bukkitTask;
        this.isRepeating = isRepeating;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void cancel() {
        this.bukkitTask.cancel();
    }

    @Override
    public boolean isCancelled() {
        return this.bukkitTask.isCancelled();
    }

    @Override
    public boolean isRepeatingTask() {
        return isRepeating;
    }

}
