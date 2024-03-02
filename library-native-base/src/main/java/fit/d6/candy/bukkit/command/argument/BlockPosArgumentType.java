package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class BlockPosArgumentType extends BukkitArgumentType {

    public final static BlockPosArgumentType BLOCK_POS = new BlockPosArgumentType();

    private final ArgumentType<?> argument;

    public BlockPosArgumentType() {
        this.argument = NmsAccessor.getAccessor().argumentBlockPosition();
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return argument;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.BLOCK_POSITION;
    }
}
