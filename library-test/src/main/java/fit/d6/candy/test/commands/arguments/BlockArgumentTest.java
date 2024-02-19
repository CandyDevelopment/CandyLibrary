package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class BlockArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("block")
                        .then(
                                commandManager.createCommand("value", argumentManager.block())
                                        .executesPlayer((context, argument) -> {
                                            argument.getBlock("value").place(context.getPlayer().getLocation(), 2);
                                            return 1;
                                        })
                        )
        );
    }

}
