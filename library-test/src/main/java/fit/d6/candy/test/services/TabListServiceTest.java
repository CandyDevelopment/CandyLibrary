package fit.d6.candy.test.services;

import com.catppuccin.Palette;
import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.player.BuiltinSkins;
import fit.d6.candy.api.player.PlayerService;
import fit.d6.candy.api.visual.VisualService;
import fit.d6.candy.api.visual.tablist.TabList;
import fit.d6.candy.api.visual.tablist.TabListManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TabListServiceTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();

        builder.then(
                commandManager.createCommand("tablist")
                        .then(
                                commandManager.createCommand("advance")
                                        .executesPlayer((context, argument) -> {
                                            VisualService visualService = CandyLibrary.getLibrary().getService(VisualService.class);

                                            TabListManager tabListManager = visualService.getTabListManager();

                                            BuiltinSkins builtinSkins = CandyLibrary.getLibrary().getService(PlayerService.class).getSkinManager().getBuiltinSkins();

                                            TabList tabList = tabListManager.createTabList();

                                            tabList.shouldSendActualPlayers(true);

                                            tabList.addContent(tabListManager.createTabListContent(Component.text("         Players(").color(
                                                            TextColor.color(Palette.FRAPPE.getGreen().getRGB())
                                                    ).append(Component.text(1).color(TextColor.color(Palette.FRAPPE.getYellow().getRGB()))).append(Component.text(")").color(TextColor.color(Palette.FRAPPE.getGreen().getRGB()))))
                                                    .setSkin(builtinSkins.getLime())
                                                    .setPing(1));

                                            tabList.addContent(CandyLibrary.getLibrary().getService(PlayerService.class).getPlayerManager().getAsTabListContent(context.getPlayer()).setPing(2));

                                            for (int i = 0; i < 18; i++)
                                                tabList.addContent(tabListManager.createTabListContent(Component.text(" ")).setSkin(builtinSkins.getWhite()).setPing(i + 3));

                                            tabList.addContent(tabListManager.createTabListContent(Component.text("         Players(").color(
                                                            TextColor.color(Palette.FRAPPE.getGreen().getRGB())
                                                    ).append(Component.text(1).color(TextColor.color(Palette.FRAPPE.getYellow().getRGB()))).append(Component.text(")").color(TextColor.color(Palette.FRAPPE.getGreen().getRGB()))))
                                                    .setSkin(builtinSkins.getLime())
                                                    .setPing(21));

                                            for (int i = 0; i < 19; i++)
                                                tabList.addContent(tabListManager.createTabListContent(Component.text(" ")).setSkin(builtinSkins.getWhite()).setPing(i + 22));

                                            tabList.addContent(tabListManager.createTabListContent(Component.text("         Status").color(
                                                    TextColor.color(Palette.FRAPPE.getBlue().getRGB())
                                            )).setSkin(builtinSkins.getBlue()).setPing(41));

                                            for (int i = 0; i < 19; i++)
                                                tabList.addContent(tabListManager.createTabListContent(Component.text(" ")).setSkin(builtinSkins.getWhite()).setPing(i + 42));

                                            tabList.addContent(tabListManager.createTabListContent(Component.text("         Rank").color(
                                                    TextColor.color(Palette.FRAPPE.getRed().getRGB())
                                            )).setSkin(builtinSkins.getRed()).setPing(61));

                                            for (int i = 0; i < 19; i++)
                                                tabList.addContent(tabListManager.createTabListContent(Component.text(" ")).setSkin(builtinSkins.getWhite()).setPing(i + 62));

                                            tabList.setHeader(MiniMessage.miniMessage().deserialize(" <newline>       <red><bold>Candy Realms      <reset><newline> "));
                                            tabList.setFooter(MiniMessage.miniMessage().deserialize(" <newline>       <yellow>candy.d6.fit      <reset><newline> "));

                                            CandyLibrary.getLibrary().getService(PlayerService.class).getPlayerManager().sendTabList(context.getPlayer(), tabList);
                                            return 1;
                                        })
                        )
                        .then(
                                commandManager.createCommand("show")
                                        .executesPlayer((context, argument) -> {
                                            VisualService visualService = CandyLibrary.getLibrary().getService(VisualService.class);

                                            TabListManager tabListManager = visualService.getTabListManager();

                                            BuiltinSkins builtinSkins = CandyLibrary.getLibrary().getService(PlayerService.class).getSkinManager().getBuiltinSkins();

                                            TabList tabList = tabListManager.createTabList();
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Dark Gray")).setSkin(builtinSkins.getDarkGray()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Darker Gray")).setSkin(builtinSkins.getDarkerGray()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Dirty Yellow")).setSkin(builtinSkins.getDirtyYellow()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Blue")).setSkin(builtinSkins.getBlue()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Pink")).setSkin(builtinSkins.getPink()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Red")).setSkin(builtinSkins.getRed()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Yellow")).setSkin(builtinSkins.getYellow()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Dark Green")).setSkin(builtinSkins.getDarkGreen()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Dirty Blue")).setSkin(builtinSkins.getDirtyBlue()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Magenta")).setSkin(builtinSkins.getMagenta()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("White")).setSkin(builtinSkins.getWhite()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Black")).setSkin(builtinSkins.getBlack()));
                                            tabList.addContent(tabListManager.createTabListContent(Component.text("Lime")).setSkin(builtinSkins.getLime()));

                                            tabList.setHeader(MiniMessage.miniMessage().deserialize(" <newline>       TabList Header      <reset><newline> "));
                                            tabList.setFooter(MiniMessage.miniMessage().deserialize(" <newline>       TabList Footer      <reset><newline> "));

                                            CandyLibrary.getLibrary().getService(PlayerService.class).getPlayerManager().sendTabList(context.getPlayer(), tabList);
                                            return 1;
                                        })
                        )
                        .then(
                                commandManager.createCommand("reset")
                                        .executesPlayer((context, argument) -> {
                                            CandyLibrary.getLibrary().getService(PlayerService.class).getPlayerManager().resetTabList(context.getPlayer());
                                            return 1;
                                        })
                        )
        );
    }

}
