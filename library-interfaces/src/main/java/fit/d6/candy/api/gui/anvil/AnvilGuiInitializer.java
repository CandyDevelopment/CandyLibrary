package fit.d6.candy.api.gui.anvil;

import fit.d6.candy.api.gui.GuiInitializer;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface AnvilGuiInitializer extends GuiInitializer<AnvilGui, AnvilGuiInitializer> {

    @NotNull
    AnvilGuiInitializer slot(@NotNull AnvilSlot slot, @NotNull Consumer<SlotBuilder> consumer);

}
