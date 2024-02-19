package fit.d6.candy.api.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface CommandExecutor {

    int executes(CommandContext context, CommandArgumentHelper argument) throws CommandSyntaxException;

}
