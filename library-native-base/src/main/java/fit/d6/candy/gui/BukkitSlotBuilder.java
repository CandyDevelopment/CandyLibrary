package fit.d6.candy.gui;

import fit.d6.candy.api.gui.slot.Clicker;
import fit.d6.candy.api.gui.slot.Image;
import fit.d6.candy.api.gui.slot.SlotBuilder;
import org.jetbrains.annotations.NotNull;

public class BukkitSlotBuilder implements SlotBuilder {

    private Image image;
    private Clicker clicker;

    @Override
    public @NotNull SlotBuilder image(@NotNull Image image) {
        this.image = image;
        return this;
    }

    @Override
    public @NotNull SlotBuilder clicker(@NotNull Clicker clicker) {
        this.clicker = clicker;
        return this;
    }

    @Override
    public @NotNull BukkitSlot build() {
        return new BukkitSlot(this.image, this.clicker);
    }

}
