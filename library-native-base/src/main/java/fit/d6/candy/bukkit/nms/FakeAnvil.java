package fit.d6.candy.bukkit.nms;

import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public interface FakeAnvil {

    @NotNull
    String getItemName();

    @NotNull
    InventoryView getBukkitView();

    int getContainerId();

}
