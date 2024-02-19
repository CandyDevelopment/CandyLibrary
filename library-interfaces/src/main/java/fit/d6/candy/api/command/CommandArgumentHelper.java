package fit.d6.candy.api.command;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fit.d6.candy.api.item.BlockInput;
import fit.d6.candy.api.item.ItemInput;
import fit.d6.candy.api.item.ItemPredicate;
import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtCompound;
import fit.d6.candy.api.position.BlockPosition;
import fit.d6.candy.api.position.Rotation;
import fit.d6.candy.api.time.Duration;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CommandArgumentHelper {

    @NotNull
    String getString(@NotNull String name);

    boolean getBoolean(@NotNull String name);

    int getInteger(@NotNull String name);

    double getDouble(@NotNull String name);

    long getLong(@NotNull String name) throws CommandSyntaxException;

    float getFloat(@NotNull String name);

    @NotNull
    Entity getSingleEntity(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Set<Entity> getEntities(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Set<Entity> getOptionalEntities(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Player getSinglePlayer(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Set<Player> getPlayers(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Set<Player> getOptionalPlayers(@NotNull String name) throws CommandSyntaxException;

    float getAngle(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Component getComponent(@NotNull String name);

    @NotNull
    World getWorld(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Component getMessage(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    GameMode getGameMode(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    UUID getUuid(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Vector getVector(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Particle getParticle(@NotNull String name);

    @NotNull
    BlockInput getBlock(@NotNull String name);

    @NotNull
    ItemInput getItem(@NotNull String name);

    @NotNull
    ItemPredicate getItemPredicate(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    Enchantment getEnchantment(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    EntityType getEntityType(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    EntityType getSummonableEntityType(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    PotionEffectType getPotionEffectType(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    List<PlayerProfile> getPlayerProfiles(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    NbtBase getNbt(@NotNull String name);

    @NotNull
    NbtCompound getNbtCompound(@NotNull String name);

    @NotNull
    Attribute getAttribute(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    BlockPosition getBlockPosition(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    BlockPosition getSpawnableBlockPosition(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    BlockPosition getLoadedBlockPosition(@NotNull String name) throws CommandSyntaxException;

    @NotNull
    BlockPosition getLoadedBlockPosition(@NotNull World world, @NotNull String name) throws CommandSyntaxException;

    @NotNull
    Rotation getRotation(@NotNull String name);

    @NotNull
    Duration getDuration(@NotNull String name) throws CommandSyntaxException;

}
