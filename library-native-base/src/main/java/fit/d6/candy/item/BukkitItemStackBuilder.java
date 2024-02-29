package fit.d6.candy.item;

import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import fit.d6.candy.api.item.ItemStackBuilder;
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
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BukkitItemStackBuilder implements ItemStackBuilder {

    private Material type;
    private int amount;
    private Component displayName;
    private List<Component> lore;
    private List<ItemFlag> flags;
    private Integer customModelData;
    private Boolean unbreakable;
    private Collection<Namespaced> destroyable;
    private Collection<Namespaced> placeable;
    private final Map<Enchantment, Map.Entry<Integer, Boolean>> enchantments = new LinkedHashMap<>();
    private final Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
    private ArmorTrim trim;
    private Axolotl.Variant axolotlVariant;
    private List<Pattern> bannerPatterns;
    private BlockData blockData;
    private BlockState blockState;
    private Book book;
    private List<ItemStack> bundleItems;
    private Location compassLodestone;
    private Boolean compassLodestoneTracked;
    private List<ItemStack> crossbowChargedProjectiles;
    private Integer damage;
    private List<FireworkEffect> fireworkEffects;
    private Integer fireworkPower;
    private List<NamespacedKey> knowledgeBookRecipes;
    private Color leatherArmorColor;
    private MapView mapView;
    private Boolean mapScaling;
    private Color mapColor;
    private MusicInstrument musicInstrument;
    private PotionType potionType;
    private final Map<PotionEffect, Boolean> potionEffects = new HashMap<>();
    private Color potionColor;
    private Integer repairCost;
    private OfflinePlayer skullOwningPlayer;
    private PlayerProfile skullPlayerProfile;
    private NamespacedKey skullNoteBlockSound;
    private EntitySnapshot entitySnapshot;
    private EntityType entityType;
    private DyeColor tropicalFishPatternColor;
    private DyeColor tropicalFishBodyColor;
    private TropicalFish.Pattern tropicalFishPattern;

    @Override
    public @NotNull ItemStackBuilder type(@NotNull Material type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder displayName(@NotNull Component displayName) {
        this.displayName = displayName;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder lore(@NotNull Component @NotNull ... lore) {
        this.lore = new ArrayList<>(List.of(lore));
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder lore(@NotNull List<@NotNull Component> lore) {
        this.lore = lore;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder addLore(@NotNull Component lore) {
        if (this.lore == null)
            this.lore = new ArrayList<>();
        this.lore.add(lore);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder itemFlag(@NotNull ItemFlag @NotNull ... flags) {
        if (this.flags == null)
            this.flags = new ArrayList<>();
        this.flags.addAll(List.of(flags));
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder customModelData(int data) {
        this.customModelData = data;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder enchant(@NotNull Enchantment enchantment, int level, boolean force) {
        this.enchantments.put(enchantment, new AbstractMap.SimpleEntry<>(level, force));
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder attribute(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        this.attributes.put(attribute, modifier);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder destroyable(@NotNull Collection<@NotNull Namespaced> keys) {
        this.destroyable = keys;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder placeable(@NotNull Collection<@NotNull Namespaced> keys) {
        this.placeable = keys;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder trim(@NotNull ArmorTrim trim) {
        this.trim = trim;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder axolotlVariant(@NotNull Axolotl.@NotNull Variant variant) {
        this.axolotlVariant = variant;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder bannerPatterns(@NotNull List<@NotNull Pattern> patterns) {
        this.bannerPatterns = patterns;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder addBannerPattern(@NotNull Pattern pattern) {
        if (this.bannerPatterns == null)
            this.bannerPatterns = new ArrayList<>();
        this.bannerPatterns.add(pattern);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder blockData(@NotNull BlockData blockData) {
        this.blockData = blockData;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder blockState(@NotNull BlockState blockState) {
        this.blockState = blockState;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder book(@NotNull Book book) {
        this.book = book;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder bundleItems(@NotNull List<@NotNull ItemStack> items) {
        this.bundleItems = items;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder addBundleItem(@NotNull ItemStack item) {
        if (this.bundleItems == null)
            this.bundleItems = new ArrayList<>();
        this.bundleItems.add(item);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder compassLodestone(@NotNull Location location) {
        this.compassLodestone = location;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder compassLodestoneTracked(boolean tracked) {
        this.compassLodestoneTracked = tracked;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder crossbowChargedProjectiles(@NotNull List<@NotNull ItemStack> projectiles) {
        this.crossbowChargedProjectiles = projectiles;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder addCrossbowChargedProjectile(@NotNull ItemStack itemStack) {
        if (this.crossbowChargedProjectiles == null)
            this.crossbowChargedProjectiles = new ArrayList<>();
        this.crossbowChargedProjectiles.add(itemStack);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder damage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder fireworkEffects(@NotNull List<@NotNull FireworkEffect> fireworkEffects) {
        this.fireworkEffects = fireworkEffects;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder addFireworkEffect(@NotNull FireworkEffect fireworkEffect) {
        if (this.fireworkEffects == null)
            this.fireworkEffects = new ArrayList<>();
        this.fireworkEffects.add(fireworkEffect);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder fireworkPower(int power) {
        this.fireworkPower = power;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder knowledgeBookRecipes(@NotNull List<@NotNull NamespacedKey> recipes) {
        this.knowledgeBookRecipes = recipes;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder addKnowledgeBookRecipe(@NotNull NamespacedKey recipe) {
        if (this.knowledgeBookRecipes == null)
            this.knowledgeBookRecipes = new ArrayList<>();
        this.knowledgeBookRecipes.add(recipe);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder leatherArmorColor(@NotNull Color color) {
        this.leatherArmorColor = color;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder mapView(@NotNull MapView mapView) {
        this.mapView = mapView;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder mapScaling(boolean scaling) {
        this.mapScaling = scaling;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder mapColor(@NotNull Color color) {
        this.mapColor = color;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder musicInstrument(@NotNull MusicInstrument instrument) {
        this.musicInstrument = instrument;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder potionType(@NotNull PotionType potionType) {
        this.potionType = potionType;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder potionEffect(@NotNull PotionEffect effect, boolean overwrite) {
        this.potionEffects.put(effect, overwrite);
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder potionColor(@NotNull Color color) {
        this.potionColor = color;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder repairCost(int cost) {
        this.repairCost = cost;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder skullOwningPlayer(@NotNull OfflinePlayer owningPlayer) {
        this.skullOwningPlayer = owningPlayer;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder skullPlayerProfile(@NotNull PlayerProfile playerProfile) {
        this.skullPlayerProfile = playerProfile;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder skullNoteBlockSound(@NotNull NamespacedKey sound) {
        this.skullNoteBlockSound = sound;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder spawnEggEntitySnapshot(@NotNull EntitySnapshot entitySnapshot) {
        this.entitySnapshot = entitySnapshot;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder spawnEggCustomType(@NotNull EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder tropicalFishPatternColor(@NotNull DyeColor color) {
        this.tropicalFishPatternColor = color;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder tropicalFishBodyColor(@NotNull DyeColor color) {
        this.tropicalFishBodyColor = color;
        return this;
    }

    @Override
    public @NotNull ItemStackBuilder tropicalFishPattern(@NotNull TropicalFish.@NotNull Pattern pattern) {
        this.tropicalFishPattern = pattern;
        return this;
    }

    @Override
    public @NotNull ItemStack build() {
        ItemStack itemStack = new ItemStack(this.type);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack.setAmount(this.amount);

        if (itemMeta != null) {

            if (this.book != null && itemMeta instanceof BookMeta bookMeta) {
                itemMeta = bookMeta.toBuilder()
                        .author(this.book.author())
                        .title(this.book.title())
                        .pages(this.book.pages())
                        .build();
            }

            if (this.displayName != null)
                itemMeta.displayName(this.displayName);
            if (this.lore != null)
                itemMeta.lore(this.lore);
            if (this.flags != null)
                itemMeta.addItemFlags(this.flags.toArray(new ItemFlag[0]));
            if (this.customModelData != null)
                itemMeta.setCustomModelData(this.customModelData);
            if (this.unbreakable != null)
                itemMeta.setUnbreakable(this.unbreakable);
            if (this.destroyable != null)
                itemMeta.setPlaceableKeys(this.destroyable);
            if (this.placeable != null)
                itemMeta.setPlaceableKeys(this.placeable);

            if (itemMeta instanceof EnchantmentStorageMeta enchantmentStorageMeta) {
                for (Map.Entry<Enchantment, Map.Entry<Integer, Boolean>> entry : this.enchantments.entrySet()) {
                    enchantmentStorageMeta.addStoredEnchant(entry.getKey(), entry.getValue().getKey(), entry.getValue().getValue());
                }
            } else {
                for (Map.Entry<Enchantment, Map.Entry<Integer, Boolean>> entry : this.enchantments.entrySet()) {
                    itemMeta.addEnchant(entry.getKey(), entry.getValue().getKey(), entry.getValue().getValue());
                }
            }

            itemMeta.setAttributeModifiers(this.attributes);

            if (this.trim != null && itemMeta instanceof ArmorMeta armorMeta) {
                armorMeta.setTrim(this.trim);
            }

            if (this.axolotlVariant != null && itemMeta instanceof AxolotlBucketMeta axolotlBucketMeta) {
                axolotlBucketMeta.setVariant(this.axolotlVariant);
            }

            if (this.bannerPatterns != null && itemMeta instanceof BannerMeta bannerMeta) {
                bannerMeta.setPatterns(this.bannerPatterns);
            }

            if (this.blockData != null && itemMeta instanceof BlockDataMeta blockDataMeta) {
                blockDataMeta.setBlockData(this.blockData);
            }

            if (this.blockState != null && itemMeta instanceof BlockStateMeta blockStateMeta) {
                blockStateMeta.setBlockState(this.blockState);
            }

            if (this.bundleItems != null && itemMeta instanceof BundleMeta bundleMeta) {
                bundleMeta.setItems(this.bundleItems);
            }

            if (itemMeta instanceof CompassMeta compassMeta) {
                if (this.compassLodestone != null) {
                    compassMeta.setLodestone(this.compassLodestone);
                }

                if (this.compassLodestoneTracked != null) {
                    compassMeta.setLodestoneTracked(this.compassLodestoneTracked);
                }
            }

            if (this.crossbowChargedProjectiles != null && itemMeta instanceof CrossbowMeta crossbowMeta) {
                crossbowMeta.setChargedProjectiles(this.crossbowChargedProjectiles);
            }

            if (this.damage != null && itemMeta instanceof Damageable damageable) {
                damageable.setDamage(this.damage);
            }

            if (this.fireworkEffects != null && !this.fireworkEffects.isEmpty() && itemMeta instanceof FireworkEffectMeta fireworkEffectMeta) {
                fireworkEffectMeta.setEffect(this.fireworkEffects.get(0));
            }

            if (itemMeta instanceof FireworkMeta fireworkMeta) {
                if (this.fireworkEffects != null) {
                    fireworkMeta.addEffects(this.fireworkEffects);
                }
                if (this.fireworkPower != null) {
                    fireworkMeta.setPower(this.fireworkPower);
                }
            }

            if (this.knowledgeBookRecipes != null && itemMeta instanceof KnowledgeBookMeta knowledgeBookMeta) {
                knowledgeBookMeta.setRecipes(this.knowledgeBookRecipes);
            }

            if (this.leatherArmorColor != null && itemMeta instanceof LeatherArmorMeta leatherArmorMeta) {
                leatherArmorMeta.setColor(this.leatherArmorColor);
            }

            if (itemMeta instanceof MapMeta mapMeta) {
                if (this.mapView != null) {
                    mapMeta.setMapView(mapView);
                }
                if (this.mapScaling != null) {
                    mapMeta.setScaling(this.mapScaling);
                }
                if (this.mapColor != null) {
                    mapMeta.setColor(this.mapColor);
                }
            }

            if (this.musicInstrument != null && itemMeta instanceof MusicInstrumentMeta musicInstrumentMeta) {
                musicInstrumentMeta.setInstrument(this.musicInstrument);
            }

            if (itemMeta instanceof PotionMeta potionMeta) {

                if (this.potionType != null) {
                    potionMeta.setBasePotionType(this.potionType);
                }

                for (Map.Entry<PotionEffect, Boolean> entry : this.potionEffects.entrySet()) {
                    potionMeta.addCustomEffect(entry.getKey(), entry.getValue());
                }

                if (this.potionColor != null) {
                    potionMeta.setColor(this.potionColor);
                }

            }

            if (this.repairCost != null && itemMeta instanceof Repairable repairable) {
                repairable.setRepairCost(this.repairCost);
            }

            if (itemMeta instanceof SkullMeta skullMeta) {
                if (this.skullOwningPlayer != null) {
                    skullMeta.setOwningPlayer(this.skullOwningPlayer);
                }
                if (this.skullPlayerProfile != null) {
                    skullMeta.setPlayerProfile(this.skullPlayerProfile);
                }
                if (this.skullNoteBlockSound != null) {
                    skullMeta.setNoteBlockSound(this.skullNoteBlockSound);
                }
            }

            if (itemMeta instanceof SpawnEggMeta spawnEggMeta) {

                if (this.entitySnapshot != null) {
                    spawnEggMeta.setSpawnedEntity(this.entitySnapshot);
                }

                if (this.entityType != null) {
                    spawnEggMeta.setCustomSpawnedType(this.entityType);
                }

            }

            if (!this.potionEffects.isEmpty() && itemMeta instanceof SuspiciousStewMeta suspiciousStewMeta) {
                for (Map.Entry<PotionEffect, Boolean> entry : this.potionEffects.entrySet()) {
                    suspiciousStewMeta.addCustomEffect(entry.getKey(), entry.getValue());
                }
            }

            if (itemMeta instanceof TropicalFishBucketMeta tropicalFishBucketMeta) {
                if (this.tropicalFishPatternColor != null) {
                    tropicalFishBucketMeta.setPatternColor(this.tropicalFishPatternColor);
                }
                if (this.tropicalFishBodyColor != null) {
                    tropicalFishBucketMeta.setBodyColor(this.tropicalFishBodyColor);
                }
                if (this.tropicalFishPattern != null) {
                    tropicalFishBucketMeta.setPattern(this.tropicalFishPattern);
                }
            }

            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

}
