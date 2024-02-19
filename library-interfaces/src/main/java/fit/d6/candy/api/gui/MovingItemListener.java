package fit.d6.candy.api.gui;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface MovingItemListener {

    void moving(@NotNull GuiContext context, @NotNull ItemStack movingItem);

}
