package fit.d6.candy.api.player;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

public interface SkinManager {

    BuiltinSkins getBuiltinSkins();

    @NotNull
    Skin getSkin(@NotNull String texture, @NotNull String signature);

    @NotNull
    List<@NotNull Skin> getSkin(@NotNull Player player);

    @NotNull
    Future<@NotNull List<@NotNull Skin>> getSkin(@NotNull UUID onlinePlayerUuid);

    @NotNull
    Future<@NotNull List<@NotNull Skin>> getSkin(@NotNull String onlinePlayerName);

}
