package fit.d6.candy.bukkit.nms.v1_20_R2;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import fit.d6.candy.api.gui.anvil.AnvilGuiScene;
import fit.d6.candy.api.item.BlockInput;
import fit.d6.candy.api.item.ItemInput;
import fit.d6.candy.api.item.ItemPredicate;
import fit.d6.candy.api.nbt.*;
import fit.d6.candy.api.player.Skin;
import fit.d6.candy.api.position.Rotation;
import fit.d6.candy.api.protocol.ProtocolManager;
import fit.d6.candy.api.protocol.packet.ClientboundDisconnectPacket;
import fit.d6.candy.api.protocol.packet.Packet;
import fit.d6.candy.api.visual.scoreboard.ScoreContent;
import fit.d6.candy.api.visual.tablist.TabListContent;
import fit.d6.candy.api.world.Environment;
import fit.d6.candy.bukkit.exception.PlayerException;
import fit.d6.candy.bukkit.exception.ProtocolException;
import fit.d6.candy.bukkit.exception.VisualException;
import fit.d6.candy.bukkit.exception.WorldException;
import fit.d6.candy.bukkit.gui.BukkitAnvilGuiScene;
import fit.d6.candy.bukkit.nms.FakeAnvil;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import fit.d6.candy.bukkit.nms.v1_20_R2.item.BukkitBlockInput;
import fit.d6.candy.bukkit.nms.v1_20_R2.item.BukkitItemInput;
import fit.d6.candy.bukkit.nms.v1_20_R2.nbt.*;
import fit.d6.candy.bukkit.player.BukkitSkin;
import fit.d6.candy.bukkit.position.BukkitRotation;
import fit.d6.candy.bukkit.protocol.BukkitProtocolManager;
import fit.d6.candy.bukkit.protocol.PacketHandler;
import fit.d6.candy.bukkit.protocol.packet.BukkitClientboundDisconnectPacket;
import fit.d6.candy.bukkit.protocol.packet.BukkitClientboundPlayerChatPacket;
import fit.d6.candy.bukkit.visual.scoreboard.BukkitObjective;
import fit.d6.candy.bukkit.visual.scoreboard.BukkitScore;
import fit.d6.candy.bukkit.visual.scoreboard.BukkitScoreContent;
import fit.d6.candy.bukkit.world.BukkitEnvironment;
import fit.d6.candy.bukkit.world.BukkitEnvironmentBuilder;
import fit.d6.candy.bukkit.world.BukkitWorldInitializer;
import io.netty.channel.Channel;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.*;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.*;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.validation.ContentValidationException;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R2.CraftParticle;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R2.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntityType;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_20_R2.generator.CraftWorldInfo;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftLocation;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class NmsAccessorV1_20_R2 implements NmsAccessor {

    public static final CommandBuildContext COMMAND_REGISTRY_ACCESS;

    static {
        CommandBuildContext commandRegistryAccess;
        try {
            Field field = ReloadableServerResources.class.getDeclaredField("c"); // do not modify, this is re-obfuscated field name
            field.setAccessible(true);
            commandRegistryAccess = (CommandBuildContext) field.get(MinecraftServer.getServer().resources.managers());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            commandRegistryAccess = null;
        }
        COMMAND_REGISTRY_ACCESS = commandRegistryAccess;
    }

    @Override
    public void setTitle(Player player, Inventory inventory, Component component) {
        ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        int containerId = entityPlayer.containerMenu.containerId;
        MenuType<?> windowType = CraftContainer.getNotchInventoryType(inventory);
        entityPlayer.connection.send(new ClientboundOpenScreenPacket(containerId, windowType, PaperAdventure.asVanilla(component == null ? Component.text(" ") : component)));
        player.updateInventory();
    }

    @Override
    public void openAnvil(Player player, FakeAnvil fakeAnvil, Component title) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

        CraftEventFactory.handleInventoryCloseEvent(serverPlayer, InventoryCloseEvent.Reason.PLUGIN);
        serverPlayer.connection.send(new ClientboundOpenScreenPacket(fakeAnvil.getContainerId(), MenuType.ANVIL, PaperAdventure.asVanilla(title == null ? Component.text(" ") : title)));
        serverPlayer.containerMenu = (AbstractContainerMenu) fakeAnvil;
        serverPlayer.initMenu((AbstractContainerMenu) fakeAnvil);
    }

    @Override
    public FakeAnvil createAnvil(AnvilGuiScene scene, Player player, Component title) {
        return new BukkitFakeAnvil((BukkitAnvilGuiScene) scene, player, title);
    }

    @Override
    public ArgumentType<?> argumentSingleEntity() {
        return EntityArgument.entity();
    }

    @Override
    public ArgumentType<?> argumentEntities() {
        return EntityArgument.entities();
    }

    @Override
    public ArgumentType<?> argumentSinglePlayer() {
        return EntityArgument.player();
    }

    @Override
    public ArgumentType<?> argumentPlayers() {
        return EntityArgument.players();
    }

    @Override
    public ArgumentType<?> argumentAngle() {
        return AngleArgument.angle();
    }

    @Override
    public ArgumentType<?> argumentComponent() {
        return ComponentArgument.textComponent();
    }

    @Override
    public ArgumentType<?> argumentWorld() {
        return DimensionArgument.dimension();
    }

    @Override
    public ArgumentType<?> argumentMessage() {
        return MessageArgument.message();
    }

    @Override
    public ArgumentType<?> argumentGameMode() {
        return GameModeArgument.gameMode();
    }

    @Override
    public ArgumentType<?> argumentUuid() {
        return UuidArgument.uuid();
    }

    @Override
    public ArgumentType<?> argumentVector(boolean centerIntegers) {
        return Vec3Argument.vec3(centerIntegers);
    }

    @Override
    public ArgumentType<?> argumentParticle() {
        return ParticleArgument.particle(COMMAND_REGISTRY_ACCESS);
    }

    @Override
    public ArgumentType<?> argumentBlock() {
        return BlockStateArgument.block(COMMAND_REGISTRY_ACCESS);
    }

    @Override
    public ArgumentType<?> argumentItem() {
        return ItemArgument.item(COMMAND_REGISTRY_ACCESS);
    }

    @Override
    public ArgumentType<?> argumentItemPredicate() {
        return ItemPredicateArgument.itemPredicate(COMMAND_REGISTRY_ACCESS);
    }

    @Override
    public ArgumentType<?> argumentEnchantment() {
        return ResourceArgument.resource(COMMAND_REGISTRY_ACCESS, Registries.ENCHANTMENT);
    }

    @Override
    public ArgumentType<?> argumentEntityType() {
        return ResourceArgument.resource(COMMAND_REGISTRY_ACCESS, Registries.ENTITY_TYPE);
    }

    @Override
    public ArgumentType<?> argumentSummonableEntityType() {
        return ResourceArgument.resource(COMMAND_REGISTRY_ACCESS, Registries.ENTITY_TYPE);
    }

    @Override
    public ArgumentType<?> argumentPotionEffectType() {
        return ResourceArgument.resource(COMMAND_REGISTRY_ACCESS, Registries.MOB_EFFECT);
    }

    @Override
    public ArgumentType<?> argumentPlayerProfiles() {
        return GameProfileArgument.gameProfile();
    }

    @Override
    public ArgumentType<?> argumentNbt() {
        return NbtTagArgument.nbtTag();
    }

    @Override
    public ArgumentType<?> argumentNbtCompound() {
        return CompoundTagArgument.compoundTag();
    }

    @Override
    public ArgumentType<?> argumentAttribute() {
        return ResourceArgument.resource(COMMAND_REGISTRY_ACCESS, Registries.ATTRIBUTE);
    }

    @Override
    public ArgumentType<?> argumentBlockPosition() {
        return BlockPosArgument.blockPos();
    }

    @Override
    public Entity getArgumentSingleEntity(Object context, String name) throws CommandSyntaxException {
        return EntityArgument.getEntity(((CommandContext<CommandSourceStack>) context), name).getBukkitEntity();
    }

    @Override
    public Set<Entity> getArgumentEntities(Object context, String name) throws CommandSyntaxException {
        return EntityArgument.getEntities(((CommandContext<CommandSourceStack>) context), name).stream().map(net.minecraft.world.entity.Entity::getBukkitEntity).collect(Collectors.toSet());
    }

    @Override
    public Set<Entity> getArgumentOptionalEntities(Object context, String name) throws CommandSyntaxException {
        return EntityArgument.getOptionalEntities(((CommandContext<CommandSourceStack>) context), name).stream().map(net.minecraft.world.entity.Entity::getBukkitEntity).collect(Collectors.toSet());
    }

    @Override
    public Player getArgumentSinglePlayer(Object context, String name) throws CommandSyntaxException {
        return EntityArgument.getPlayer(((CommandContext<CommandSourceStack>) context), name).getBukkitEntity();
    }

    @Override
    public Set<Player> getArgumentPlayers(Object context, String name) throws CommandSyntaxException {
        return EntityArgument.getPlayers(((CommandContext<CommandSourceStack>) context), name).stream().map(ServerPlayer::getBukkitEntity).collect(Collectors.toSet());
    }

    @Override
    public Set<Player> getArgumentOptionalPlayers(Object context, String name) throws CommandSyntaxException {
        return EntityArgument.getOptionalPlayers(((CommandContext<CommandSourceStack>) context), name).stream().map(ServerPlayer::getBukkitEntity).collect(Collectors.toSet());
    }

    @Override
    public float getArgumentAngle(Object context, String name) {
        return AngleArgument.getAngle(((CommandContext<CommandSourceStack>) context), name);
    }

    @Override
    public Component getArgumentComponent(Object context, String name) {
        return PaperAdventure.asAdventure(ComponentArgument.getComponent(((CommandContext<CommandSourceStack>) context), name));
    }

    @Override
    public World getArgumentWorld(Object context, String name) throws CommandSyntaxException {
        return DimensionArgument.getDimension(((CommandContext<CommandSourceStack>) context), name).getWorld();
    }

    @Override
    public Component getArgumentMessage(Object context, String name) throws CommandSyntaxException {
        return PaperAdventure.asAdventure(MessageArgument.getMessage(((CommandContext<CommandSourceStack>) context), name));
    }

    @Override
    public GameMode getArgumentGameMode(Object context, String name) throws CommandSyntaxException {
        return GameMode.valueOf(GameModeArgument.getGameMode(((CommandContext<CommandSourceStack>) context), name).getName().toUpperCase());
    }

    @Override
    public UUID getArgumentUuid(Object context, String name) {
        return UuidArgument.getUuid(((CommandContext<CommandSourceStack>) context), name);
    }

    @Override
    public Vector getArgumentVector(Object context, String name) {
        Vec3 vec3 = Vec3Argument.getVec3(((CommandContext<CommandSourceStack>) context), name);
        return new Vector(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public Particle getArgumentParticle(Object context, String name) {
        return CraftParticle.minecraftToBukkit(ParticleArgument.getParticle(((CommandContext<CommandSourceStack>) context), name).getType());
    }

    @Override
    public BlockInput getArgumentBlock(Object context, String name) {
        return new BukkitBlockInput(BlockStateArgument.getBlock(((CommandContext<CommandSourceStack>) context), name));
    }

    @Override
    public ItemInput getArgumentItem(Object context, String name) {
        return new BukkitItemInput(ItemArgument.getItem(((CommandContext<CommandSourceStack>) context), name));
    }

    @Override
    public ItemPredicate getArgumentItemPredicate(Object context, String name) {
        Predicate<ItemStack> predicate = ItemPredicateArgument.getItemPredicate(((CommandContext<CommandSourceStack>) context), name);
        return (it) -> predicate.test(CraftItemStack.asNMSCopy(it));
    }

    @Override
    public Enchantment getArgumentEnchantment(Object context, String name) throws CommandSyntaxException {
        return Registry.ENCHANTMENT.get(CraftNamespacedKey.fromMinecraft(ResourceArgument.getEnchantment(((CommandContext<CommandSourceStack>) context), name).key().location()));
    }

    @Override
    public EntityType getArgumentEntityType(Object context, String name) throws CommandSyntaxException {
        return CraftEntityType.minecraftToBukkit(ResourceArgument.getEntityType(((CommandContext<CommandSourceStack>) context), name).value());
    }

    @Override
    public EntityType getArgumentSummonableEntityType(Object context, String name) throws CommandSyntaxException {
        return CraftEntityType.minecraftToBukkit(ResourceArgument.getSummonableEntityType(((CommandContext<CommandSourceStack>) context), name).value());
    }

    @Override
    public PotionEffectType getArgumentPotionEffectType(Object context, String name) throws CommandSyntaxException {
        return CraftPotionEffectType.minecraftToBukkit(ResourceArgument.getMobEffect(((CommandContext<CommandSourceStack>) context), name).value());
    }

    @Override
    public List<PlayerProfile> getArgumentPlayerProfiles(Object context, String name) throws CommandSyntaxException {
        return GameProfileArgument.getGameProfiles(((CommandContext<CommandSourceStack>) context), name).stream().map(CraftPlayerProfile::asBukkitMirror).toList();
    }

    @Override
    public NbtBase getArgumentNbt(Object context, String name) {
        return BukkitNbtCompound.nmsToCandy(NbtTagArgument.getNbtTag((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public NbtCompound getArgumentNbtCompound(Object context, String name) {
        return new BukkitNbtCompound(CompoundTagArgument.getCompoundTag((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Attribute getArgumentAttribute(Object context, String name) throws CommandSyntaxException {
        return CraftAttribute.minecraftToBukkit(ResourceArgument.getAttribute((CommandContext<CommandSourceStack>) context, name).value());
    }

    @Override
    public Location getArgumentBlockPosition(Object context, String name) {
        return CraftLocation.toBukkit(BlockPosArgument.getBlockPos((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Location getArgumentSpawnableBlockPosition(Object context, String name) throws CommandSyntaxException {
        return CraftLocation.toBukkit(BlockPosArgument.getSpawnablePos((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Location getArgumentLoadedBlockPosition(Object context, String name) throws CommandSyntaxException {
        return CraftLocation.toBukkit(BlockPosArgument.getLoadedBlockPos((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Location getArgumentLoadedBlockPosition(Object context, World world, String name) throws CommandSyntaxException {
        return CraftLocation.toBukkit(BlockPosArgument.getLoadedBlockPos((CommandContext<CommandSourceStack>) context, ((CraftWorld) world).getHandle(), name));
    }

    @Override
    public SuggestionProvider<?> getSummonableEntitiesProvider() {
        return SuggestionProviders.SUMMONABLE_ENTITIES;
    }

    @Override
    public RequiredArgumentBuilder<?, ?> createArgumentCommand(String name, ArgumentType<?> type) {
        return Commands.argument(name, type);
    }

    @Override
    public LiteralArgumentBuilder<?> createLiteralCommand(String name) {
        return Commands.literal(name);
    }

    @Override
    public CommandSender commandSourceStackGetBukkitSender(Object object) {
        return ((CommandSourceStack) object).getBukkitSender();
    }

    @Override
    public boolean commandSourceStackIsPlayer(Object object) {
        return ((CommandSourceStack) object).isPlayer();
    }

    @Override
    public Player commandSourceStackGetPlayerOrException(Object object) throws CommandSyntaxException {
        return ((CommandSourceStack) object).getPlayerOrException().getBukkitEntity();
    }

    @Override
    public SimpleCommandExceptionType commandSourceStackNotPlayerException() {
        return CommandSourceStack.ERROR_NOT_PLAYER;
    }

    @Override
    public Message componentAsVanilla(Component component) {
        return PaperAdventure.asVanilla(component);
    }

    @Override
    public Component componentAsAdventure(Object object) {
        return PaperAdventure.asAdventure((net.minecraft.network.chat.Component) object);
    }

    @Override
    public Object getBukkitCommands() {
        return MinecraftServer.getServer().getCommands();
    }

    @Override
    public Object createVanillaCommandWrapper(Object commands, CommandNode<?> commandNode) {
        return new VanillaCommandWrapper(commands == null ? null : (Commands) commands, (CommandNode<CommandSourceStack>) commandNode);
    }

    @Override
    public NbtCompound compoundNbt() {
        return new BukkitNbtCompound(new CompoundTag());
    }

    @Override
    public NbtList listNbt() {
        return new BukkitNbtList(new ListTag());
    }

    @Override
    public NbtEnd endNbt() {
        return BukkitNbtEnd.INSTANCE;
    }

    @Override
    public NbtString stringNbt(String value) {
        return new BukkitNbtString(StringTag.valueOf(value));
    }

    @Override
    public NbtByte byteNbt(byte value) {
        return new BukkitNbtByte(ByteTag.valueOf(value));
    }

    @Override
    public NbtShort shortNbt(short value) {
        return new BukkitNbtShort(ShortTag.valueOf(value));
    }

    @Override
    public NbtInt intNbt(int value) {
        return new BukkitNbtInt(IntTag.valueOf(value));
    }

    @Override
    public NbtLong longNbt(long value) {
        return new BukkitNbtLong(LongTag.valueOf(value));
    }

    @Override
    public NbtFloat floatNbt(float value) {
        return new BukkitNbtFloat(FloatTag.valueOf(value));
    }

    @Override
    public NbtDouble doubleNbt(double value) {
        return new BukkitNbtDouble(DoubleTag.valueOf(value));
    }

    @Override
    public NbtByteArray byteArrayNbt(byte[] value) {
        return new BukkitNbtByteArray(new ByteArrayTag(value));
    }

    @Override
    public NbtByteArray byteArrayNbt(List<Byte> value) {
        return new BukkitNbtByteArray(new ByteArrayTag(value));
    }

    @Override
    public NbtIntArray intArrayNbt(int[] value) {
        return new BukkitNbtIntArray(new IntArrayTag(value));
    }

    @Override
    public NbtIntArray intArrayNbt(List<Integer> value) {
        return new BukkitNbtIntArray(new IntArrayTag(value));
    }

    @Override
    public NbtLongArray longArrayNbt(long[] value) {
        return new BukkitNbtLongArray(new LongArrayTag(value));
    }

    @Override
    public NbtLongArray longArrayNbt(List<Long> value) {
        return new BukkitNbtLongArray(new LongArrayTag(value));
    }

    @Override
    public void sendRemovePlayerPacket(Player receiver, UUID who) {
        ServerPlayer serverPlayer = ((CraftPlayer) receiver).getHandle();
        serverPlayer.connection.send(new ClientboundPlayerInfoRemovePacket(List.of(who)));
    }

    @Override
    public void addPlayerList(Player receiver, List<TabListContent> contents) {
        ServerPlayer serverPlayer = ((CraftPlayer) receiver).getHandle();
        List<ClientboundPlayerInfoUpdatePacket.Entry> newList = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            TabListContent content = contents.get(i);

            GameProfile gameProfile = new GameProfile(content.getUniqueId(), "!tablist" + (i + 10));
            Skin skin = content.getSkin();
            if (skin != null) {
                gameProfile.getProperties().put("textures", new Property("textures", skin.getTextures(), skin.getSignature()));
            }

            newList.add(new ClientboundPlayerInfoUpdatePacket.Entry(
                    content.getUniqueId(),
                    gameProfile,
                    true,
                    content.getPing(),
                    GameType.CREATIVE,
                    PaperAdventure.asVanilla(content.getText()),
                    null));
        }
        serverPlayer.connection.send(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY), newList));
    }

    @Override
    public void addActualPlayers(Player receiver) {
        ServerPlayer serverPlayer = ((CraftPlayer) receiver).getHandle();
        serverPlayer.connection.send(new ClientboundPlayerInfoUpdatePacket(
                        EnumSet.of(
                                ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED,
                                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME,
                                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY
                        ),
                        Bukkit.getOnlinePlayers().stream().map(player -> ((CraftPlayer) player).getHandle()).toList()
                )
        );
    }

    @Override
    public List<Skin> getPlayerSkin(Player player) {
        GameProfile profile = ((CraftPlayer) player).getHandle().getGameProfile();
        List<Property> properties = new ArrayList<>(profile.getProperties().get("textures"));
        if (properties.isEmpty())
            throw new PlayerException("This player has no skin");
        return properties.stream()
                .map(property -> new BukkitSkin(property.value(), property.signature()))
                .map(skin -> (Skin) skin)
                .toList();
    }

    @Override
    public void injectPlayer(Object object, Player bukkitPlayer, Set<Channel> injected) {
        PacketHandler packetHandler = (PacketHandler) object;

        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        Channel channel = player.connection.connection.channel;
        channel.eventLoop().submit(() -> {
            if (injected.add(channel)) {
                channel.pipeline().addBefore("packet_handler", "CandyLibrary-ProtocolService", packetHandler);
            }
        });
    }

    @Override
    public boolean isPacketLoginOutSuccess(Object packet) {
        return packet instanceof ClientboundGameProfilePacket;
    }

    @Override
    public UUID getUUIDFromPacketLoginOutSuccess(Object packet) {
        return ((ClientboundGameProfilePacket) packet).getGameProfile().getId();
    }

    @Override
    public List<?> getNetworkManagers() {
        return MinecraftServer.getServer().getConnection().getConnections();
    }

    @Override
    public Channel getChannelWithNetworkManager(Object networkManager) {
        return ((Connection) networkManager).channel;
    }

    @Override
    public void injectNetworkManager(ProtocolManager interfaceOne, Logger logger, Object networkManager, Set<Channel> injected) {
        BukkitProtocolManager protocolManager = (BukkitProtocolManager) interfaceOne;
        Connection connection = (Connection) networkManager;
        PacketHandler handler = new PacketHandler(protocolManager, logger, connection.getPlayer().getBukkitEntity());
        this.injectPlayer(handler, connection.getPlayer().getBukkitEntity(), injected);
    }

    @Override
    public Channel getPlayerChannel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.connection.channel;
    }

    @Override
    public boolean packetIsClientboundPlayerChat(Object object) {
        return object instanceof ClientboundPlayerChatPacket;
    }

    @Override
    public boolean packetIsClientboundDisconnect(Object object) {
        return object instanceof net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
    }

    @Override
    public fit.d6.candy.api.protocol.packet.ClientboundPlayerChatPacket packetClientboundPlayerChatPacketAsCandy(Object packet) {
        ClientboundPlayerChatPacket vanillaPacket = (ClientboundPlayerChatPacket) packet;
        return new BukkitClientboundPlayerChatPacket(
                packet,
                vanillaPacket.sender(),
                vanillaPacket.index(),
                PaperAdventure.asAdventure(vanillaPacket.unsignedContent())
        );
    }

    @Override
    public ClientboundDisconnectPacket packetClientboundDisconnectPacketAsCandy(Object raw) {
        net.minecraft.network.protocol.common.ClientboundDisconnectPacket packet = (net.minecraft.network.protocol.common.ClientboundDisconnectPacket) raw;
        return new BukkitClientboundDisconnectPacket(packet, PaperAdventure.asAdventure(packet.getReason()));
    }

    @Override
    public Object packetAsVanilla(Packet packet) {
        if (packet instanceof BukkitClientboundPlayerChatPacket bukkitPacket) {
            ClientboundPlayerChatPacket original = (ClientboundPlayerChatPacket) bukkitPacket.getOriginal();

            net.minecraft.network.chat.Component newContent = PaperAdventure.asVanilla(bukkitPacket.getContent());

            return new ClientboundPlayerChatPacket(
                    original.sender(),
                    original.index(),
                    original.signature(),
                    new SignedMessageBody.Packed(
                            LegacyComponentSerializer.builder().hexColors().build().serialize(bukkitPacket.getContent()),
                            original.body().timeStamp(),
                            original.body().salt(),
                            original.body().lastSeen()
                    ),
                    newContent,
                    original.filterMask(),
                    original.chatType()
            );
        } else if (packet instanceof BukkitClientboundDisconnectPacket disconnectPacket) {
            return new net.minecraft.network.protocol.common.ClientboundDisconnectPacket(PaperAdventure.asVanilla(disconnectPacket.getReason()));
        }
        throw new ProtocolException("Unknown packet");
    }

    @Override
    public void sendPacket(Player player, Packet packet) {
        ((CraftPlayer) player).getHandle().connection.send((net.minecraft.network.protocol.Packet<?>) packetAsVanilla(packet));
    }

    @Override
    public Object createNewScoreboard() {
        return new Scoreboard();
    }

    @Override
    public Object createNewScoreboardObjective(Object scoreboard, String name, Component displayName) {
        return ((Scoreboard) scoreboard).addObjective(name, ObjectiveCriteria.DUMMY, PaperAdventure.asVanilla(displayName), ObjectiveCriteria.RenderType.INTEGER);
    }

    @Override
    public Object createNewScoreboardScoreHolder(String name, ScoreContent content) {
        return name;
    }

    @Override
    public Object createNewScoreboardPlayerTeam(Object scoreboard, String name, Object holder) {
        PlayerTeam team = ((Scoreboard) scoreboard).addPlayerTeam(name);
        ((Scoreboard) scoreboard).addPlayerToTeam(name, team);
        return team;
    }

    @Override
    public Object createNewScoreAccess(Object scoreboard, Object objective, Object scoreHolder) {
        return ((Scoreboard) scoreboard).getOrCreatePlayerScore((String) scoreHolder, (Objective) objective);
    }

    @Override
    public void updateObjectiveDisplayName(Object objective, Component displayName) {
        ((Objective) objective).setDisplayName(PaperAdventure.asVanilla(displayName));
    }

    @Override
    public void removeScoreboardObjective(Object scoreboard, Object objective) {
        ((Scoreboard) scoreboard).removeObjective((Objective) objective);
    }

    @Override
    public void updateDisplaySlot(Object scoreboard, DisplaySlot displaySlot, Object objective) {
        net.minecraft.world.scores.DisplaySlot slot = null;
        for (net.minecraft.world.scores.DisplaySlot vanillaSlot : net.minecraft.world.scores.DisplaySlot.values()) {
            if (vanillaSlot.getSerializedName().equals(displaySlot.getId())) {
                slot = vanillaSlot;
                break;
            }
        }
        if (slot == null)
            throw new VisualException("Failed to convert display slot");
        ((Scoreboard) scoreboard).setDisplayObjective(net.minecraft.world.scores.DisplaySlot.valueOf(displaySlot.name()), objective == null ? null : (Objective) objective);
    }

    @Override
    public void updatePlayerTeamPrefix(Object playerTeam, Component prefix) {
        ((PlayerTeam) playerTeam).setPlayerPrefix(PaperAdventure.asVanilla(prefix));
    }

    @Override
    public void updatePlayerTeamSuffix(Object playerTeam, Component suffix) {
        ((PlayerTeam) playerTeam).setPlayerSuffix(PaperAdventure.asVanilla(suffix));
    }

    @Override
    public int scoreboardScoreAccessGet(Object scoreAccess) {
        return ((Score) scoreAccess).getScore();
    }

    @Override
    public void scoreboardScoreAccessSet(Object scoreAccess, int score) {
        ((Score) scoreAccess).setScore(score);
    }

    @Override
    public int scoreboardScoreAccessAdd(Object scoreAccess, int amount) {
        ((Score) scoreAccess).add(amount);
        return ((Score) scoreAccess).getScore();
    }

    @Override
    public int scoreboardScoreAccessIncrement(Object scoreAccess) {
        ((Score) scoreAccess).increment();
        return ((Score) scoreAccess).getScore();
    }

    @Override
    public void scoreboardScoreAccessReset(Object scoreAccess) {
        ((Score) scoreAccess).reset();
    }

    @Override
    public void scoreboardScoreAccessUpdateDisplayName(Object scoreAccess, Component displayName) {
    }

    @Override
    public void removeScoreAccess(Object scoreboard, Object objective, Object scoreHolder) {
        ((Scoreboard) scoreboard).resetPlayerScore((String) scoreHolder, (Objective) objective);
    }

    @Override
    public void sendSetObjectivePacketAdd(Player bukkitPlayer, BukkitObjective objective) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(new ClientboundSetObjectivePacket((Objective) objective.getOriginal(), 0));
    }

    @Override
    public void sendSetObjectivePacketRemove(Player bukkitPlayer, BukkitObjective objective) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(new ClientboundSetObjectivePacket((Objective) objective.getOriginal(), 1));
    }

    @Override
    public void sendSetObjectivePacketChange(Player bukkitPlayer, BukkitObjective objective) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(new ClientboundSetObjectivePacket((Objective) objective.getOriginal(), 2));
    }

    @Override
    public void sendSetDisplayObjectivePacket(Player bukkitPlayer, DisplaySlot bukkitSlot, BukkitObjective objective) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        net.minecraft.world.scores.DisplaySlot slot = null;
        for (net.minecraft.world.scores.DisplaySlot vanillaSlot : net.minecraft.world.scores.DisplaySlot.values()) {
            if (vanillaSlot.getSerializedName().equals(bukkitSlot.getId())) {
                slot = vanillaSlot;
                break;
            }
        }
        if (slot == null)
            return;
        player.connection.send(new ClientboundSetDisplayObjectivePacket(slot, objective == null ? null : (Objective) objective.getOriginal()));
    }

    @Override
    public void sendSetPlayerTeamPacket_Add(Player bukkitPlayer, BukkitScoreContent content) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket((PlayerTeam) content.getPlayerTeam(), true));
    }

    @Override
    public void sendSetPlayerTeamPacket_Modify(Player bukkitPlayer, BukkitScoreContent content) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket((PlayerTeam) content.getPlayerTeam(), false));
    }

    @Override
    public void sendSetPlayerTeamPacket_Remove(Player bukkitPlayer, BukkitScoreContent content) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(ClientboundSetPlayerTeamPacket.createRemovePacket((PlayerTeam) content.getPlayerTeam()));
    }

    @Override
    public void sendRemoveScore(Player bukkitPlayer, BukkitObjective objective, BukkitScore score, Component component) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, ((Objective) objective.getOriginal()).getName(), LegacyComponentSerializer.builder().build().serialize(component), score.getScore()));
    }

    @Override
    public void sendSetScorePacket(Player bukkitPlayer, BukkitObjective objective, BukkitScore score) {
        ServerPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        player.connection.send(new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, ((Objective) objective.getOriginal()).getName(), LegacyComponentSerializer.builder().build().serialize(score.getContent().getDisplayName()), score.getScore()));
    }

    @Override
    public NbtCompound getNbt(org.bukkit.inventory.ItemStack itemStack) {
        CompoundTag tag = CraftItemStack.asNMSCopy(itemStack).getTag();
        return tag == null ? null : (NbtCompound) BukkitNbtCompound.nmsToCandy(tag);
    }

    @Override
    public NbtCompound getOrCreateNbt(org.bukkit.inventory.ItemStack itemStack) {
        return (NbtCompound) BukkitNbtCompound.nmsToCandy(CraftItemStack.asNMSCopy(itemStack).getOrCreateTag());
    }

    @Override
    public org.bukkit.inventory.ItemStack setNbt(org.bukkit.inventory.ItemStack itemStack, NbtCompound nbtCompound) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        nmsStack.setTag((CompoundTag) ((BukkitNbtCompound) nbtCompound).getNms());
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    @Override
    public JsonObject asJson(NbtCompound compound) {
        return JsonParser.parseString(compound.getAsString()).getAsJsonObject();
    }

    @Override
    public NbtCompound asNbt(JsonObject json) {
        try {
            return new BukkitNbtCompound(TagParser.parseTag(json.toString()));
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgumentType<?> argumentRotation() {
        return RotationArgument.rotation();
    }

    @Override
    public Rotation getArgumentRotation(Object context, String name) {
        Vec2 vec2 = RotationArgument.getRotation(((CommandContext<CommandSourceStack>) context), name).getRotation(((CommandContext<CommandSourceStack>) context).getSource());
        return new BukkitRotation(vec2.y, vec2.x);
    }

    @Override
    public World createBukkitWorld(WorldCreator creator, BukkitWorldInitializer initializer) {
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        DedicatedServer console = craftServer.getServer();

        Preconditions.checkState(console.getAllLevels().iterator().hasNext(), "Cannot create additional worlds on STARTUP");
        //Preconditions.checkState(!console.isIteratingOverLevels, "Cannot create a world while worlds are being ticked"); // Paper - Cat - Temp disable. We'll see how this goes.
        Preconditions.checkArgument(creator != null, "WorldCreator cannot be null");

        String name = creator.name();

        String levelName = console.getProperties().levelName;
        ResourceKey<net.minecraft.world.level.Level> worldKey;
        if (name.equals(levelName + "_nether")) {
            worldKey = net.minecraft.world.level.Level.NETHER;
        } else if (name.equals(levelName + "_the_end")) {
            worldKey = net.minecraft.world.level.Level.END;
        } else {
            worldKey = ResourceKey.create(Registries.DIMENSION, new net.minecraft.resources.ResourceLocation(creator.key().getNamespace().toLowerCase(java.util.Locale.ENGLISH), creator.key().getKey().toLowerCase(java.util.Locale.ENGLISH))); // Paper
        }

        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        File folder = new File(craftServer.getWorldContainer(), name);
        World world = craftServer.getWorld(name);

        CraftWorld worldByKey = (CraftWorld) craftServer.getWorld(creator.key());
        if (world != null || worldByKey != null) {
            if (world != worldByKey) {
                throw new WorldException("World duplicated");
            }
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new WorldException("Invalid world folder");
        }

        if (generator == null) {
            generator = craftServer.getGenerator(name);
        }

        if (biomeProvider == null) {
            biomeProvider = craftServer.getBiomeProvider(name);
        }

        ResourceKey<LevelStem> actualDimension;

        if (initializer.getEnvironment() != null) {
            actualDimension = (ResourceKey<LevelStem>) ((BukkitEnvironment) initializer.getEnvironment()).getKey();
        } else {
            actualDimension = switch (creator.environment()) {
                case NORMAL -> LevelStem.OVERWORLD;
                case NETHER -> LevelStem.NETHER;
                case THE_END -> LevelStem.END;
                default -> throw new WorldException("Illegal dimension");
            };
        }

        LevelStorageSource.LevelStorageAccess worldSession;
        try {
            worldSession = LevelStorageSource.createDefault(craftServer.getWorldContainer().toPath()).validateAndCreateAccess(name, actualDimension);
        } catch (IOException | ContentValidationException ex) {
            throw new WorldException(ex);
        }

        boolean hardcore = creator.hardcore();

        PrimaryLevelData worlddata;
        WorldLoader.DataLoadContext worldloader_a = console.worldLoader;
        net.minecraft.core.Registry<LevelStem> iregistry = worldloader_a.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);
        DynamicOps<Tag> dynamicops = RegistryOps.create(NbtOps.INSTANCE, worldloader_a.datapackWorldgen());
        Pair<WorldData, WorldDimensions.Complete> pair = worldSession.getDataTag(dynamicops, worldloader_a.dataConfiguration(), iregistry, worldloader_a.datapackWorldgen().allRegistriesLifecycle());

        if (pair != null) {
            worlddata = (PrimaryLevelData) pair.getFirst();
            iregistry = pair.getSecond().dimensions();
        } else {
            LevelSettings worldsettings;
            WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
            WorldDimensions worlddimensions;

            DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(GsonHelper.parse((creator.generatorSettings().isEmpty()) ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT));

            worldsettings = new LevelSettings(name, GameType.byId(craftServer.getDefaultGameMode().getValue()), hardcore, Difficulty.EASY, false, new GameRules(), worldloader_a.dataConfiguration());
            worlddimensions = properties.create(worldloader_a.datapackWorldgen());

            WorldDimensions.Complete worlddimensions_b = worlddimensions.bake(iregistry);
            Lifecycle lifecycle = worlddimensions_b.lifecycle().add(worldloader_a.datapackWorldgen().allRegistriesLifecycle());

            worlddata = new PrimaryLevelData(worldsettings, worldoptions, worlddimensions_b.specialWorldProperty(), lifecycle);
            iregistry = worlddimensions_b.dimensions();
        }
        worlddata.customDimensions = iregistry;
        worlddata.checkName(name);
        worlddata.setModdedInfo(console.getServerModName(), console.getModdedStatus().shouldReportAsModified());

        // Paper - move down

        long j = BiomeManager.obfuscateSeed(worlddata.worldGenOptions().seed()); // Paper - use world seed
        List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
        // LevelStem worlddimension = iregistry.get(actualDimension);
        LevelStem worlddimension = (LevelStem) ((BukkitEnvironment) initializer.getEnvironment()).getStem();

        WorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), worlddimension.type().value(), worlddimension.generator(), console.registryAccess()); // Paper
        if (biomeProvider == null && generator != null) {
            biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
        }

        // Paper start - fix and optimise world upgrading
        if (console.options.has("forceUpgrade")) {
            net.minecraft.server.Main.convertWorldButItWorks(
                    actualDimension, worldSession, DataFixers.getDataFixer(), worlddimension.generator().getTypeNameForDataFixer(), console.options.has("eraseCache")
            );
        }
        // Paper end - fix and optimise world upgrading

        ServerLevel internal = new ServerLevel(console, console.executor, worldSession, worlddata, worldKey, worlddimension, console.progressListenerFactory.create(11),
                worlddata.isDebugWorld(), j, creator.environment() == World.Environment.NORMAL ? list : ImmutableList.of(), true, console.overworld().getRandomSequences(), creator.environment(), generator, biomeProvider);
        // internal.keepSpawnInMemory = creator.keepSpawnInMemory(); // Paper - replace

        console.addLevel(internal); // Paper - move up
        console.initWorld(internal, worlddata, worlddata, worlddata.worldGenOptions());

        internal.setSpawnSettings(true, true);
        // Paper - move up

        internal.keepSpawnInMemory = creator.keepSpawnLoaded().toBooleanOrElse(internal.getWorld().getKeepSpawnInMemory()); // Paper
        console.prepareLevels(internal.getChunkSource().chunkMap.progressListener, internal);
        //internal.entityManager.tick(); // SPIGOT-6526: Load pending entities so they are available to the API // Paper - rewrite chunk system

        return internal.getWorld();
    }

    @Override
    public Environment registerEnvironment(BukkitEnvironmentBuilder builder) {
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        DedicatedServer console = craftServer.getServer();

        ResourceKey<DimensionType> resourceKeyDimension = ResourceKey.create(Registries.DIMENSION_TYPE, CraftNamespacedKey.toMinecraft(builder.getKey()));
        ResourceKey<LevelStem> resourceKeyLevelStem = ResourceKey.create(Registries.LEVEL_STEM, CraftNamespacedKey.toMinecraft(builder.getKey()));
        WritableRegistry<DimensionType> registryDimensions = (WritableRegistry<DimensionType>) console.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE);
        WritableRegistry<LevelStem> registryLevelStems = (WritableRegistry<LevelStem>) console.registryAccess().registryOrThrow(Registries.LEVEL_STEM);

        for (Field field : MappedRegistry.class.getDeclaredFields()) {
            if (field.getType() != boolean.class)
                continue;
            field.setAccessible(true);
            try {
                field.set(registryDimensions, false);
                field.set(registryLevelStems, false);
            } catch (IllegalAccessException e) {
                throw new WorldException(e);
            }
            break;
        }

        DimensionType dimensionType = new DimensionType(
                builder.fixedTime == null ? OptionalLong.empty() : OptionalLong.of(builder.fixedTime),
                builder.hasSkylight,
                builder.hasCeiling,
                builder.ultraWarm,
                builder.natural,
                builder.coordinateScale,
                builder.bedWorks,
                builder.respawnAnchorWorks,
                builder.minY,
                builder.height,
                builder.logicalHeight,
                TagKey.create(Registries.BLOCK, CraftNamespacedKey.toMinecraft(builder.infiniburn.getKey())),
                CraftNamespacedKey.toMinecraft(builder.effectsLocation),
                builder.ambientLight,
                new DimensionType.MonsterSettings(
                        builder.piglinSafe,
                        builder.hasRaids,
                        ConstantInt.of(builder.monsterSpawnLightTest),
                        builder.monsterSpawnBlockLightLimit
                )
        );

        Holder.Reference<DimensionType> holder = registryDimensions.register(resourceKeyDimension, dimensionType, Lifecycle.stable());

        for (Method method : Holder.Reference.class.getDeclaredMethods()) {
            if (method.getReturnType() == Void.class || method.getReturnType() == void.class) {
                if (method.getParameterCount() == 1) {
                    if (method.getGenericParameterTypes()[0] instanceof TypeVariable<?>) {
                        method.setAccessible(true);
                        try {
                            method.invoke(holder, dimensionType);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
            }
        }


        ResourceKey<NoiseGeneratorSettings> resourceKey;

        if (builder.noiseSettings == null) {
            if (builder.base == World.Environment.NETHER) {
                resourceKey = NoiseGeneratorSettings.NETHER;
            } else if (builder.base == World.Environment.THE_END) {
                resourceKey = NoiseGeneratorSettings.END;
            } else {
                resourceKey = NoiseGeneratorSettings.OVERWORLD;
            }
        } else {
            resourceKey = switch (builder.noiseSettings) {
                default -> NoiseGeneratorSettings.OVERWORLD;
                case LARGE_BIOMES -> NoiseGeneratorSettings.LARGE_BIOMES;
                case AMPLIFIED -> NoiseGeneratorSettings.AMPLIFIED;
                case NETHER -> NoiseGeneratorSettings.NETHER;
                case END -> NoiseGeneratorSettings.END;
                case CAVES -> NoiseGeneratorSettings.CAVES;
                case FLOATING_ISLANDS -> NoiseGeneratorSettings.FLOATING_ISLANDS;
            };
        }

        HolderLookup<Biome> biomeHolderGetter = console.registryAccess()
                .registryOrThrow(Registries.BIOME)
                .asLookup()
                .filterFeatures(FeatureFlagSet.of());
        MultiNoiseBiomeSourceParameterList parameterList = new MultiNoiseBiomeSourceParameterList(resourceKey == NoiseGeneratorSettings.NETHER ? MultiNoiseBiomeSourceParameterList.Preset.NETHER : MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, biomeHolderGetter);
        BiomeSource biomeSource = MultiNoiseBiomeSource.createFromPreset(Holder.direct(parameterList));

        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, console.registryAccess()
                .registryOrThrow(Registries.NOISE_SETTINGS)
                .getHolderOrThrow(resourceKey));

        LevelStem levelStem = new LevelStem(holder, chunkGenerator);

        Holder.Reference<LevelStem> levelStemHolder = registryLevelStems.register(resourceKeyLevelStem, levelStem, Lifecycle.stable());

        for (Method method : Holder.Reference.class.getDeclaredMethods()) {
            if (method.getReturnType() == Void.class || method.getReturnType() == void.class) {
                if (method.getParameterCount() == 1) {
                    if (method.getGenericParameterTypes()[0] instanceof TypeVariable<?>) {
                        method.setAccessible(true);
                        try {
                            method.invoke(levelStemHolder, levelStem);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
            }
        }

        return new BukkitEnvironment(resourceKeyLevelStem, levelStem);
    }

    @Override
    public BukkitEnvironmentBuilder copyEnvironment(BukkitEnvironment environment, BukkitEnvironmentBuilder builder) {
        DimensionType dimensionType = ((CraftServer) Bukkit.getServer()).getServer()
                .registryAccess()
                .registryOrThrow(Registries.DIMENSION_TYPE)
                .get(((ResourceKey<LevelStem>) environment.getKey()).location());

        Preconditions.checkNotNull(dimensionType);
        OptionalLong fixedOptionLong = dimensionType.fixedTime();

        builder.fixedTime = fixedOptionLong.isPresent() ? fixedOptionLong.getAsLong() : null;
        builder.hasSkylight = dimensionType.hasSkyLight();
        builder.hasCeiling = dimensionType.hasCeiling();
        builder.ultraWarm = dimensionType.ultraWarm();
        builder.natural = dimensionType.natural();
        builder.coordinateScale = dimensionType.coordinateScale();
        builder.piglinSafe = dimensionType.piglinSafe();
        builder.bedWorks = dimensionType.bedWorks();
        builder.respawnAnchorWorks = dimensionType.respawnAnchorWorks();
        builder.hasRaids = dimensionType.hasRaids();
        builder.minY = dimensionType.minY();
        builder.height = dimensionType.height();
        builder.logicalHeight = dimensionType.logicalHeight();
        builder.ambientLight = dimensionType.ambientLight();

        TagKey<Block> infinityBurn = dimensionType.infiniburn();
        ResourceLocation infinityBurnLoc = infinityBurn.location();
        ResourceLocation effectLoc = dimensionType.effectsLocation();

        builder.infiniburn = Bukkit.getServer().getTag("blocks", new NamespacedKey(infinityBurnLoc.getNamespace(), infinityBurnLoc.getPath()), Material.class);
        builder.effectsLocation = new NamespacedKey(effectLoc.getNamespace(), effectLoc.getPath());

        return builder;
    }

    @Override
    public World createFoliaWorld(WorldCreator creator, BukkitWorldInitializer initializer) {
        io.papermc.paper.threadedregions.RegionizedServer.ensureGlobalTickThread("World create can be done only on global tick thread");
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        DedicatedServer console = craftServer.getServer();

        String name = creator.name();

        String levelName = console.getProperties().levelName;
        ResourceKey<net.minecraft.world.level.Level> worldKey;
        if (name.equals(levelName + "_nether")) {
            worldKey = net.minecraft.world.level.Level.NETHER;
        } else if (name.equals(levelName + "_the_end")) {
            worldKey = net.minecraft.world.level.Level.END;
        } else {
            worldKey = ResourceKey.create(Registries.DIMENSION, new net.minecraft.resources.ResourceLocation(creator.key().getNamespace().toLowerCase(java.util.Locale.ENGLISH), creator.key().getKey().toLowerCase(java.util.Locale.ENGLISH))); // Paper
        }

        ChunkGenerator generator = creator.generator();
        BiomeProvider biomeProvider = creator.biomeProvider();
        File folder = new File(craftServer.getWorldContainer(), name);
        org.bukkit.World world = craftServer.getWorld(name);

        CraftWorld worldByKey = (CraftWorld) craftServer.getWorld(creator.key());
        if (world != null || worldByKey != null) {
            if (world != worldByKey) {
                throw new WorldException("World duplicated");
            }
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new WorldException("Invalid world folder");
        }

        if (generator == null) {
            generator = craftServer.getGenerator(name);
        }

        if (biomeProvider == null) {
            biomeProvider = craftServer.getBiomeProvider(name);
        }

        ResourceKey<LevelStem> actualDimension;

        if (initializer.getEnvironment() != null) {
            actualDimension = (ResourceKey<LevelStem>) ((BukkitEnvironment) initializer.getEnvironment()).getKey();
        } else {
            actualDimension = switch (creator.environment()) {
                case NORMAL -> LevelStem.OVERWORLD;
                case NETHER -> LevelStem.NETHER;
                case THE_END -> LevelStem.END;
                default -> throw new WorldException("Illegal dimension");
            };
        }

        LevelStorageSource.LevelStorageAccess worldSession;
        try {
            worldSession = LevelStorageSource.createDefault(craftServer.getWorldContainer().toPath()).createAccess(name, actualDimension);
        } catch (IOException ex) {
            throw new WorldException(ex);
        }

        boolean hardcore = creator.hardcore();

        PrimaryLevelData worlddata;
        WorldLoader.DataLoadContext worldloader_a = console.worldLoader;
        net.minecraft.core.Registry<LevelStem> iregistry = worldloader_a.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM);
        DynamicOps<Tag> dynamicops = RegistryOps.create(NbtOps.INSTANCE, worldloader_a.datapackWorldgen());
        Pair<WorldData, WorldDimensions.Complete> pair = worldSession.getDataTag(dynamicops, worldloader_a.dataConfiguration(), iregistry, worldloader_a.datapackWorldgen().allRegistriesLifecycle());

        if (pair != null) {
            worlddata = (PrimaryLevelData) pair.getFirst();
            iregistry = pair.getSecond().dimensions();
        } else {
            LevelSettings worldsettings;
            WorldOptions worldoptions = new WorldOptions(creator.seed(), creator.generateStructures(), false);
            WorldDimensions worlddimensions;

            DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(GsonHelper.parse((creator.generatorSettings().isEmpty()) ? "{}" : creator.generatorSettings()), creator.type().name().toLowerCase(Locale.ROOT));

            worldsettings = new LevelSettings(name, GameType.SURVIVAL, hardcore, Difficulty.EASY, false, new GameRules(), worldloader_a.dataConfiguration());
            worlddimensions = properties.create(worldloader_a.datapackWorldgen());

            WorldDimensions.Complete worlddimensions_b = worlddimensions.bake(iregistry);
            Lifecycle lifecycle = worlddimensions_b.lifecycle().add(worldloader_a.datapackWorldgen().allRegistriesLifecycle());

            worlddata = new PrimaryLevelData(worldsettings, worldoptions, worlddimensions_b.specialWorldProperty(), lifecycle);
            iregistry = worlddimensions_b.dimensions();
        }
        worlddata.customDimensions = iregistry;
        worlddata.checkName(name);
        worlddata.setModdedInfo(console.getServerModName(), console.getModdedStatus().shouldReportAsModified());

        long j = BiomeManager.obfuscateSeed(creator.seed());
        List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(worlddata));
        // LevelStem worlddimension = iregistry.get(actualDimension);
        LevelStem worlddimension = (LevelStem) ((BukkitEnvironment) initializer.getEnvironment()).getStem();

        WorldInfo worldInfo = new CraftWorldInfo(worlddata, worldSession, creator.environment(), worlddimension.type().value(), worlddimension.generator(), craftServer.getHandle().getServer().registryAccess()); // Paper
        if (biomeProvider == null && generator != null) {
            biomeProvider = generator.getDefaultBiomeProvider(worldInfo);
        }

        if (console.options.has("forceUpgrade")) {
            net.minecraft.server.Main.convertWorldButItWorks(
                    actualDimension, worldSession, DataFixers.getDataFixer(), worlddimension.generator().getTypeNameForDataFixer(), console.options.has("eraseCache")
            );
        }

        ServerLevel internal = new ServerLevel(console, console.executor, worldSession, worlddata, worldKey, worlddimension, console.progressListenerFactory.create(11),
                worlddata.isDebugWorld(), j, creator.environment() == org.bukkit.World.Environment.NORMAL ? list : ImmutableList.of(), true, null, creator.environment(), generator, biomeProvider);

        console.addLevel(internal);

        int loadRegionRadius = ((32) >> 4);
        internal.randomSpawnSelection = new ChunkPos(internal.getChunkSource().randomState().sampler().findSpawnPosition());
        for (int currX = -loadRegionRadius; currX <= loadRegionRadius; ++currX) {
            for (int currZ = -loadRegionRadius; currZ <= loadRegionRadius; ++currZ) {
                ChunkPos pos = new ChunkPos(currX, currZ);
                internal.chunkSource.addTicketAtLevel(
                        TicketType.UNKNOWN, pos, io.papermc.paper.chunk.system.scheduling.ChunkHolderManager.MAX_TICKET_LEVEL, pos
                );
            }
        }

        internal.setSpawnSettings(true, true);
        internal.keepSpawnInMemory = creator.keepSpawnLoaded().toBooleanOrElse(internal.getWorld().getKeepSpawnInMemory()); // Paper
        io.papermc.paper.threadedregions.RegionizedServer.getInstance().addWorld(internal);

        return internal.getWorld();
    }

    @Override
    public Object worldLevelStemOverworld() {
        return LevelStem.OVERWORLD;
    }

    @Override
    public Object worldLevelStemNether() {
        return LevelStem.NETHER;
    }

    @Override
    public Object worldLevelStemTheEnd() {
        return LevelStem.END;
    }

    @Override
    public Object worldLevelStem(Object key) {
        return ((CraftServer) Bukkit.getServer()).getServer().registryAccess().registryOrThrow(Registries.LEVEL_STEM).get((ResourceKey<LevelStem>) key);
    }

    @Override
    public @NotNull String getId() {
        return "nms_accessor";
    }

}
