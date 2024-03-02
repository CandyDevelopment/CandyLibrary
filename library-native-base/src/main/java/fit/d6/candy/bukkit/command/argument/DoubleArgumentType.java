package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class DoubleArgumentType extends BukkitArgumentType {

    private final com.mojang.brigadier.arguments.DoubleArgumentType argumentType;

    public DoubleArgumentType(com.mojang.brigadier.arguments.DoubleArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    @Override
    public ArgumentType<Double> toBrigadier() {
        return this.argumentType;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.DOUBLE;
    }
}
