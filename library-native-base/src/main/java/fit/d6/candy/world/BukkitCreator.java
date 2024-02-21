package fit.d6.candy.world;

import fit.d6.candy.api.scheduler.Scheduler;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class BukkitCreator implements Creator {

    @Override
    public void create(Plugin plugin, WorldCreator creator, Consumer<fit.d6.candy.api.world.World> consumer) {
        Scheduler.global(plugin)
                .runTask(scheduledTask -> {
                    consumer.accept(new BukkitWorld(creator.createWorld()));
                });
    }

}
