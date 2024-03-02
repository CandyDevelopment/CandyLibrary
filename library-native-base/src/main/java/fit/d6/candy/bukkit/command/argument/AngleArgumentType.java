package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class AngleArgumentType extends BukkitArgumentType {

    public final static AngleArgumentType ANGLE = new AngleArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentAngle();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.ANGLE;
    }

}
