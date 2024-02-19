package fit.d6.candy.item;

import fit.d6.candy.api.item.ItemManager;
import fit.d6.candy.api.nbt.NbtCompound;
import fit.d6.candy.nms.NmsAccessor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitItemManager implements ItemManager {

    @Override
    public @Nullable NbtCompound getNbt(@NotNull ItemStack itemStack) {
        return NmsAccessor.getAccessor().getNbt(itemStack);
    }

    @Override
    public @NotNull NbtCompound getOrCreateNbt(@NotNull ItemStack itemStack) {
        return NmsAccessor.getAccessor().getOrCreateNbt(itemStack);
    }

    @Override
    public @NotNull ItemStack setNbt(@NotNull ItemStack itemStack, @NotNull NbtCompound nbt) {
        return NmsAccessor.getAccessor().setNbt(itemStack, nbt);
    }

}
