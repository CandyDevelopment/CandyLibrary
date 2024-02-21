package fit.d6.candy.world;

import fit.d6.candy.util.Ref;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface Creator {

    void create(Plugin plugin, WorldCreator creator, Consumer<fit.d6.candy.api.world.World> consumer);

    static Creator getCreator() {
        return Ref.isFolia() ? new FoliaCreator() : new BukkitCreator();
    }

}
