package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class SummonableEntityTypeArgument extends BukkitArgumentType {

    public final static SummonableEntityTypeArgument SUMMONABLE_ENTITY_TYPE = new SummonableEntityTypeArgument();

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.SUMMONABLE_ENTITY_TYPE;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentSummonableEntityType();
    }

}
