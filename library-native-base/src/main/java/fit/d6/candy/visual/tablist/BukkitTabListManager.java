package fit.d6.candy.visual.tablist;

import fit.d6.candy.api.visual.tablist.TabList;
import fit.d6.candy.api.visual.tablist.TabListContent;
import fit.d6.candy.api.visual.tablist.TabListManager;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BukkitTabListManager implements TabListManager {

    private final static List<UUID> REGISTERED_UUIDS = new ArrayList<>();

    @Override
    public @NotNull TabList createTabList() {
        return new BukkitTabList();
    }

    @Override
    public @NotNull TabListContent createTabListContent(@NotNull Component text) {
        return new BukkitTabListContent(generate(), text);
    }

    private static UUID generate() {
        UUID uuid = UUID.randomUUID();
        while (REGISTERED_UUIDS.contains(uuid))
            uuid = UUID.randomUUID();
        REGISTERED_UUIDS.add(uuid);
        return uuid;
    }

}
