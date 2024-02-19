package fit.d6.candy.api.protocol;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface ProtocolManager {

    @NotNull
    ProtocolListener createListener(@NotNull Plugin plugin);

}
