package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class ItemArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("item")
                        .then(
                                commandManager.createCommand("value", argumentManager.item())
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().getInventory().addItem(argument.getItem("value").createItemStack(1, true));
                                            return 1;
                                        })
                        )
        );
    }

}
