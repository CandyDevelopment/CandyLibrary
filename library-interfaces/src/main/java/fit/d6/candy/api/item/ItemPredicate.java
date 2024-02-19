package fit.d6.candy.api.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemPredicate {

    boolean check(@NotNull ItemStack itemStack);

}
