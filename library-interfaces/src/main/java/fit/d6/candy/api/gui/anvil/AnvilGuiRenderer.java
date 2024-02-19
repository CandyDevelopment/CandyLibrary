package fit.d6.candy.api.gui.anvil;

import fit.d6.candy.api.gui.GuiRenderer;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface AnvilGuiRenderer extends GuiRenderer<AnvilGui, AnvilGuiRenderer, AnvilGuiScene> {

    boolean hasSlot(@NotNull AnvilSlot slot);

    @NotNull
    AnvilGuiRenderer slot(@NotNull AnvilSlot slot, @NotNull Consumer<SlotBuilder> consumer);

    @NotNull
    AnvilGuiScene render();

}
