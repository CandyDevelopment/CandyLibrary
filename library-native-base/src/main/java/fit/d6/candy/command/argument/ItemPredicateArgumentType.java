package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class ItemPredicateArgumentType extends BukkitArgumentType {

    public final static ItemPredicateArgumentType ITEM_PREDICATE = new ItemPredicateArgumentType();

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.ITEM_PREDICATE;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentItemPredicate();
    }
}
