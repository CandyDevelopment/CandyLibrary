package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class StringArgumentType extends BukkitArgumentType {

    public final static StringArgumentType STRING = new StringArgumentType(com.mojang.brigadier.arguments.StringArgumentType.string());
    public final static StringArgumentType WORD = new StringArgumentType(com.mojang.brigadier.arguments.StringArgumentType.word());
    public final static StringArgumentType GREEDY_STRING = new StringArgumentType(com.mojang.brigadier.arguments.StringArgumentType.greedyString());

    private final com.mojang.brigadier.arguments.StringArgumentType brigadier;

    private StringArgumentType(com.mojang.brigadier.arguments.StringArgumentType stringArgumentType) {
        this.brigadier = stringArgumentType;
    }

    @Override
    public ArgumentType<String> toBrigadier() {
        return brigadier;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.STRING;
    }
}
