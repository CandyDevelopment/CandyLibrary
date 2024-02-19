package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class BooleanArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("boolean")
                        .then(
                                commandManager.createCommand("value", argumentManager.booleanType())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("You are " + (argument.getBoolean("value") ? "" : "not ") + "the right one");
                                            return 1;
                                        })
                        )
        );
    }

}
