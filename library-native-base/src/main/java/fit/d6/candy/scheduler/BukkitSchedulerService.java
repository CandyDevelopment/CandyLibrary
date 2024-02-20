package fit.d6.candy.scheduler;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.scheduler.Scheduler;
import fit.d6.candy.api.scheduler.SchedulerService;
import fit.d6.candy.exception.SchedulerException;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitSchedulerService extends BukkitService implements SchedulerService {

    private final boolean isHigherThan1_20;

    public BukkitSchedulerService(boolean isHigherThan1_20) {
        this.isHigherThan1_20 = isHigherThan1_20;
    }

    @Override
    public @NotNull String getId() {
        return "scheduler";
    }

    @Override
    public @NotNull Scheduler getRegionScheduler(@NotNull Plugin plugin, @NotNull Chunk chunk) {
        if (!isHigherThan1_20)
            throw new SchedulerException("Folia Only");
        return new FoliaRegionScheduler(plugin, chunk.getWorld(), chunk.getX(), chunk.getZ());
    }

    @Override
    public @NotNull Scheduler getRegionScheduler(@NotNull Plugin plugin, @NotNull Location location) {
        return new FoliaRegionScheduler(plugin, location.getWorld(), location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    @Override
    public @NotNull Scheduler getRegionScheduler(@NotNull Plugin plugin, @NotNull World world, int x, int z) {
        return new FoliaRegionScheduler(plugin, world, x, z);
    }

    @Override
    public @NotNull Scheduler getGlobalScheduler(@NotNull Plugin plugin) {
        return isHigherThan1_20 ? new FoliaGlobalScheduler(plugin) : new BukkitScheduler(plugin);
    }

    @Override
    public @NotNull Scheduler getAsyncScheduler(@NotNull Plugin plugin) {
        return isHigherThan1_20 ? new FoliaAsyncScheduler(plugin) : new BukkitAsyncScheduler(plugin);
    }

    @Override
    public @NotNull Scheduler getScheduler(@NotNull Plugin plugin, @NotNull Player player) {
        return isHigherThan1_20 ? new FoliaScheduler(plugin, player) : new BukkitScheduler(plugin);
    }

}
