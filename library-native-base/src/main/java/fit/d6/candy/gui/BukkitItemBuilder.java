package fit.d6.candy.gui;

import fit.d6.candy.api.gui.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BukkitItemBuilder implements ItemBuilder {

    private Material type;
    private Component displayName;
    private List<Component> lore;
    private boolean enchanted = false;
    private ItemStack nativeItemStack;

    @Override
    public @NotNull ItemBuilder type(@NotNull Material type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull ItemBuilder displayName(@NotNull Component component) {
        this.displayName = component;
        return this;
    }

    @Override
    public @NotNull ItemBuilder lore(Component @NotNull ... components) {
        this.lore = new ArrayList<>(Arrays.asList(components));
        return this;
    }

    @Override
    public @NotNull ItemBuilder enchanted(boolean isEnchanted) {
        this.enchanted = isEnchanted;
        return this;
    }

    @Override
    public @NotNull ItemBuilder deliverNative(ItemStack object) {
        this.nativeItemStack = object;
        return this;
    }

    public ItemStack build() {
        if (this.nativeItemStack != null)
            return this.nativeItemStack;
        if (this.type == null)
            return new ItemStack(Material.AIR);
        ItemStack itemStack = new ItemStack(Material.valueOf(this.type.name()));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.displayName(this.displayName);
            itemMeta.lore(this.lore);
            if (this.enchanted) {
                itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

}
