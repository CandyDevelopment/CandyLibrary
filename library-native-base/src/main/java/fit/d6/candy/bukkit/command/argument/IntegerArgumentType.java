package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class IntegerArgumentType extends BukkitArgumentType {

    private final com.mojang.brigadier.arguments.IntegerArgumentType argumentType;

    public IntegerArgumentType(com.mojang.brigadier.arguments.IntegerArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    @Override
    public ArgumentType<Integer> toBrigadier() {
        return this.argumentType;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.INTEGER;
    }
}
