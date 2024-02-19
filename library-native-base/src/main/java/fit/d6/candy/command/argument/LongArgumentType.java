package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class LongArgumentType extends BukkitArgumentType {

    private final com.mojang.brigadier.arguments.LongArgumentType argumentType;

    public LongArgumentType(com.mojang.brigadier.arguments.LongArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    @Override
    public ArgumentType<Long> toBrigadier() {
        return this.argumentType;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.LONG;
    }
}
