package fit.d6.candy.bukkit.visual.tablist;

import fit.d6.candy.api.visual.tablist.TabList;
import fit.d6.candy.api.visual.tablist.TabListContent;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BukkitTabList implements TabList {

    private final List<TabListContent> contents = new ArrayList<>();

    private Component header = Component.empty();
    private Component footer = Component.empty();

    private boolean shouldSendActualPlayers;

    @Override
    public @NotNull List<@NotNull TabListContent> listContents() {
        return new ArrayList<>(this.contents);
    }

    @Override
    public void addContent(@NotNull TabListContent content) {
        this.contents.add(content);
    }

    @Override
    public void setHeader(@NotNull Component header) {
        this.header = header;
    }

    @Override
    public void setFooter(@NotNull Component footer) {
        this.footer = footer;
    }

    @NotNull
    @Override
    public Component getHeader() {
        return header;
    }

    @NotNull
    @Override
    public Component getFooter() {
        return footer;
    }

    @Override
    public boolean shouldSendActualPlayers() {
        return this.shouldSendActualPlayers;
    }

    @Override
    public @NotNull TabList shouldSendActualPlayers(boolean ifShould) {
        this.shouldSendActualPlayers = ifShould;
        return this;
    }

}
