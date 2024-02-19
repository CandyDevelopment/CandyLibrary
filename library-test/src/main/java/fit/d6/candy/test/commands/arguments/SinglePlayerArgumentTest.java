package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class SinglePlayerArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("single_player")
                        .then(
                                commandManager.createCommand("value", argumentManager.singlePlayer())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("Well, you selected... uhh, a player: " + argument.getSinglePlayer("value").getName());
                                            return 1;
                                        })
                        )
        );
    }

}
