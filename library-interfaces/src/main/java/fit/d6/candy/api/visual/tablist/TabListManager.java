package fit.d6.candy.api.visual.tablist;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface TabListManager {

    @NotNull
    TabList createTabList();

    @NotNull
    TabListContent createTabListContent(@NotNull Component text);

}
