package fit.d6.candy.api.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Requirement {

    boolean check(@NotNull CommandSender sender);

}
