package fit.d6.candy.api.gui;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GuiInitializer<G extends Gui, GI extends GuiInitializer<G, GI>> {

    @NotNull
    GI title(@Nullable Component component);

    @NotNull
    GI closeListener(@NotNull CloseListener listener);

    @NotNull
    GI movingItemListener(@NotNull MovingItemListener listener);

    @NotNull
    G initialize();

}
