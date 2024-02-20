package fit.d6.candy.api.visual.tablist;

import fit.d6.candy.api.visual.VisualService;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TabList {

    @NotNull
    List<@NotNull TabListContent> listContents();

    void addContent(@NotNull TabListContent content);

    void setHeader(@NotNull Component header);

    void setFooter(@NotNull Component footer);

    @NotNull
    Component getHeader();

    @NotNull
    Component getFooter();

    boolean shouldSendActualPlayers();

    @NotNull
    TabList shouldSendActualPlayers(boolean ifShould);

    @NotNull
    static TabList of() {
        return VisualService.getService().getTabListManager().createTabList();
    }

}
