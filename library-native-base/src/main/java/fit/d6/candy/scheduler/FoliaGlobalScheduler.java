package fit.d6.candy.scheduler;

import fit.d6.candy.api.annotation.FoliaOnly;
import fit.d6.candy.api.scheduler.ScheduledTask;
import fit.d6.candy.api.scheduler.Scheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@FoliaOnly
public class FoliaGlobalScheduler implements Scheduler {

    private final Plugin plugin;
    private final GlobalRegionScheduler scheduler;

    public FoliaGlobalScheduler(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = Bukkit.getGlobalRegionScheduler();
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void runTask(@NotNull Consumer<ScheduledTask> consumer) {
        this.scheduler.run(
                this.plugin,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task))
        );
    }

    @Override
    public void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed) {
        this.scheduler.runDelayed(
                this.plugin,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task)),
                delayed
        );
    }

    @Override
    public void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period) {
        this.scheduler.runAtFixedRate(
                this.plugin,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task)),
                delayed,
                period
        );
    }

}
