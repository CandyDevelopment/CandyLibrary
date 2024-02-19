package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.Material;

public class BlockPositionArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("block_position")
                        .then(
                                commandManager.createCommand("value", argumentManager.blockPosition())
                                        .executesPlayer((context, argument) -> {
                                            argument.getBlockPosition("value").toLocation(context.getPlayer().getWorld()).getBlock().setType(Material.DIAMOND_BLOCK);
                                            return 1;
                                        })
                        )
        );
    }

}
