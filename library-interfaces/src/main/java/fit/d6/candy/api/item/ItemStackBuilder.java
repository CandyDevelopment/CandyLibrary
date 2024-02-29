package fit.d6.candy.api.item;

import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface ItemStackBuilder {

    @NotNull
    ItemStackBuilder type(@NotNull Material type);

    @NotNull
    ItemStackBuilder amount(int amount);

    @NotNull
    ItemStackBuilder displayName(@NotNull Component displayName);

    @NotNull
    ItemStackBuilder lore(@NotNull Component @NotNull ... lore);

    @NotNull
    ItemStackBuilder lore(@NotNull List<@NotNull Component> lore);

    @NotNull
    ItemStackBuilder addLore(@NotNull Component lore);

    @NotNull
    ItemStackBuilder itemFlag(@NotNull ItemFlag @NotNull ... flags);

    @NotNull
    ItemStackBuilder customModelData(int data);

    @NotNull
    ItemStackBuilder enchant(@NotNull Enchantment enchantment, int level, boolean force);

    @NotNull
    ItemStackBuilder attribute(@NotNull Attribute attribute, @NotNull AttributeModifier modifier);

    @NotNull
    ItemStackBuilder unbreakable(boolean unbreakable);

    @NotNull
    ItemStackBuilder destroyable(@NotNull Collection<@NotNull Namespaced> keys);

    @NotNull
    ItemStackBuilder placeable(@NotNull Collection<@NotNull Namespaced> keys);

    @NotNull
    ItemStackBuilder trim(@NotNull ArmorTrim trim);

    @NotNull
    ItemStackBuilder axolotlVariant(@NotNull Axolotl.@NotNull Variant variant);

    @NotNull
    ItemStackBuilder bannerPatterns(@NotNull List<@NotNull Pattern> patterns);

    @NotNull
    ItemStackBuilder addBannerPattern(@NotNull Pattern pattern);

    @NotNull
    ItemStackBuilder blockData(@NotNull BlockData blockData);

    @NotNull
    ItemStackBuilder blockState(@NotNull BlockState blockState);

    @NotNull
    ItemStackBuilder book(@NotNull Book book);

    @NotNull
    ItemStackBuilder bundleItems(@NotNull List<@NotNull ItemStack> items);

    @NotNull
    ItemStackBuilder addBundleItem(@NotNull ItemStack item);

    @NotNull
    ItemStackBuilder compassLodestone(@NotNull Location location);

    @NotNull
    ItemStackBuilder compassLodestoneTracked(boolean tracked);

    @NotNull
    ItemStackBuilder crossbowChargedProjectiles(@NotNull List<@NotNull ItemStack> projectiles);

    @NotNull
    ItemStackBuilder addCrossbowChargedProjectile(@NotNull ItemStack itemStack);

    @NotNull
    ItemStackBuilder damage(int damage);

    @NotNull
    ItemStackBuilder fireworkEffects(@NotNull List<@NotNull FireworkEffect> fireworkEffects);

    @NotNull
    ItemStackBuilder addFireworkEffect(@NotNull FireworkEffect fireworkEffect);

    @NotNull
    ItemStackBuilder fireworkPower(int power);

    @NotNull
    ItemStackBuilder knowledgeBookRecipes(@NotNull List<@NotNull NamespacedKey> recipes);

    @NotNull
    ItemStackBuilder addKnowledgeBookRecipe(@NotNull NamespacedKey recipe);

    @NotNull
    ItemStackBuilder leatherArmorColor(@NotNull Color color);

    @NotNull
    ItemStackBuilder mapView(@NotNull MapView mapView);

    @NotNull
    ItemStackBuilder mapScaling(boolean scaling);

    @NotNull
    ItemStackBuilder mapColor(@NotNull Color color);

    @NotNull
    ItemStackBuilder musicInstrument(@NotNull MusicInstrument instrument);

    @NotNull
    ItemStackBuilder potionType(@NotNull PotionType potionType);

    @NotNull
    ItemStackBuilder potionEffect(@NotNull PotionEffect effect, boolean overwrite);

    @NotNull
    ItemStackBuilder potionColor(@NotNull Color color);

    @NotNull
    ItemStackBuilder repairCost(int cost);

    @NotNull
    ItemStackBuilder skullOwningPlayer(@NotNull OfflinePlayer owningPlayer);

    @NotNull
    ItemStackBuilder skullPlayerProfile(@NotNull PlayerProfile playerProfile);

    @NotNull
    ItemStackBuilder skullNoteBlockSound(@NotNull NamespacedKey sound);

    @NotNull
    ItemStackBuilder spawnEggEntitySnapshot(@NotNull EntitySnapshot entitySnapshot);

    @NotNull
    ItemStackBuilder spawnEggCustomType(@NotNull EntityType entityType);

    @NotNull
    ItemStackBuilder tropicalFishPatternColor(@NotNull DyeColor color);

    @NotNull
    ItemStackBuilder tropicalFishBodyColor(@NotNull DyeColor color);

    @NotNull
    ItemStackBuilder tropicalFishPattern(@NotNull TropicalFish.@NotNull Pattern pattern);

    @NotNull
    ItemStack build();

    @NotNull
    static ItemStackBuilder of() {
        return ItemManager.getManager().createItemStackBuilder();
    }

}
