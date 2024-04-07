package fit.d6.candy.api.item;

import fit.d6.candy.api.nbt.NbtCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemManager {

    @NotNull
    ItemStack newCraftItemStack(@NotNull Material type);

    @NotNull
    ItemStackBuilder createItemStackBuilder();

    @Nullable
    NbtCompound getNbt(@NotNull ItemStack itemStack);

    @NotNull
    NbtCompound getOrCreateNbt(@NotNull ItemStack itemStack);

    @NotNull
    ItemStack setNbt(@NotNull ItemStack itemStack, @NotNull NbtCompound nbt);

    @NotNull
    static ItemManager getManager() {
        return ItemService.getService().getItemManager();
    }

}
