package fit.d6.candy.bukkit.scheduler;

import fit.d6.candy.api.annotation.FoliaOnly;
import fit.d6.candy.api.scheduler.ScheduledTask;
import fit.d6.candy.api.scheduler.Scheduler;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@FoliaOnly
public class FoliaAsyncScheduler implements Scheduler {

    private final Plugin plugin;
    private final AsyncScheduler scheduler;

    public FoliaAsyncScheduler(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = Bukkit.getAsyncScheduler();
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
        this.scheduler.runNow(
                this.plugin,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task))
        );
    }

    @Override
    public void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed) {
        this.scheduler.runDelayed(
                this.plugin,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task)),
                delayed,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period) {
        this.scheduler.runAtFixedRate(
                this.plugin,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task)),
                delayed,
                period,
                TimeUnit.MILLISECONDS
        );
    }

}
