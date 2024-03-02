package fit.d6.candy.bukkit.command;

import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import fit.d6.candy.api.command.CommandArgumentHelper;
import fit.d6.candy.api.command.CommandContext;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BukkitCommandContext implements CommandContext {

    private final com.mojang.brigadier.context.CommandContext<Object> brigadier;
    private final CommandArgumentHelper argumentHelper;

    public BukkitCommandContext(com.mojang.brigadier.context.CommandContext<Object> brigadier, CommandArgumentHelper argumentHelper) {
        this.brigadier = brigadier;
        this.argumentHelper = argumentHelper;
    }

    @Override
    public @NotNull CommandArgumentHelper getArgument() {
        return this.argumentHelper;
    }

    @Override
    public @NotNull CommandSender getSender() {
        return NmsAccessor.getAccessor().commandSourceStackGetBukkitSender(this.brigadier.getSource());
    }

    @Override
    public @NotNull Player getPlayer() throws CommandSyntaxException {
        return NmsAccessor.getAccessor().commandSourceStackGetPlayerOrException(this.brigadier.getSource());
    }

    @Override
    public @NotNull String getAlias() {
        CommandNode<Object> root = this.brigadier.getRootNode();
        for (CommandNode<Object> commandNode : this.brigadier.getNodes().stream().map(ParsedCommandNode::getNode).toList()) {
            if (root.getChildren().contains(commandNode))
                return commandNode.getName();
        }
        return "wtf";
    }

    @Override
    public boolean isPlayer() {
        return NmsAccessor.getAccessor().commandSourceStackIsPlayer(this.brigadier.getSource());
    }

    @Override
    public String getRawCommand() {
        return this.brigadier.getInput();
    }

    public com.mojang.brigadier.context.CommandContext<?> toBrigadier() {
        return this.brigadier;
    }

}
