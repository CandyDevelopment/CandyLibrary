package fit.d6.candy.api.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemInput {

    boolean test(@NotNull ItemStack itemStack);

    @NotNull
    Material getType();

    @NotNull
    ItemStack createItemStack(int amount, boolean checkOverstack) throws CommandSyntaxException;

}
