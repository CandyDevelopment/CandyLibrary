package fit.d6.candy.bukkit.world;

import fit.d6.candy.api.world.Environment;
import fit.d6.candy.bukkit.nms.NmsAccessor;

public class BukkitEnvironment implements Environment {

    public final static BukkitEnvironment OVERWORLD = new BukkitEnvironment(NmsAccessor.getAccessor().worldLevelStemOverworld(), NmsAccessor.getAccessor().worldLevelStem(NmsAccessor.getAccessor().worldLevelStemOverworld()));
    public final static BukkitEnvironment NETHER = new BukkitEnvironment(NmsAccessor.getAccessor().worldLevelStemNether(), NmsAccessor.getAccessor().worldLevelStem(NmsAccessor.getAccessor().worldLevelStemNether()));
    public final static BukkitEnvironment THE_END = new BukkitEnvironment(NmsAccessor.getAccessor().worldLevelStemTheEnd(), NmsAccessor.getAccessor().worldLevelStem(NmsAccessor.getAccessor().worldLevelStemTheEnd()));

    private final Object key;
    private final Object stem;

    public BukkitEnvironment(Object key, Object stem) {
        this.key = key;
        this.stem = stem;
    }

    public Object getKey() {
        return key;
    }

    public Object getStem() {
        return stem;
    }

}
