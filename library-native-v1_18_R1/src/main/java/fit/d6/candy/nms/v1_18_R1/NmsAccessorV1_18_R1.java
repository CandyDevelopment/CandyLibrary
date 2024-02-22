package fit.d6.candy.nms.v1_18_R1;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
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
import fit.d6.candy.command.argument.AttributeArgumentType;
import fit.d6.candy.exception.CommandException;
import fit.d6.candy.exception.PlayerException;
import fit.d6.candy.exception.ProtocolException;
import fit.d6.candy.exception.WorldException;
import fit.d6.candy.gui.BukkitAnvilGuiScene;
import fit.d6.candy.nms.FakeAnvil;
import fit.d6.candy.nms.NmsAccessor;
import fit.d6.candy.nms.v1_18_R1.item.BukkitBlockInput;
import fit.d6.candy.nms.v1_18_R1.item.BukkitItemInput;
import fit.d6.candy.nms.v1_18_R1.nbt.*;
import fit.d6.candy.player.BukkitSkin;
import fit.d6.candy.position.BukkitRotation;
import fit.d6.candy.protocol.BukkitProtocolManager;
import fit.d6.candy.protocol.PacketHandler;
import fit.d6.candy.protocol.packet.BukkitClientboundDisconnectPacket;
import fit.d6.candy.protocol.packet.BukkitClientboundPlayerChatPacket;
import fit.d6.candy.visual.scoreboard.BukkitObjective;
import fit.d6.candy.visual.scoreboard.BukkitScore;
import fit.d6.candy.visual.scoreboard.BukkitScoreContent;
import fit.d6.candy.world.BukkitEnvironment;
import fit.d6.candy.world.BukkitEnvironmentBuilder;
import fit.d6.candy.world.BukkitWorldInitializer;
import io.netty.channel.Channel;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.nbt.*;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.commands.AttributeCommand;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
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
import org.bukkit.craftbukkit.v1_18_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class NmsAccessorV1_18_R1 implements NmsAccessor {

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
    public void trySuggests(RequiredArgumentBuilder<Object, ?> argumentBuilder, fit.d6.candy.api.command.ArgumentType candyType, ArgumentType<?> type) {
        if (type instanceof ResourceLocationArgument && candyType instanceof AttributeArgumentType) {
            for (Field field : AttributeCommand.class.getDeclaredFields()) {
                if (field.getType() == SuggestionProvider.class) {
                    try {
                        field.setAccessible(true);
                        argumentBuilder.suggests((SuggestionProvider<Object>) field.get(null));
                    } catch (IllegalAccessException ignored) {
                    }
                    break;
                }
            }
        }
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
        throw new CommandException("This argument type is not supported under this version");
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
        return ParticleArgument.particle();
    }

    @Override
    public ArgumentType<?> argumentBlock() {
        return BlockStateArgument.block();
    }

    @Override
    public ArgumentType<?> argumentItem() {
        return ItemArgument.item();
    }

    @Override
    public ArgumentType<?> argumentItemPredicate() {
        return ItemPredicateArgument.itemPredicate();
    }

    @Override
    public ArgumentType<?> argumentEnchantment() {
        return ItemEnchantmentArgument.enchantment();
    }

    @Override
    public ArgumentType<?> argumentEntityType() {
        throw new CommandException("Not available in this nms version");
    }

    @Override
    public ArgumentType<?> argumentSummonableEntityType() {
        return EntitySummonArgument.id();
    }

    @Override
    public ArgumentType<?> argumentPotionEffectType() {
        return MobEffectArgument.effect();
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
        return ResourceLocationArgument.id();
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
    public GameMode getArgumentGameMode(Object context, String name) {
        throw new CommandException("This argument type is not supported under this version");
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
        return CraftParticle.toBukkit(ParticleArgument.getParticle(((CommandContext<CommandSourceStack>) context), name).getType());
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
    public ItemPredicate getArgumentItemPredicate(Object context, String name) throws CommandSyntaxException {
        Predicate<ItemStack> predicate = ItemPredicateArgument.getItemPredicate(((CommandContext<CommandSourceStack>) context), name);
        return (it) -> predicate.test(CraftItemStack.asNMSCopy(it));
    }

    @Override
    public Enchantment getArgumentEnchantment(Object context, String name) {
        return org.bukkit.Registry.ENCHANTMENT.get(CraftNamespacedKey.fromMinecraft(Objects.requireNonNull(Registry.ENCHANTMENT.getKey(ItemEnchantmentArgument.getEnchantment(((CommandContext<CommandSourceStack>) context), name)))));
    }

    @Override
    public EntityType getArgumentEntityType(Object context, String name) {
        throw new CommandException("Not available in this nms version");
    }

    @Override
    public EntityType getArgumentSummonableEntityType(Object context, String name) throws CommandSyntaxException {
        return EntityType.valueOf(EntitySummonArgument.getSummonableEntity(((CommandContext<CommandSourceStack>) context), name).getPath());
    }

    @Override
    public PotionEffectType getArgumentPotionEffectType(Object context, String name) {
        return org.bukkit.Registry.POTION_EFFECT_TYPE.get(CraftNamespacedKey.fromMinecraft(Objects.requireNonNull(Registry.MOB_EFFECT.getKey(MobEffectArgument.getEffect(((CommandContext<CommandSourceStack>) context), name)))));
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
        NamespacedKey namespacedKey = CraftNamespacedKey.fromMinecraft(Objects.requireNonNull(Registry.ATTRIBUTE.getKey(ResourceLocationArgument.getAttribute((CommandContext<CommandSourceStack>) context, name))));
        for (Attribute attribute : Attribute.values()) {
            if (attribute.getKey().equals(namespacedKey))
                return attribute;
        }
        throw new CommandException("Failed to analyse the attribute");
    }

    @Override
    public Location getArgumentBlockPosition(Object context, String name) throws CommandSyntaxException {
        return NmsUtilsV1_18_R1.toBukkit(BlockPosArgument.getLoadedBlockPos((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Location getArgumentSpawnableBlockPosition(Object context, String name) throws CommandSyntaxException {
        return NmsUtilsV1_18_R1.toBukkit(BlockPosArgument.getSpawnablePos((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Location getArgumentLoadedBlockPosition(Object context, String name) throws CommandSyntaxException {
        return NmsUtilsV1_18_R1.toBukkit(BlockPosArgument.getLoadedBlockPos((CommandContext<CommandSourceStack>) context, name));
    }

    @Override
    public Location getArgumentLoadedBlockPosition(Object context, World world, String name) {
        throw new CommandException("Not available in this nms version");
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
        return ((CommandSourceStack) object).getBukkitSender() instanceof Player;
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
        return ((CraftServer) Bukkit.getServer()).getServer().getCommands();
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
        serverPlayer.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, List.of(serverPlayer)));
    }

    @Override
    public void addPlayerList(Player receiver, List<TabListContent> contents) {
        ServerPlayer serverPlayer = ((CraftPlayer) receiver).getHandle();
        List<ClientboundPlayerInfoPacket.PlayerUpdate> newList = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            TabListContent content = contents.get(i);

            GameProfile gameProfile = new GameProfile(content.getUniqueId(), "!tablist" + (i + 10));
            Skin skin = content.getSkin();
            if (skin != null) {
                gameProfile.getProperties().put("textures", new Property("textures", skin.getTextures(), skin.getSignature()));
            }

            newList.add(new ClientboundPlayerInfoPacket.PlayerUpdate(
                    gameProfile,
                    content.getPing(),
                    GameType.CREATIVE,
                    PaperAdventure.asVanilla(content.getText())));
        }
        ClientboundPlayerInfoPacket packetAddPlayer = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, new ArrayList<>());
        ClientboundPlayerInfoPacket packetUpdateLatency = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_LATENCY, new ArrayList<>());
        ClientboundPlayerInfoPacket packetUpdateDisplayName = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, new ArrayList<>());
        for (Field field : ClientboundPlayerInfoPacket.class.getDeclaredFields()) {
            if (field.getType() != List.class)
                continue;
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Type[] types = type.getActualTypeArguments();
            if (types.length == 0)
                continue;
            if (types[0] == ClientboundPlayerInfoPacket.PlayerUpdate.class) {
                field.setAccessible(true);
                try {
                    field.set(packetAddPlayer, newList);
                    field.set(packetUpdateLatency, newList);
                    field.set(packetUpdateDisplayName, newList);
                    break;
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        serverPlayer.connection.send(packetAddPlayer);
        serverPlayer.connection.send(packetUpdateLatency);
        serverPlayer.connection.send(packetUpdateDisplayName);
    }

    @Override
    public void addActualPlayers(Player receiver) {
        ServerPlayer serverPlayer = ((CraftPlayer) receiver).getHandle();
        List<ServerPlayer> players = Bukkit.getOnlinePlayers().stream().map(player -> ((CraftPlayer) player).getHandle()).toList();
        serverPlayer.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, players));
        serverPlayer.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_LATENCY, players));
        serverPlayer.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, players));
        serverPlayer.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_GAME_MODE, players));
    }

    @Override
    public List<Skin> getPlayerSkin(Player player) {
        GameProfile profile = ((CraftPlayer) player).getHandle().getGameProfile();
        List<Property> properties = new ArrayList<>(profile.getProperties().get("textures"));
        if (properties.isEmpty())
            throw new PlayerException("This player has no skin");
        return properties.stream()
                .map(property -> new BukkitSkin(property.getValue(), property.getSignature()))
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
        return Objects.requireNonNull(((CraftServer) Bukkit.getServer()).getServer().getConnection()).getConnections();
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
        return false;
    }

    @Override
    public boolean packetIsClientboundDisconnect(Object object) {
        return object instanceof net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
    }

    @Override
    public fit.d6.candy.api.protocol.packet.ClientboundPlayerChatPacket packetClientboundPlayerChatPacketAsCandy(Object packet) {
        throw new ProtocolException("Not supported version");
    }

    @Override
    public ClientboundDisconnectPacket packetClientboundDisconnectPacketAsCandy(Object raw) {
        net.minecraft.network.protocol.game.ClientboundDisconnectPacket packet = (net.minecraft.network.protocol.game.ClientboundDisconnectPacket) raw;
        return new BukkitClientboundDisconnectPacket(packet, PaperAdventure.asAdventure(packet.getReason()));
    }

    @Override
    public Object packetAsVanilla(Packet packet) {
        if (packet instanceof BukkitClientboundPlayerChatPacket) {
            throw new ProtocolException("Not supported version");
        } else if (packet instanceof BukkitClientboundDisconnectPacket disconnectPacket) {
            return new net.minecraft.network.protocol.game.ClientboundDisconnectPacket(PaperAdventure.asVanilla(disconnectPacket.getReason()));
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
        ((Scoreboard) scoreboard).setDisplayObjective(Scoreboard.getDisplaySlotByName(displaySlot.getId()), objective == null ? null : (Objective) objective);
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
        player.connection.send(new ClientboundSetDisplayObjectivePacket(Scoreboard.getDisplaySlotByName(bukkitSlot.getId()), objective == null ? null : (Objective) objective.getOriginal()));
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
        return creator.createWorld();
    }

    @Override
    public Environment registerEnvironment(BukkitEnvironmentBuilder builder) {
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        DedicatedServer console = craftServer.getServer();

        ResourceKey<DimensionType> resourceKeyDimension = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, CraftNamespacedKey.toMinecraft(builder.getKey()));
        ResourceKey<LevelStem> resourceKeyLevelStem = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, CraftNamespacedKey.toMinecraft(builder.getKey()));
        WritableRegistry<DimensionType> registryDimensions = (WritableRegistry<DimensionType>) console.registryAccess().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        WritableRegistry<LevelStem> registrymaterials = (WritableRegistry<LevelStem>) console.registryAccess().registryOrThrow(Registry.LEVEL_STEM_REGISTRY);

        for (Field field : MappedRegistry.class.getDeclaredFields()) {
            if (field.getType() != boolean.class)
                continue;
            field.setAccessible(true);
            try {
                field.set(registryDimensions, false);
                field.set(registrymaterials, false);
            } catch (IllegalAccessException e) {
                throw new WorldException(e);
            }
            break;
        }

        DimensionType dimensionType = DimensionType.create(
                builder.fixedTime == null ? OptionalLong.empty() : OptionalLong.of(builder.fixedTime),
                builder.hasSkylight,
                builder.hasCeiling,
                builder.ultraWarm,
                builder.natural,
                builder.coordinateScale,
                builder.enderDragonFight,
                builder.piglinSafe,
                builder.bedWorks,
                builder.respawnAnchorWorks,
                builder.hasRaids,
                builder.minY,
                builder.height,
                builder.logicalHeight,
                CraftNamespacedKey.toMinecraft(builder.infiniburn.getKey()),
                CraftNamespacedKey.toMinecraft(builder.effectsLocation),
                builder.ambientLight
        );

        registryDimensions.registerOrOverride(OptionalInt.empty(), resourceKeyDimension, dimensionType, Lifecycle.stable());

        LevelStem dimension = new LevelStem(() -> dimensionType, Objects.requireNonNull(((CraftServer) Bukkit.getServer()).getServer()
                .registryAccess()
                .registryOrThrow(Registry.LEVEL_STEM_REGISTRY)
                .get(LevelStem.OVERWORLD)).generator());

        return new BukkitEnvironment(resourceKeyLevelStem, registrymaterials.registerOrOverride(OptionalInt.empty(), resourceKeyLevelStem, dimension, Lifecycle.stable()));
    }

    @Override
    public BukkitEnvironmentBuilder copyEnvironment(BukkitEnvironment environment, BukkitEnvironmentBuilder builder) {
        DimensionType dimensionType = ((CraftServer) Bukkit.getServer()).getServer()
                .registryAccess()
                .registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY)
                .get(((ResourceKey<LevelStem>) environment.getKey()).location());

        OptionalLong fixedOptionLong = OptionalLong.empty();
        Float ambientLight = null;

        for (Field field : DimensionType.class.getDeclaredFields()) {
            if (field.getType() == OptionalLong.class) {
                field.setAccessible(true);
                try {
                    Object tempValue = field.get(dimensionType);
                    fixedOptionLong = tempValue == null ? OptionalLong.empty() : (OptionalLong) tempValue;
                } catch (IllegalAccessException e) {
                    throw new WorldException(e);
                }
            } else if (field.getType() == float.class && !Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    ambientLight = (float) field.get(dimensionType);
                } catch (IllegalAccessException e) {
                    throw new WorldException(e);
                }
            }
        }

        Preconditions.checkNotNull(dimensionType);
        builder.fixedTime = fixedOptionLong.isPresent() ? fixedOptionLong.getAsLong() : null;
        builder.hasSkylight = dimensionType.hasSkyLight();
        builder.hasCeiling = dimensionType.hasCeiling();
        builder.ultraWarm = dimensionType.ultraWarm();
        builder.natural = dimensionType.natural();
        builder.coordinateScale = dimensionType.coordinateScale();
        builder.enderDragonFight = dimensionType.createDragonFight();
        builder.piglinSafe = dimensionType.piglinSafe();
        builder.bedWorks = dimensionType.bedWorks();
        builder.respawnAnchorWorks = dimensionType.respawnAnchorWorks();
        builder.hasRaids = dimensionType.hasRaids();
        builder.minY = dimensionType.minY();
        builder.height = dimensionType.height();
        builder.logicalHeight = dimensionType.logicalHeight();
        builder.ambientLight = ambientLight == null ? 0.0f : ambientLight;

        builder.infiniburn = Bukkit.getServer().getTag("blocks", CraftNamespacedKey.fromMinecraft(Objects.requireNonNull(BlockTags.getAllTags().getId(dimensionType.infiniburn()))), Material.class);
        builder.effectsLocation = CraftNamespacedKey.fromMinecraft(dimensionType.effectsLocation());

        return builder;
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
    public @NotNull String getId() {
        return "nms_accessor";
    }

}
