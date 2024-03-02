package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiContext;
import fit.d6.candy.api.gui.anvil.AnvilGuiRenderer;
import fit.d6.candy.api.gui.anvil.AnvilGuiScene;
import org.jetbrains.annotations.NotNull;

public class BukkitAnvilGuiContext extends BukkitGuiContext<AnvilGui, AnvilGuiRenderer, AnvilGuiScene> implements AnvilGuiContext {

    private final BukkitAnvilGuiScene scene;

    public BukkitAnvilGuiContext(GuiAudience audience, BukkitAnvilGuiScene scene) {
        super(audience);
        this.scene = scene;
    }

    @Override
    public @NotNull AnvilGuiScene getScene() {
        return this.scene;
    }

}
