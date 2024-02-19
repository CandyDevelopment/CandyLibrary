package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;


public class MessageArgumentType extends BukkitArgumentType {

    public final static MessageArgumentType MESSAGE = new MessageArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentMessage();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.MESSAGE;
    }
}
