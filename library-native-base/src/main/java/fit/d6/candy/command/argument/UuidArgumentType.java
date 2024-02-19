package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class UuidArgumentType extends BukkitArgumentType {

    public final static UuidArgumentType UUID = new UuidArgumentType();

    private UuidArgumentType() {
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentUuid();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.UUID;
    }
}
