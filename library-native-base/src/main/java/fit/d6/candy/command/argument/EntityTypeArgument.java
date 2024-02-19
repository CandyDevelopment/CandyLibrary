package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class EntityTypeArgument extends BukkitArgumentType {

    public final static EntityTypeArgument ENTITY_TYPE = new EntityTypeArgument();

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.ENTITY_TYPE;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentEntityType();
    }

}
