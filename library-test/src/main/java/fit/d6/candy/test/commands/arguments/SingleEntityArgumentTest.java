package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SingleEntityArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("single_entity")
                        .then(
                                commandManager.createCommand("value", argumentManager.singleEntity())
                                        .executes((context, argument) -> {
                                            Entity entity = argument.getSingleEntity("value");
                                            if (entity instanceof Player) {
                                                context.getSender().sendMessage("Why you selected a player? The player is " + entity.getName());
                                            } else {
                                                context.getSender().sendMessage("You selected a entity with type " + entity.getType());
                                            }
                                            return 1;
                                        })
                        )
        );
    }

}
