package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class DimensionArgumentType extends BukkitArgumentType {

    public final static DimensionArgumentType DIMENSION = new DimensionArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentWorld();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.WORLD;
    }
}
