package fit.d6.candy.bukkit.nms.v1_18_R1.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fit.d6.candy.api.item.ItemInput;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BukkitItemInput implements ItemInput {

    private final net.minecraft.commands.arguments.item.ItemInput nmsInput;

    public BukkitItemInput(net.minecraft.commands.arguments.item.ItemInput nms) {
        this.nmsInput = nms;
    }

    @Override
    public boolean test(@NotNull ItemStack itemStack) {
        return this.nmsInput.test(CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public @NotNull Material getType() {
        return CraftMagicNumbers.getMaterial(this.nmsInput.getItem());
    }

    @Override
    public @NotNull ItemStack createItemStack(int amount, boolean checkOverstack) throws CommandSyntaxException {
        return CraftItemStack.asBukkitCopy(this.nmsInput.createItemStack(amount, checkOverstack));
    }

}
