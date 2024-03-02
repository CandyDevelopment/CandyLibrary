package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class ParticleArgument extends BukkitArgumentType {

    public final static ParticleArgument PARTICLE = new ParticleArgument();

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.PARTICLE;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentParticle();
    }

}
