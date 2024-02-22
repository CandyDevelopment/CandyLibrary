package fit.d6.candy.world;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.scheduler.Scheduler;
import fit.d6.candy.nms.NmsAccessor;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class BukkitCreator implements Creator {

    @Override
    public void create(Plugin plugin, WorldCreator creator, BukkitWorldInitializer initializer, Consumer<fit.d6.candy.api.world.World> consumer) {
        Scheduler.global(plugin)
                .runTask(scheduledTask -> consumer.accept(new BukkitWorld(CandyLibrary.version().isWorldEnvironemtnSupport() ? NmsAccessor.getAccessor().createBukkitWorld(creator, initializer) : creator.createWorld())));
    }

}
