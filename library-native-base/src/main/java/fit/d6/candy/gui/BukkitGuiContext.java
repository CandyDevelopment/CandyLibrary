package fit.d6.candy.gui;

import fit.d6.candy.api.gui.*;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitGuiContext<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> implements GuiContext<G, GR, GS> {

    private final GuiAudience audience;

    public BukkitGuiContext(GuiAudience audience) {
        this.audience = audience;
    }

    @Override
    public @NotNull GuiAudience getAudience() {
        return this.audience;
    }

}
