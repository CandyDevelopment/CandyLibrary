package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class VectorArgumentType extends BukkitArgumentType {

    public final static VectorArgumentType CENTER = new VectorArgumentType(true);
    public final static VectorArgumentType NO_CENTER = new VectorArgumentType(false);

    private final ArgumentType<?> argument;

    public VectorArgumentType(boolean b) {
        this.argument = NmsAccessor.getAccessor().argumentVector(b);
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return argument;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.VECTOR;
    }
}
