package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.potion.PotionEffect;

public class PotionEffectArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("potion_effect")
                        .then(
                                commandManager.createCommand("value", argumentManager.potionEffectType())
                                        .executesPlayer((context, argument) -> {
                                            context.getPlayer().addPotionEffect(new PotionEffect(
                                                    argument.getPotionEffectType("value"),
                                                    100,
                                                    0
                                            ));
                                            return 1;
                                        })
                        )
        );
    }

}
