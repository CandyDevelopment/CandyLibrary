package fit.d6.candy.bukkit.item;

import fit.d6.candy.api.item.ItemManager;
import fit.d6.candy.api.item.ItemService;
import fit.d6.candy.bukkit.BukkitService;
import org.jetbrains.annotations.NotNull;

public class BukkitItemService extends BukkitService implements ItemService {

    private final BukkitItemManager itemManager = new BukkitItemManager();

    @Override
    public @NotNull String getId() {
        return "item";
    }

    @Override
    public @NotNull ItemManager getItemManager() {
        return this.itemManager;
    }

}
