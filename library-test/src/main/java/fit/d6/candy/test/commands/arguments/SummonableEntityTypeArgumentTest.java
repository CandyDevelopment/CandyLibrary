package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class SummonableEntityTypeArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("summonable_entity_type")
                        .then(
                                commandManager.createCommand("value", argumentManager.summonableEntityType())
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().getWorld().spawnEntity(context.getPlayer().getLocation(), argument.getSummonableEntityType("value"));
                                            return 1;
                                        })
                        )
        );
    }

}
