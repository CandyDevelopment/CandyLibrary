package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class IntegerArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("integer")
                        .then(
                                commandManager.createCommand("value", argumentManager.integerArg(-5, 15))
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("Your value is " + argument.getInteger("value"));
                                            return 1;
                                        })
                        )
        );
    }
}
