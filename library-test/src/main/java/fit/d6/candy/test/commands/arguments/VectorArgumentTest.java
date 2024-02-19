package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class VectorArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("vector")
                        .then(
                                commandManager.createCommand("value", argumentManager.vector(true))
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().teleport(argument.getVector("value").toLocation(context.getPlayer().getWorld()));
                                            return 1;
                                        })
                        )
        );
    }

}
