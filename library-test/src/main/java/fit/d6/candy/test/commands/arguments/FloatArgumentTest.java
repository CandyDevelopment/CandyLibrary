package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class FloatArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("float")
                        .then(
                                commandManager.createCommand("value", argumentManager.floatArg(-16.5f, 239.67f))
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("Your value is " + argument.getFloat("value"));
                                            return 1;
                                        })
                        )
        );
    }
}
