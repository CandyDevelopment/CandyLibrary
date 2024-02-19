package fit.d6.candy.api.gui.anvil;

import fit.d6.candy.api.gui.GuiClickContext;
import org.jetbrains.annotations.NotNull;

public interface AnvilGuiClickContext extends GuiClickContext<AnvilGui, AnvilGuiRenderer, AnvilGuiScene>, AnvilGuiContext {

    @NotNull
    String getText();

}
