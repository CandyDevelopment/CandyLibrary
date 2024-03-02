package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.Gui;
import fit.d6.candy.api.gui.GuiRenderer;
import fit.d6.candy.api.gui.GuiScene;

public abstract class BukkitGuiRenderer<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> implements GuiRenderer<G, GR, GS> {
}
