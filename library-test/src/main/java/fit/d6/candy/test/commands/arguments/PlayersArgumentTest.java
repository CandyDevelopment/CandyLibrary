package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.entity.Player;

public class PlayersArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("players")
                        .then(
                                commandManager.createCommand("value", argumentManager.players())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("You selected: " + argument.getPlayers("value").stream().map(Player::getName).toList());
                                            return 1;
                                        })
                        )
        );
    }

}
