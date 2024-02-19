package fit.d6.candy.api.visual.tablist;

import fit.d6.candy.api.player.Skin;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface TabListContent {

    @NotNull
    UUID getUniqueId();

    @NotNull
    Component getText();

    @Nullable
    Skin getSkin();

    int getPing();

    @NotNull
    TabListContent setText(@NotNull Component text);

    @NotNull
    TabListContent setSkin(@Nullable Skin skin);

    TabListContent setPing(int value);

}
