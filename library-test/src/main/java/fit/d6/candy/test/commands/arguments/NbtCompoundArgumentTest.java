package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.nbt.NbtService;

public class NbtCompoundArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("nbt_compound")
                        .then(
                                commandManager.createCommand("value", argumentManager.nbtCompound())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("Your nbt is " + NbtService.getService().asJson(argument.getNbtCompound("value")));
                                            return 1;
                                        })
                        )
        );
    }

}
