package fit.d6.candy.command;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.ArgumentType;
import fit.d6.candy.command.argument.*;
import org.jetbrains.annotations.NotNull;

public class BukkitArgumentManager implements ArgumentManager {

    @Override
    public @NotNull ArgumentType stringArg() {
        return StringArgumentType.STRING;
    }

    @Override
    public @NotNull ArgumentType wordStringArg() {
        return StringArgumentType.WORD;
    }

    @Override
    public @NotNull ArgumentType greedyStringArg() {
        return StringArgumentType.GREEDY_STRING;
    }

    @Override
    public @NotNull ArgumentType booleanType() {
        return BooleanArgumentType.BOOL;
    }

    @Override
    public @NotNull ArgumentType integerArg() {
        return new IntegerArgumentType(com.mojang.brigadier.arguments.IntegerArgumentType.integer());
    }

    @Override
    public @NotNull ArgumentType integerArg(int min) {
        return new IntegerArgumentType(com.mojang.brigadier.arguments.IntegerArgumentType.integer(min));
    }

    @Override
    public @NotNull ArgumentType integerArg(int min, int max) {
        return new IntegerArgumentType(com.mojang.brigadier.arguments.IntegerArgumentType.integer(min, max));
    }

    @Override
    public @NotNull ArgumentType doubleArg() {
        return new DoubleArgumentType(com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg());
    }

    @Override
    public @NotNull ArgumentType doubleArg(double min) {
        return new DoubleArgumentType(com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg(min));
    }

    @Override
    public @NotNull ArgumentType doubleArg(double min, double max) {
        return new DoubleArgumentType(com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg(min, max));
    }

    @Override
    public @NotNull ArgumentType longArg() {
        return new LongArgumentType(com.mojang.brigadier.arguments.LongArgumentType.longArg());
    }

    @Override
    public @NotNull ArgumentType longArg(long min) {
        return new LongArgumentType(com.mojang.brigadier.arguments.LongArgumentType.longArg(min));
    }

    @Override
    public @NotNull ArgumentType longArg(long min, long max) {
        return new LongArgumentType(com.mojang.brigadier.arguments.LongArgumentType.longArg(min, max));
    }

    @Override
    public @NotNull ArgumentType floatArg() {
        return new FloatArgumentType(com.mojang.brigadier.arguments.FloatArgumentType.floatArg());
    }

    @Override
    public @NotNull ArgumentType floatArg(float min) {
        return new FloatArgumentType(com.mojang.brigadier.arguments.FloatArgumentType.floatArg(min));
    }

    @Override
    public @NotNull ArgumentType floatArg(float min, float max) {
        return new FloatArgumentType(com.mojang.brigadier.arguments.FloatArgumentType.floatArg(min, max));
    }

    @Override
    public @NotNull ArgumentType singleEntity() {
        return EntityArgumentType.SINGLE_ENTITY;
    }

    @Override
    public @NotNull ArgumentType entities() {
        return EntityArgumentType.ENTITIES;
    }

    @Override
    public @NotNull ArgumentType singlePlayer() {
        return EntityArgumentType.SINGLE_PLAYER;
    }

    @Override
    public @NotNull ArgumentType players() {
        return EntityArgumentType.PLAYERS;
    }

    @Override
    public @NotNull ArgumentType angle() {
        return AngleArgumentType.ANGLE;
    }

    @Override
    public @NotNull ArgumentType component() {
        return ComponentArgumentType.COMPONENT;
    }

    @Override
    public @NotNull ArgumentType world() {
        return DimensionArgumentType.DIMENSION;
    }

    @Override
    public @NotNull ArgumentType message() {
        return MessageArgumentType.MESSAGE;
    }

    @Override
    public @NotNull ArgumentType gameMode() {
        return GameModeArgumentType.GAME_MODE;
    }

    @Override
    public @NotNull ArgumentType uuid() {
        return UuidArgumentType.UUID;
    }

    @Override
    public @NotNull ArgumentType vector(boolean centerIntegers) {
        return centerIntegers ? VectorArgumentType.CENTER : VectorArgumentType.NO_CENTER;
    }

    @Override
    public @NotNull ArgumentType particle() {
        return ParticleArgument.PARTICLE;
    }

    @Override
    public @NotNull ArgumentType block() {
        return BlockStateArgumentType.BLOCK_STATE;
    }

    @Override
    public @NotNull ArgumentType item() {
        return ItemArgumentType.ITEM;
    }

    @Override
    public @NotNull ArgumentType itemPredicate() {
        return ItemPredicateArgumentType.ITEM_PREDICATE;
    }

    @Override
    public @NotNull ArgumentType enchantment() {
        return EnchantmentArgument.ENCHANTMENT;
    }

    @Override
    public @NotNull ArgumentType entityType() {
        return EntityTypeArgument.ENTITY_TYPE;
    }

    @Override
    public @NotNull ArgumentType summonableEntityType() {
        return SummonableEntityTypeArgument.SUMMONABLE_ENTITY_TYPE;
    }

    @Override
    public @NotNull ArgumentType potionEffectType() {
        return PotionEffectArgument.MOB_EFFECT;
    }

    @Override
    public @NotNull ArgumentType playerProfiles() {
        return PlayerProfilesArgumentType.PLAYER_PROFILES;
    }

    @Override
    public @NotNull ArgumentType nbt() {
        return NbtArgumentType.NBT;
    }

    @Override
    public @NotNull ArgumentType nbtCompound() {
        return NbtCompoundArgumentType.NBT_COMPOUND;
    }

    @Override
    public @NotNull ArgumentType attribute() {
        return AttributeArgumentType.ATTRIBUTE;
    }

    @Override
    public @NotNull ArgumentType blockPosition() {
        return BlockPosArgumentType.BLOCK_POS;
    }

    @Override
    public @NotNull ArgumentType rotation() {
        return RotationArgumentType.ROTATION;
    }

}
