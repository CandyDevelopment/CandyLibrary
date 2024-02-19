package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class GameModeArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("game_mode")
                        .then(
                                commandManager.createCommand("value", argumentManager.gameMode())
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().setGameMode(argument.getGameMode("value"));
                                            return 1;
                                        })
                        )
        );
    }

}
