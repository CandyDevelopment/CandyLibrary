package fit.d6.candy.api.scheduler;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.annotation.FoliaOnly;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface SchedulerService extends Service {

    @FoliaOnly
    @NotNull
    Scheduler getRegionScheduler(@NotNull Plugin plugin, @NotNull Chunk chunk);

    @FoliaOnly
    @NotNull
    Scheduler getRegionScheduler(@NotNull Plugin plugin, @NotNull Location location);

    @FoliaOnly
    @NotNull
    Scheduler getRegionScheduler(@NotNull Plugin plugin, @NotNull World world, int x, int z);

    @NotNull
    Scheduler getGlobalScheduler(@NotNull Plugin plugin);

    @NotNull
    Scheduler getAsyncScheduler(@NotNull Plugin plugin);

    @NotNull
    Scheduler getScheduler(@NotNull Plugin plugin, @NotNull Entity entity);

    @NotNull
    static SchedulerService getService() {
        return CandyLibrary.getLibrary().getService(SchedulerService.class);
    }

}
