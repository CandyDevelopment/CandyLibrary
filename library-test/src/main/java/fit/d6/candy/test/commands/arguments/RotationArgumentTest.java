package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.position.Rotation;

public class RotationArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("rotation")
                        .then(
                                commandManager.createCommand("value", argumentManager.rotation())
                                        .executesPlayer((context, argument) -> {
                                            Rotation rotation = argument.getRotation("value");
                                            context.getPlayer().setRotation(rotation.getYaw(), rotation.getPitch());
                                            return 1;
                                        })
                        )
        );
    }

}
