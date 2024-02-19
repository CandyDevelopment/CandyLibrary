package fit.d6.candy.api.gui;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Gui {

    @NotNull
    Set<CloseListener> listCloseListeners();

    @NotNull
    Set<MovingItemListener> listMovingItemListeners();

    @NotNull
    Component getDefaultTitle();

}
