package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.Location;

public class EntitiesArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("entities")
                        .then(
                                commandManager.createCommand("value", argumentManager.entities())
                                        .executesPlayer((context, argument) -> {
                                            Location location = context.getPlayer().getLocation();
                                            argument.getEntities("value")
                                                    .forEach(it -> it.teleportAsync(location));
                                            return 1;
                                        })
                        )
        );
    }

}
