package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class BlockStateArgumentType extends BukkitArgumentType {

    public final static BlockStateArgumentType BLOCK_STATE = new BlockStateArgumentType();

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.BLOCK;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentBlock();
    }
}
