package fit.d6.candy.api.gui.slot;

import org.jetbrains.annotations.NotNull;

public interface SlotBuilder {

    @NotNull
    SlotBuilder image(@NotNull Image image);

    @NotNull
    SlotBuilder clicker(@NotNull Clicker clicker);

    @NotNull
    Slot build();

}
