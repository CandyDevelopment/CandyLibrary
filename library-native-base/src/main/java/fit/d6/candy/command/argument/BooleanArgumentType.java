package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class BooleanArgumentType extends BukkitArgumentType {

    public final static BooleanArgumentType BOOL = new BooleanArgumentType();

    private final static BoolArgumentType ARGUMENT_TYPE = BoolArgumentType.bool();

    private BooleanArgumentType() {
    }

    @Override
    public ArgumentType<Boolean> toBrigadier() {
        return ARGUMENT_TYPE;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.BOOLEAN;
    }
}
