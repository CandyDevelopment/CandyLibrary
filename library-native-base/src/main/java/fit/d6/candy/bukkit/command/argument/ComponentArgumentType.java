package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class ComponentArgumentType extends BukkitArgumentType {

    public final static ComponentArgumentType COMPONENT = new ComponentArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentComponent();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.COMPONENT;
    }
}
