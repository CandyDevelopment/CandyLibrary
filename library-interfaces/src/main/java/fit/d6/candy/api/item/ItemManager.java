package fit.d6.candy.api.item;

import fit.d6.candy.api.nbt.NbtCompound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemManager {

    @Nullable
    NbtCompound getNbt(@NotNull ItemStack itemStack);

    @NotNull
    NbtCompound getOrCreateNbt(@NotNull ItemStack itemStack);

    @NotNull
    ItemStack setNbt(@NotNull ItemStack itemStack, @NotNull NbtCompound nbt);

}
