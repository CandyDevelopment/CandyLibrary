package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.slot.Clicker;
import fit.d6.candy.api.gui.slot.Image;
import fit.d6.candy.api.gui.slot.Slot;

public class BukkitSlot implements Slot {

    private final Image image;
    private final Clicker clicker;

    public BukkitSlot(Image image, Clicker clicker) {
        this.image = image;
        this.clicker = clicker;
    }

    public Image getImage() {
        return image;
    }

    public Clicker getClicker() {
        return clicker;
    }

}
