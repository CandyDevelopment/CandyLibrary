package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import org.jetbrains.annotations.NotNull;

public class DurationArgumentType extends BukkitArgumentType {

    public final static DurationArgumentType DURATION = new DurationArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return StringArgumentType.word();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.DURATION;
    }

}
