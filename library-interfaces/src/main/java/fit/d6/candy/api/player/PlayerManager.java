package fit.d6.candy.api.player;

import fit.d6.candy.api.protocol.packet.Packet;
import fit.d6.candy.api.visual.scoreboard.Scoreboard;
import fit.d6.candy.api.visual.tablist.TabList;
import fit.d6.candy.api.visual.tablist.TabListContent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerManager {

    void sendPacket(@NotNull Player player, @NotNull Packet packet);

    void sendScoreboard(@NotNull Player player, @NotNull Scoreboard scoreboard);

    void clearScoreboard(@NotNull Player player);

    void clearTabList(@NotNull Player player);

    void sendTabList(@NotNull Player player, @NotNull TabList tabList);

    void resetTabList(@NotNull Player player);

    @NotNull
    TabListContent getAsTabListContent(@NotNull Player player);

}
