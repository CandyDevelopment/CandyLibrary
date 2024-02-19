package fit.d6.candy.visual.tablist;

import fit.d6.candy.api.player.Skin;
import fit.d6.candy.api.visual.tablist.TabListContent;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BukkitTabListContent implements TabListContent {

    private final UUID uuid;
    private Component text;
    private Skin skin;
    private int ping = 0;

    public BukkitTabListContent(UUID uuid, Component text) {
        this.uuid = uuid;
        this.text = text;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public @NotNull Component getText() {
        return this.text;
    }

    @Override
    public @Nullable Skin getSkin() {
        return this.skin;
    }

    @Override
    public int getPing() {
        return this.ping;
    }

    @NotNull
    @Override
    public TabListContent setText(@NotNull Component text) {
        this.text = text;
        return this;
    }

    @NotNull
    @Override
    public TabListContent setSkin(@Nullable Skin skin) {
        this.skin = skin;
        return this;
    }

    @Override
    public TabListContent setPing(int value) {
        this.ping = value;
        return this;
    }

}
