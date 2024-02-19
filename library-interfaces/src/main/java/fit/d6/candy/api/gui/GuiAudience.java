package fit.d6.candy.api.gui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface GuiAudience {

    @NotNull
    UUID getUniqueId();

    @NotNull
    String getName();

    @Nullable
    GuiScene getCurrentScene();

    @NotNull
    Player asBukkit();

    static GuiAudience getAudience(Player player) {
        return GuiService.getService().getAudience(player);
    }

}
