package fit.d6.candy.scheduler;

import fit.d6.candy.api.annotation.FoliaOnly;
import fit.d6.candy.api.scheduler.ScheduledTask;
import fit.d6.candy.api.scheduler.Scheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@FoliaOnly
public class FoliaRegionScheduler implements Scheduler {

    private final Plugin plugin;
    private final RegionScheduler scheduler;
    private final World world;
    private final int x;
    private final int z;

    public FoliaRegionScheduler(Plugin plugin, World world, int x, int z) {
        this.plugin = plugin;
        this.scheduler = Bukkit.getRegionScheduler();
        this.world = world;
        this.x = x;
        this.z = z;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void runTask(@NotNull Consumer<ScheduledTask> consumer) {
        this.scheduler.run(
                this.plugin,
                this.world,
                this.x,
                this.z,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task))
        );
    }

    @Override
    public void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed) {
        this.scheduler.runDelayed(
                this.plugin,
                this.world,
                this.x,
                this.z,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task)),
                delayed
        );
    }

    @Override
    public void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period) {
        this.scheduler.runAtFixedRate(
                this.plugin,
                this.world,
                this.x,
                this.z,
                (task) -> consumer.accept(new FoliaScheduledTask(this.plugin, task)),
                delayed,
                period
        );
    }

}
