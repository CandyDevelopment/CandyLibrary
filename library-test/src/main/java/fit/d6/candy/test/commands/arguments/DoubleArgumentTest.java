package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class DoubleArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("double")
                        .then(
                                commandManager.createCommand("value", argumentManager.doubleArg(-5.7, 18.3))
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("Your value is " + argument.getDouble("value"));
                                            return 1;
                                        })
                        )
        );
    }
}
