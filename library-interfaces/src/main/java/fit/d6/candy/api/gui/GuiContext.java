package fit.d6.candy.api.gui;

import org.jetbrains.annotations.NotNull;

public interface GuiContext<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> {

    @NotNull
    GS getScene();

    @NotNull
    GuiAudience getAudience();

}
