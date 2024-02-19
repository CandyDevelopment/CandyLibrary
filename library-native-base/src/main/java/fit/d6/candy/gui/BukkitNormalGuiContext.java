package fit.d6.candy.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiContext;
import fit.d6.candy.api.gui.normal.NormalGuiRenderer;
import fit.d6.candy.api.gui.normal.NormalGuiScene;
import org.jetbrains.annotations.NotNull;

public class BukkitNormalGuiContext extends BukkitGuiContext<NormalGui, NormalGuiRenderer, NormalGuiScene> implements NormalGuiContext {

    private final BukkitNormalGuiScene scene;

    public BukkitNormalGuiContext(BukkitNormalGuiScene bukkitNormalGuiScene, GuiAudience audience) {
        super(audience);
        this.scene = bukkitNormalGuiScene;
    }

    @Override
    public @NotNull NormalGuiScene getScene() {
        return this.scene;
    }

}
