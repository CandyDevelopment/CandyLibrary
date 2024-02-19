package fit.d6.candy.api.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface CommandContext {

    @NotNull
    CommandArgumentHelper getArgument();

    @NotNull
    CommandSender getSender();

    @NotNull
    Player getPlayer() throws CommandSyntaxException;

    @NotNull
    String getAlias();

    boolean isPlayer();

    String getRawCommand();

}
