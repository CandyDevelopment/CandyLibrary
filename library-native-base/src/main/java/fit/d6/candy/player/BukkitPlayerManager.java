package fit.d6.candy.player;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.player.PlayerManager;
import fit.d6.candy.api.player.PlayerService;
import fit.d6.candy.api.protocol.packet.Packet;
import fit.d6.candy.api.visual.VisualService;
import fit.d6.candy.api.visual.scoreboard.Objective;
import fit.d6.candy.api.visual.scoreboard.Score;
import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.api.visual.tablist.TabList;
import fit.d6.candy.api.visual.tablist.TabListContent;
import fit.d6.candy.nms.NmsAccessor;
import fit.d6.candy.visual.scoreboard.BukkitObjective;
import fit.d6.candy.visual.scoreboard.BukkitScore;
import fit.d6.candy.visual.scoreboard.BukkitScoreContent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BukkitPlayerManager implements PlayerManager, Listener {

    final Map<Player, List<UUID>> showedUuids = new HashMap<>();
    final Map<Player, List<BukkitObjective>> showedObjectives = new HashMap<>();
    final Map<Player, Map<BukkitScoreContent, Component>> showedScoreContents = new HashMap<>();

    @EventHandler
    public void join(PlayerJoinEvent event) {
        for (List<UUID> list : showedUuids.values())
            list.add(event.getPlayer().getUniqueId());

        showedUuids.put(event.getPlayer(), new ArrayList<>());
        showedUuids.get(event.getPlayer()).addAll(Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList());

        this.showedObjectives.put(event.getPlayer(), new ArrayList<>());
        this.showedScoreContents.put(event.getPlayer(), new HashMap<>());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        showedUuids.remove(event.getPlayer());
    }

    @Override
    public void sendPacket(@NotNull Player player, @NotNull Packet packet) {
        NmsAccessor.getAccessor().sendPacket(player, packet);
    }

    @Override
    public void sendScoreboard(@NotNull Player player, @NotNull Scoreboard scoreboard) {
        NmsAccessor accessor = NmsAccessor.getAccessor();
        for (DisplaySlot slot : DisplaySlot.values()) {
            accessor.sendSetDisplayObjectivePacket(player, slot, null);
        }
        for (BukkitObjective objective : this.showedObjectives.get(player))
            if (scoreboard.hasObjective(objective))
                accessor.sendSetObjectivePacketChange(player, objective);
        for (BukkitObjective objective : scoreboard.listObjectives().stream().map(objective -> (BukkitObjective) objective).toList()) {
            if (!this.showedObjectives.get(player).contains(objective)) {
                this.showedObjectives.get(player).add(objective);
                accessor.sendSetObjectivePacketAdd(player, objective);
            }
        }
        for (Map.Entry<DisplaySlot, Objective> entry : scoreboard.listDisplays().entrySet()) {
            accessor.sendSetDisplayObjectivePacket(player, entry.getKey(), (BukkitObjective) entry.getValue());
        }
        for (BukkitScoreContent content : this.showedScoreContents.get(player).keySet()) {
            if (scoreboard.hasContent(content)) {
                accessor.sendSetPlayerTeamPacket_Modify(player, content);
            }
        }
        for (Objective objective : scoreboard.listObjectives()) {
            for (Score score : objective.listContents()) {
                if (!this.showedScoreContents.get(player).containsKey((BukkitScoreContent) score.getContent())) {
                    accessor.sendSetPlayerTeamPacket_Add(player, (BukkitScoreContent) score.getContent());
                    this.showedScoreContents.get(player).put((BukkitScoreContent) score.getContent(), score.getContent().getDisplayName());
                }
                accessor.sendRemoveScore(player, (BukkitObjective) objective, (BukkitScore) score, this.showedScoreContents.get(player).get((BukkitScoreContent) score.getContent()));
                accessor.sendSetScorePacket(player, (BukkitObjective) objective, (BukkitScore) score);
            }
        }
    }

    @Override
    public void clearScoreboard(@NotNull Player player) {
        NmsAccessor accessor = NmsAccessor.getAccessor();
        for (DisplaySlot slot : DisplaySlot.values()) {
            accessor.sendSetDisplayObjectivePacket(player, slot, null);
        }
        for (BukkitScoreContent content : this.showedScoreContents.get(player).keySet()) {
            accessor.sendSetPlayerTeamPacket_Remove(player, content);
        }
        for (BukkitObjective objective : this.showedObjectives.get(player)) {
            accessor.sendSetObjectivePacketRemove(player, objective);
        }
        this.showedObjectives.get(player).clear();
        this.showedScoreContents.get(player).clear();
    }

    @Override
    public void clearTabList(@NotNull Player player) {
        for (UUID uuid : showedUuids.get(player)) {
            NmsAccessor.getAccessor().sendRemovePlayerPacket(player, uuid);
        }
        this.showedUuids.get(player).clear();
    }

    @Override
    public void sendTabList(@NotNull Player player, @NotNull TabList tabList) {
        this.clearTabList(player);

        this.showedUuids.get(player).addAll(tabList.listContents().stream().map(TabListContent::getUniqueId).toList());

        NmsAccessor.getAccessor().addPlayerList(player, tabList.listContents());

        if (tabList.shouldSendActualPlayers())
            NmsAccessor.getAccessor().addActualPlayers(player);

        player.sendPlayerListHeaderAndFooter(tabList.getHeader(), tabList.getFooter());
    }

    @Override
    public void resetTabList(@NotNull Player player) {
        for (UUID uuid : showedUuids.get(player)) {
            NmsAccessor.getAccessor().sendRemovePlayerPacket(player, uuid);
        }
        this.showedUuids.get(player).clear();
        showedUuids.get(player).addAll(Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList());
        NmsAccessor.getAccessor().addActualPlayers(player);
        player.sendPlayerListHeaderAndFooter(Component.empty(), Component.empty());
    }

    @Override
    public @NotNull TabListContent getAsTabListContent(@NotNull Player player) {
        return CandyLibrary.getLibrary().getService(VisualService.class).getTabListManager().createTabListContent(
                player.displayName()
        ).setSkin(CandyLibrary.getLibrary().getService(PlayerService.class).getSkinManager().getSkin(player).get(0));
    }


}
