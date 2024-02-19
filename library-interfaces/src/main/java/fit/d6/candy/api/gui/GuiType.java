package fit.d6.candy.api.gui;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface GuiType<G extends Gui, GI extends GuiInitializer<G, GI>> {

    @NotNull
    GI create(@NotNull Plugin plugin);

}
