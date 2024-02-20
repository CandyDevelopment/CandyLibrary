package fit.d6.candy.scheduler;

import fit.d6.candy.api.annotation.FoliaOnly;
import fit.d6.candy.api.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@FoliaOnly
public class FoliaScheduledTask implements ScheduledTask {

    private final Plugin plugin;

    private final io.papermc.paper.threadedregions.scheduler.ScheduledTask scheduledTask;

    public FoliaScheduledTask(Plugin plugin, io.papermc.paper.threadedregions.scheduler.ScheduledTask scheduledTask) {
        this.plugin = plugin;
        this.scheduledTask = scheduledTask;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void cancel() {
        this.scheduledTask.cancel();
    }

    @Override
    public boolean isCancelled() {
        return this.scheduledTask.isCancelled();
    }

    @Override
    public boolean isRepeatingTask() {
        return this.scheduledTask.isRepeatingTask();
    }

}
