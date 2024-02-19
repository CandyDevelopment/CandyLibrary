package fit.d6.candy.api.gui.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemBuilder {

    @NotNull
    ItemBuilder type(@NotNull Material type);

    @NotNull
    ItemBuilder displayName(@NotNull Component component);

    @NotNull
    ItemBuilder lore(@NotNull Component @NotNull ... components);

    @NotNull
    ItemBuilder enchanted(boolean isEnchanted);

    /**
     * Directly deliver a bukkit item stack instead of using candy-gui builder
     *
     * @param object a bukkit ItemStack
     * @return self
     */
    @NotNull
    ItemBuilder deliverNative(@Nullable ItemStack object);

}
