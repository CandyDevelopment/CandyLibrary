package fit.d6.candy.command;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fit.d6.candy.api.command.CommandArgumentHelper;
import fit.d6.candy.api.item.BlockInput;
import fit.d6.candy.api.item.ItemInput;
import fit.d6.candy.api.item.ItemPredicate;
import fit.d6.candy.api.nbt.NbtBase;
import fit.d6.candy.api.nbt.NbtCompound;
import fit.d6.candy.api.position.BlockPosition;
import fit.d6.candy.api.position.Rotation;
import fit.d6.candy.api.time.Duration;
import fit.d6.candy.command.brigadier.DurationArgument;
import fit.d6.candy.nms.NmsAccessor;
import fit.d6.candy.position.BukkitBlockPosition;
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

public class BukkitCommandArgumentHelper implements CommandArgumentHelper {

    private final CommandContext<Object> commandContext;

    public BukkitCommandArgumentHelper(CommandContext<Object> context) {
        this.commandContext = context;
    }

    @Override
    public @NotNull String getString(@NotNull String name) {
        return StringArgumentType.getString(this.commandContext, name);
    }

    @Override
    public boolean getBoolean(@NotNull String name) {
        return BoolArgumentType.getBool(this.commandContext, name);
    }

    @Override
    public int getInteger(@NotNull String name) {
        return IntegerArgumentType.getInteger(this.commandContext, name);
    }

    @Override
    public double getDouble(@NotNull String name) {
        return DoubleArgumentType.getDouble(this.commandContext, name);
    }

    @Override
    public long getLong(@NotNull String name) {
        return LongArgumentType.getLong(this.commandContext, name);
    }

    @Override
    public float getFloat(@NotNull String name) {
        return FloatArgumentType.getFloat(this.commandContext, name);
    }

    @Override
    public @NotNull Entity getSingleEntity(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentSingleEntity(this.commandContext, name);
    }

    @Override
    public @NotNull Set<Entity> getEntities(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentEntities(this.commandContext, name);
    }

    @Override
    public @NotNull Set<Entity> getOptionalEntities(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentOptionalEntities(this.commandContext, name);
    }

    @Override
    public @NotNull Player getSinglePlayer(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentSinglePlayer(this.commandContext, name);
    }

    @Override
    public @NotNull Set<Player> getPlayers(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentPlayers(this.commandContext, name);
    }

    @Override
    public @NotNull Set<Player> getOptionalPlayers(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentOptionalPlayers(this.commandContext, name);
    }

    @Override
    public float getAngle(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentAngle(this.commandContext, name);
    }

    @Override
    public @NotNull Component getComponent(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentComponent(this.commandContext, name);
    }

    @Override
    public @NotNull World getWorld(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentWorld(this.commandContext, name);
    }

    @Override
    public @NotNull Component getMessage(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentMessage(this.commandContext, name);
    }

    @Override
    public @NotNull GameMode getGameMode(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentGameMode(this.commandContext, name);
    }

    @Override
    public @NotNull UUID getUuid(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentUuid(this.commandContext, name);
    }

    @Override
    public @NotNull Vector getVector(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentVector(this.commandContext, name);
    }

    @Override
    public @NotNull Particle getParticle(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentParticle(this.commandContext, name);
    }

    @Override
    public @NotNull BlockInput getBlock(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentBlock(this.commandContext, name);
    }

    @Override
    public @NotNull ItemInput getItem(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentItem(this.commandContext, name);
    }

    @Override
    public @NotNull ItemPredicate getItemPredicate(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentItemPredicate(this.commandContext, name);
    }

    @Override
    public @NotNull Enchantment getEnchantment(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentEnchantment(this.commandContext, name);
    }

    @Override
    public @NotNull EntityType getEntityType(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentEntityType(this.commandContext, name);
    }

    @Override
    public @NotNull EntityType getSummonableEntityType(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentSummonableEntityType(this.commandContext, name);
    }

    @Override
    public @NotNull PotionEffectType getPotionEffectType(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentPotionEffectType(this.commandContext, name);
    }

    @Override
    public @NotNull List<PlayerProfile> getPlayerProfiles(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentPlayerProfiles(this.commandContext, name);
    }

    @Override
    public @NotNull NbtBase getNbt(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentNbt(this.commandContext, name);
    }

    @Override
    public @NotNull NbtCompound getNbtCompound(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentNbtCompound(this.commandContext, name);
    }

    @Override
    public @NotNull Attribute getAttribute(@NotNull String name) throws CommandSyntaxException {
        return NmsAccessor.getAccessor().getArgumentAttribute(this.commandContext, name);
    }

    @Override
    public @NotNull BlockPosition getBlockPosition(@NotNull String name) throws CommandSyntaxException {
        return BukkitBlockPosition.fromBukkit(NmsAccessor.getAccessor().getArgumentBlockPosition(this.commandContext, name));
    }

    @Override
    public @NotNull BlockPosition getSpawnableBlockPosition(@NotNull String name) throws CommandSyntaxException {
        return BukkitBlockPosition.fromBukkit(NmsAccessor.getAccessor().getArgumentSpawnableBlockPosition(this.commandContext, name));
    }

    @Override
    public @NotNull BlockPosition getLoadedBlockPosition(@NotNull String name) throws CommandSyntaxException {
        return BukkitBlockPosition.fromBukkit(NmsAccessor.getAccessor().getArgumentLoadedBlockPosition(this.commandContext, name));
    }

    @Override
    public @NotNull BlockPosition getLoadedBlockPosition(@NotNull World world, @NotNull String name) throws CommandSyntaxException {
        return BukkitBlockPosition.fromBukkit(NmsAccessor.getAccessor().getArgumentLoadedBlockPosition(this.commandContext, world, name));
    }

    @Override
    public @NotNull Rotation getRotation(@NotNull String name) {
        return NmsAccessor.getAccessor().getArgumentRotation(this.commandContext, name);
    }

    @Override
    public @NotNull Duration getDuration(@NotNull String name) throws CommandSyntaxException {
        return DurationArgument.getDuration(this.commandContext, name);
    }

}
