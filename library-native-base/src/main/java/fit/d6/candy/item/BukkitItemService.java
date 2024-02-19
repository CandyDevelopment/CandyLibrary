package fit.d6.candy.item;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.item.ItemManager;
import fit.d6.candy.api.item.ItemService;
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
