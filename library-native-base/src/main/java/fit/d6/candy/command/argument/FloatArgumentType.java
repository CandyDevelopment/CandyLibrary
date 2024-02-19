package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class FloatArgumentType extends BukkitArgumentType {

    private final com.mojang.brigadier.arguments.FloatArgumentType argumentType;

    public FloatArgumentType(com.mojang.brigadier.arguments.FloatArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    @Override
    public ArgumentType<Float> toBrigadier() {
        return this.argumentType;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.FLOAT;
    }
}
