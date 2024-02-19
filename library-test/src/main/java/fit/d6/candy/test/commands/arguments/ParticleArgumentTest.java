package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

public class ParticleArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("particle")
                        .then(
                                commandManager.createCommand("value", argumentManager.particle())
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().getWorld().spawnParticle(argument.getParticle("value"), context.getPlayer().getLocation(), 10);
                                            return 1;
                                        })
                        )
        );
    }

}
