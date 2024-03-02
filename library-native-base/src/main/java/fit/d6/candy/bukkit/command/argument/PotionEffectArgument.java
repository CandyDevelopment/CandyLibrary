package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class PotionEffectArgument extends BukkitArgumentType {

    public final static PotionEffectArgument MOB_EFFECT = new PotionEffectArgument();

    @Override
    public @NotNull ArgumentTypes getType() {

        return ArgumentTypes.POTION_EFFECT_TYPE;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentPotionEffectType();
    }

}
