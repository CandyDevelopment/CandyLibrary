package fit.d6.candy.api.scheduler;

import fit.d6.candy.api.annotation.FoliaOnly;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Scheduler {

    boolean isAsync();

    @NotNull
    Plugin getPlugin();

    void runTask(@NotNull Consumer<ScheduledTask> consumer);

    void runTaskLater(@NotNull Consumer<ScheduledTask> consumer, long delayed);

    void runTaskTimer(@NotNull Consumer<ScheduledTask> consumer, long delayed, long period);


    @FoliaOnly
    @NotNull
    static Scheduler region(@NotNull Plugin plugin, @NotNull Chunk chunk) {
        return SchedulerService.getService().getRegionScheduler(plugin, chunk);
    }

    @FoliaOnly
    @NotNull
    static Scheduler region(@NotNull Plugin plugin, @NotNull Location location) {
        return SchedulerService.getService().getRegionScheduler(plugin, location);
    }

    @FoliaOnly
    @NotNull
    static Scheduler region(@NotNull Plugin plugin, @NotNull World world, int x, int z) {
        return SchedulerService.getService().getRegionScheduler(plugin, world, x, z);
    }

    @NotNull
    static Scheduler global(@NotNull Plugin plugin) {
        return SchedulerService.getService().getGlobalScheduler(plugin);
    }

    @NotNull
    static Scheduler async(@NotNull Plugin plugin) {
        return SchedulerService.getService().getAsyncScheduler(plugin);
    }

    @NotNull
    static Scheduler of(@NotNull Plugin plugin, @NotNull Entity entity) {
        return SchedulerService.getService().getScheduler(plugin, entity);
    }

}
