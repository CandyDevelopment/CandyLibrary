package fit.d6.candy.api.gui;

import fit.d6.candy.api.gui.slot.SlotBuilder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface GuiRenderer<G extends Gui, GR extends GuiRenderer<G, GR, GS>, GS extends GuiScene<G, GR, GS>> {

    boolean isFull();

    boolean isEmpty();

    void addSlot(@NotNull Consumer<SlotBuilder> consumer);

    @NotNull
    GR title(@Nullable Component component);

}
