package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class StringArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("string")
                        .then(
                                commandManager.createCommand("string")
                                        .then(
                                                commandManager.createCommand("value", argumentManager.stringArg())
                                                        .suggests((context, argument, suggestion) -> {
                                                            suggestion.suggests("example", Component.text("Just to test the tooltips"));
                                                            suggestion.suggests("test", Component.text("test with color").color(NamedTextColor.GOLD));
                                                            suggestion.suggests("DeeChael", Component.text("The main maintainer of Candy Library"));
                                                        })
                                                        .executes(((context, argument) -> {
                                                            context.getSender().sendMessage("You sent " + argument.getString("value"));
                                                            return 1;
                                                        }))
                                        )
                        )
                        .then(
                                commandManager.createCommand("word")
                                        .then(
                                                commandManager.createCommand("value", argumentManager.wordStringArg())
                                                        .executes(((context, argument) -> {
                                                            context.getSender().sendMessage("You sent " + argument.getString("value"));
                                                            return 1;
                                                        }))
                                        )
                        )
                        .then(
                                commandManager.createCommand("greedy")
                                        .then(
                                                commandManager.createCommand("value", argumentManager.greedyStringArg())
                                                        .executes(((context, argument) -> {
                                                            context.getSender().sendMessage("You sent " + argument.getString("value"));
                                                            return 1;
                                                        }))
                                        )
                        )
        );
    }

}
