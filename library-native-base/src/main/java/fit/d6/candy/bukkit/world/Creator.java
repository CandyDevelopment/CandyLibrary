package fit.d6.candy.bukkit.world;

import fit.d6.candy.bukkit.util.Ref;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface Creator {

    void create(Plugin plugin, WorldCreator creator, BukkitWorldInitializer initializer, Consumer<fit.d6.candy.api.world.World> consumer);

    static Creator getCreator() {
        return Ref.isFolia() ? new FoliaCreator() : new BukkitCreator();
    }

}
