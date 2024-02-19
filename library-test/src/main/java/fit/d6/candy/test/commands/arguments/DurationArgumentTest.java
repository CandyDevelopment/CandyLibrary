package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DurationArgumentTest {

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("duration")
                        .then(
                                commandManager.createCommand("value", argumentManager.duration())
                                        .executes((context, argument) -> {
                                            context.getSender().sendMessage("After now is: " + FORMAT.format(argument.getDuration("value").after(new Date())));
                                            return 1;
                                        })
                        )
                        .then(
                                commandManager.createCommand("forever")
                                        .executes((context, argument) -> {
                                            return 1;
                                        })
                        )
        );
    }

}
