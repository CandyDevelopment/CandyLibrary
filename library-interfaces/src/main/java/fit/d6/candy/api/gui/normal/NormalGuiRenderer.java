package fit.d6.candy.api.gui.normal;

import fit.d6.candy.api.gui.GuiRenderer;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface NormalGuiRenderer extends GuiRenderer<NormalGui, NormalGuiRenderer, NormalGuiScene> {

    boolean hasSlot(int i);

    @NotNull
    NormalGuiRenderer slot(int i, @NotNull Consumer<SlotBuilder> consumer);

    @NotNull
    NormalGuiScene render();

}
