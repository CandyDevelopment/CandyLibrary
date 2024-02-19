package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class NbtArgumentType extends BukkitArgumentType {

    public final static NbtArgumentType NBT = new NbtArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentNbt();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.NBT;
    }

}
