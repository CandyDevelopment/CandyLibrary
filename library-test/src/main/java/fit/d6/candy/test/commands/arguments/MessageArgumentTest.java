package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import net.kyori.adventure.text.Component;

public class MessageArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("message")
                        .then(
                                commandManager.createCommand("value", argumentManager.message())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage(Component.text("Your message is - ").append(argument.getMessage("value")));
                                            return 1;
                                        })
                        )
        );
    }

}
