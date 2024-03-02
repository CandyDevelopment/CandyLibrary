package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class RotationArgumentType extends BukkitArgumentType {

    public final static RotationArgumentType ROTATION = new RotationArgumentType();

    private final ArgumentType<?> argument;

    public RotationArgumentType() {
        this.argument = NmsAccessor.getAccessor().argumentRotation();
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return argument;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.ROTATION;
    }
}
