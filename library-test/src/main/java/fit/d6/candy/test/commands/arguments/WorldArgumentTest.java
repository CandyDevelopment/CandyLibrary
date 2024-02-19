package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.Location;

public class WorldArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("world")
                        .then(
                                commandManager.createCommand("value", argumentManager.world())
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().teleport(new Location(argument.getWorld("value"), 0, 128, 0));
                                            return 1;
                                        })
                        )
        );
    }

}
