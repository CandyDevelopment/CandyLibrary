package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class EntityTypeArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("entity_type")
                        .then(
                                commandManager.createCommand("value", argumentManager.entityType())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("The entity type is " + argument.getEntityType("value"));
                                            return 1;
                                        })
                        )
        );
    }

}
