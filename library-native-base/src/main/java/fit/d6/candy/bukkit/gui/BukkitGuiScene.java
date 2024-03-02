package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.Gui;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.GuiRenderer;
import fit.d6.candy.api.gui.GuiScene;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitGuiScene<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> implements GuiScene<G, GR, GS> {

    private final GuiAudience audience;

    public BukkitGuiScene(GuiAudience audience) {
        this.audience = audience;
    }

    @Override
    public @NotNull GuiAudience getAudience() {
        return this.audience;
    }

}
