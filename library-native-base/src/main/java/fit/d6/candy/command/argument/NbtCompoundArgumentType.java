package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class NbtCompoundArgumentType extends BukkitArgumentType {

    public final static NbtCompoundArgumentType NBT_COMPOUND = new NbtCompoundArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentNbtCompound();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.NBT_COMPOUND;
    }

}
