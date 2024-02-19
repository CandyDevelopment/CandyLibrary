package fit.d6.candy.command;

import com.mojang.brigadier.tree.CommandNode;
import fit.d6.candy.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class BukkitCommand implements Command {

    private final CommandNode<Object> commandNode;

    public BukkitCommand(CommandNode<Object> commandNode) {
        this.commandNode = commandNode;
    }

    public CommandNode<Object> toBrigadier() {
        return this.commandNode;
    }

    @Override
    public @NotNull String getName() {
        return this.commandNode.getName();
    }

}
