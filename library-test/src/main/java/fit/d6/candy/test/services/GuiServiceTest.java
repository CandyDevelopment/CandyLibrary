package fit.d6.candy.test.services;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.GuiService;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiClickContext;
import fit.d6.candy.api.gui.anvil.AnvilSlot;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiContext;
import fit.d6.candy.test.CandyLibraryTestPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class GuiServiceTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();

        builder.then(
                commandManager.createCommand("gui")
                        .then(
                                commandManager.createCommand("normal")
                                        .executesPlayer((context, argument) -> {

                                            NormalGui gui = GuiService.getService()
                                                    .getGuiManager()
                                                    .normal(6)
                                                    .create(CandyLibraryTestPlugin.getInstance())
                                                    .initialize();

                                            gui.prepare(GuiAudience.getAudience(context.getPlayer()))
                                                    .title(Component.text("普通的 GUI"))
                                                    .slot(4, slotBuilder -> {
                                                        slotBuilder.image(
                                                                (guiContext, itemBuilder) -> {
                                                                    itemBuilder.type(Material.DIAMOND)
                                                                            .displayName(Component.text("Button"))
                                                                            .lore(Component.text(" "), Component.text("Click to refresh"));
                                                                }
                                                        ).clicker(guiContext -> {
                                                            ((NormalGuiContext) guiContext).getScene()
                                                                    .refresh(false)
                                                                    .slot(3, anotherSlotBuilder -> {
                                                                        anotherSlotBuilder.image(
                                                                                (anotherGuiContext, itemBuilder) -> {
                                                                                    itemBuilder.type(Material.BARRIER)
                                                                                            .displayName(Component.text("Click to go back"))
                                                                                            .lore(Component.text("Hello, " + anotherGuiContext.getAudience().getName() + "!"))
                                                                                            .enchanted(true);
                                                                                }
                                                                        ).clicker(anotherContext -> {
                                                                            ((NormalGuiContext) anotherContext).getScene().back().render();
                                                                        });
                                                                    })
                                                                    .render();
                                                        });
                                                    })
                                                    .render();

                                            return 1;
                                        })
                        )
                        .then(
                                commandManager.createCommand("anvil")
                                        .executesPlayer((context, argument) -> {

                                            AnvilGui gui = GuiService.getService()
                                                    .getGuiManager()
                                                    .anvil()
                                                    .create(CandyLibraryTestPlugin.getInstance())
                                                    .slot(AnvilSlot.LEFT_INPUT, slotBuilder -> {
                                                        slotBuilder.image((guiContext, itemBuilder) -> {
                                                            itemBuilder.type(Material.PAPER)
                                                                    .displayName(Component.text("Typing your name..."));
                                                        });
                                                    })
                                                    .slot(AnvilSlot.RESULT, slotBuilder -> {
                                                        slotBuilder.image((guiContext, itemBuilder) -> {
                                                                    itemBuilder.type(Material.DIAMOND)
                                                                            .displayName(Component.text("Click to confirm"));
                                                                })
                                                                .clicker(guiContext -> {
                                                                    guiContext.getAudience().asBukkit().sendMessage("Your name is " + ((AnvilGuiClickContext) guiContext).getText());
                                                                });
                                                    })
                                                    .initialize();

                                            gui.prepare(GuiAudience.getAudience(context.getPlayer()))
                                                    .render();

                                            return 1;
                                        })
                        )
        );
    }

}
