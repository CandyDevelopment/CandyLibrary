package fit.d6.candy.bukkit.nms;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.gson.JsonObject;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.gui.anvil.AnvilGuiScene;
import fit.d6.candy.api.item.BlockInput;
import fit.d6.candy.api.item.ItemInput;
import fit.d6.candy.api.item.ItemPredicate;
import fit.d6.candy.api.nbt.*;
import fit.d6.candy.api.player.Skin;
import fit.d6.candy.api.position.Rotation;
import fit.d6.candy.api.protocol.ProtocolManager;
import fit.d6.candy.api.protocol.packet.ClientboundDisconnectPacket;
import fit.d6.candy.api.protocol.packet.ClientboundPlayerChatPacket;
import fit.d6.candy.api.protocol.packet.Packet;
import fit.d6.candy.api.visual.scoreboard.ScoreContent;
import fit.d6.candy.api.visual.tablist.TabListContent;
import fit.d6.candy.api.world.Environment;
import fit.d6.candy.bukkit.visual.scoreboard.BukkitObjective;
import fit.d6.candy.bukkit.visual.scoreboard.BukkitScore;
import fit.d6.candy.bukkit.visual.scoreboard.BukkitScoreContent;
import fit.d6.candy.bukkit.world.BukkitEnvironment;
import fit.d6.candy.bukkit.world.BukkitEnvironmentBuilder;
import fit.d6.candy.bukkit.world.BukkitWorldInitializer;
import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NmsAccessor extends Service {

    void setTitle(Player player, Inventory inventory, Component component);

    void openAnvil(Player player, FakeAnvil fakeAnvil, Component title);

    FakeAnvil createAnvil(AnvilGuiScene scene, Player player, Component title);

    default void trySuggests(RequiredArgumentBuilder<Object, ?> argumentBuilder, fit.d6.candy.api.command.ArgumentType candyType, ArgumentType<?> type) {
    }

    ArgumentType<?> argumentSingleEntity();

    ArgumentType<?> argumentEntities();

    ArgumentType<?> argumentSinglePlayer();

    ArgumentType<?> argumentPlayers();

    ArgumentType<?> argumentAngle();

    ArgumentType<?> argumentComponent();

    ArgumentType<?> argumentWorld();

    ArgumentType<?> argumentMessage();

    ArgumentType<?> argumentGameMode();

    ArgumentType<?> argumentUuid();

    ArgumentType<?> argumentVector(boolean centerIntegers);

    ArgumentType<?> argumentParticle();

    ArgumentType<?> argumentBlock();

    ArgumentType<?> argumentItem();

    ArgumentType<?> argumentItemPredicate();

    ArgumentType<?> argumentEnchantment();

    ArgumentType<?> argumentEntityType();

    ArgumentType<?> argumentSummonableEntityType();

    ArgumentType<?> argumentPotionEffectType();

    ArgumentType<?> argumentPlayerProfiles();

    ArgumentType<?> argumentNbt();

    ArgumentType<?> argumentNbtCompound();

    ArgumentType<?> argumentAttribute();

    ArgumentType<?> argumentBlockPosition();

    Entity getArgumentSingleEntity(Object context, String name) throws CommandSyntaxException;

    Set<Entity> getArgumentEntities(Object context, String name) throws CommandSyntaxException;

    Set<Entity> getArgumentOptionalEntities(Object context, String name) throws CommandSyntaxException;

    Player getArgumentSinglePlayer(Object context, String name) throws CommandSyntaxException;

    Set<Player> getArgumentPlayers(Object context, String name) throws CommandSyntaxException;

    Set<Player> getArgumentOptionalPlayers(Object context, String name) throws CommandSyntaxException;

    float getArgumentAngle(Object context, String name) throws CommandSyntaxException;

    Component getArgumentComponent(Object context, String name);

    World getArgumentWorld(Object context, String name) throws CommandSyntaxException;

    Component getArgumentMessage(Object context, String name) throws CommandSyntaxException;

    GameMode getArgumentGameMode(Object context, String name) throws CommandSyntaxException;

    UUID getArgumentUuid(Object context, String name) throws CommandSyntaxException;

    Vector getArgumentVector(Object context, String name) throws CommandSyntaxException;

    Particle getArgumentParticle(Object context, String name);

    BlockInput getArgumentBlock(Object context, String name);

    ItemInput getArgumentItem(Object context, String name);

    ItemPredicate getArgumentItemPredicate(Object context, String name) throws CommandSyntaxException;

    Enchantment getArgumentEnchantment(Object context, String name) throws CommandSyntaxException;

    EntityType getArgumentEntityType(Object context, String name) throws CommandSyntaxException;

    EntityType getArgumentSummonableEntityType(Object context, String name) throws CommandSyntaxException;

    PotionEffectType getArgumentPotionEffectType(Object context, String name) throws CommandSyntaxException;

    List<PlayerProfile> getArgumentPlayerProfiles(Object context, String name) throws CommandSyntaxException;

    NbtBase getArgumentNbt(Object context, String name);

    NbtCompound getArgumentNbtCompound(Object context, String name);

    Attribute getArgumentAttribute(Object context, String name) throws CommandSyntaxException;

    Location getArgumentBlockPosition(Object context, String name) throws CommandSyntaxException;

    Location getArgumentSpawnableBlockPosition(Object context, String name) throws CommandSyntaxException;

    Location getArgumentLoadedBlockPosition(Object context, String name) throws CommandSyntaxException;

    Location getArgumentLoadedBlockPosition(Object context, World world, String name) throws CommandSyntaxException;

    SuggestionProvider<?> getSummonableEntitiesProvider();

    RequiredArgumentBuilder<?, ?> createArgumentCommand(String name, ArgumentType<?> type);

    LiteralArgumentBuilder<?> createLiteralCommand(String name);

    CommandSender commandSourceStackGetBukkitSender(Object object);

    boolean commandSourceStackIsPlayer(Object object);

    Player commandSourceStackGetPlayerOrException(Object object) throws CommandSyntaxException;

    SimpleCommandExceptionType commandSourceStackNotPlayerException();

    Message componentAsVanilla(Component component);

    Component componentAsAdventure(Object object);

    Object getBukkitCommands();

    Object createVanillaCommandWrapper(Object commands, CommandNode<?> commandNode);

    NbtCompound compoundNbt();

    NbtList listNbt();

    NbtEnd endNbt();

    NbtString stringNbt(String value);

    NbtByte byteNbt(byte value);

    NbtShort shortNbt(short value);

    NbtInt intNbt(int value);

    NbtLong longNbt(long value);

    NbtFloat floatNbt(float value);

    NbtDouble doubleNbt(double value);

    NbtByteArray byteArrayNbt(byte[] value);

    NbtByteArray byteArrayNbt(List<Byte> value);

    NbtIntArray intArrayNbt(int[] value);

    NbtIntArray intArrayNbt(List<Integer> value);

    NbtLongArray longArrayNbt(long[] value);

    NbtLongArray longArrayNbt(List<Long> value);

    void sendRemovePlayerPacket(Player receiver, UUID who);

    void addPlayerList(Player receiver, List<TabListContent> contents);

    void addActualPlayers(Player receiver);

    List<Skin> getPlayerSkin(Player player);

    void injectPlayer(Object object, Player player, Set<Channel> injected);

    boolean isPacketLoginOutSuccess(Object packet);

    UUID getUUIDFromPacketLoginOutSuccess(Object packet);

    List<?> getNetworkManagers();

    Channel getChannelWithNetworkManager(Object networkManager);

    void injectNetworkManager(ProtocolManager protocolManager, Logger logger, Object networkManager, Set<Channel> injected);

    Channel getPlayerChannel(Player player);

    boolean packetIsClientboundPlayerChat(Object object);

    boolean packetIsClientboundDisconnect(Object object);

    ClientboundPlayerChatPacket packetClientboundPlayerChatPacketAsCandy(Object packet);

    ClientboundDisconnectPacket packetClientboundDisconnectPacketAsCandy(Object packet);

    Object packetAsVanilla(Packet packet);

    void sendPacket(Player player, Packet packet);

    Object createNewScoreboard();

    Object createNewScoreboardObjective(Object scoreboard, String name, Component displayName);

    Object createNewScoreboardScoreHolder(String name, ScoreContent content);

    Object createNewScoreboardPlayerTeam(Object scoreboard, String name, Object holder);

    Object createNewScoreAccess(Object scoreboard, Object objective, Object scoreHolder);

    void updateObjectiveDisplayName(Object objective, Component displayName);

    void removeScoreboardObjective(Object scoreboard, Object objective);

    void updateDisplaySlot(Object scoreboard, DisplaySlot displaySlot, Object objective);

    void updatePlayerTeamPrefix(Object playerTeam, Component prefix);

    void updatePlayerTeamSuffix(Object playerTeam, Component suffix);

    int scoreboardScoreAccessGet(Object scoreAccess);

    void scoreboardScoreAccessSet(Object scoreAccess, int score);

    int scoreboardScoreAccessAdd(Object scoreAccess, int amount);

    int scoreboardScoreAccessIncrement(Object scoreAccess);

    void scoreboardScoreAccessReset(Object scoreAccess);

    void scoreboardScoreAccessUpdateDisplayName(Object scoreAccess, Component displayName);

    void removeScoreAccess(Object scoreboard, Object objective, Object scoreHolder);

    void sendSetObjectivePacketAdd(Player player, BukkitObjective objective);

    void sendSetObjectivePacketRemove(Player player, BukkitObjective objective);

    void sendSetObjectivePacketChange(Player player, BukkitObjective objective);

    void sendSetDisplayObjectivePacket(Player player, DisplaySlot slot, BukkitObjective objective);

    void sendSetPlayerTeamPacket_Add(Player player, BukkitScoreContent content);

    void sendSetPlayerTeamPacket_Modify(Player player, BukkitScoreContent content);

    void sendSetPlayerTeamPacket_Remove(Player bukkitPlayer, BukkitScoreContent content);

    default void sendRemoveScore(Player player, BukkitObjective objective, BukkitScore score, Component component) {
    }

    void sendSetScorePacket(Player player, BukkitObjective objective, BukkitScore score);

    NbtCompound getNbt(ItemStack itemStack);

    NbtCompound getOrCreateNbt(ItemStack itemStack);

    ItemStack setNbt(ItemStack itemStack, NbtCompound nbtCompound);

    JsonObject asJson(NbtCompound compound);

    NbtCompound asNbt(JsonObject json);

    ArgumentType<?> argumentRotation();

    Rotation getArgumentRotation(Object context, String name);

    World createBukkitWorld(WorldCreator creator, BukkitWorldInitializer initializer);

    Environment registerEnvironment(BukkitEnvironmentBuilder builder);

    BukkitEnvironmentBuilder copyEnvironment(BukkitEnvironment environment, BukkitEnvironmentBuilder builder);

    default World createFoliaWorld(WorldCreator creator, BukkitWorldInitializer initializer) {
        return creator.createWorld();
    }

    Object worldLevelStemOverworld();

    Object worldLevelStemNether();

    Object worldLevelStemTheEnd();

    default Object worldLevelStem(Object key) {
        return key;
    }

    static NmsAccessor getAccessor() {
        return CandyLibrary.getLibrary().getService(NmsAccessor.class);
    }

    boolean isVanillaCommandWrapper(Command command);

    RootCommandNode<?> getRootCommandNode();

    ItemStack newCraftItemStack(Material type);

}
