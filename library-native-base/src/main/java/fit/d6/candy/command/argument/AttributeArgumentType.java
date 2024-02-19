package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class AttributeArgumentType extends BukkitArgumentType {

    public final static AttributeArgumentType ATTRIBUTE = new AttributeArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentAttribute();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.ATTRIBUTE;
    }

}
