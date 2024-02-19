package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class AngleArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("angle")
                        .then(
                                commandManager.createCommand("value", argumentManager.angle())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("I don't know what can this argument type do, just show you the value: " + argument.getAngle("value"));
                                            return 1;
                                        })
                        )
        );
    }

}
