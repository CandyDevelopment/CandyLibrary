package fit.d6.candy.api.gui.normal;

import fit.d6.candy.api.gui.GuiInitializer;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface NormalGuiInitializer extends GuiInitializer<NormalGui, NormalGuiInitializer> {

    @NotNull
    NormalGuiInitializer slot(int i, @NotNull Consumer<SlotBuilder> consumer);

}
