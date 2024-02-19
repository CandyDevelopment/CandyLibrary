package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class PlayerProfilesArgumentType extends BukkitArgumentType {

    public final static PlayerProfilesArgumentType PLAYER_PROFILES = new PlayerProfilesArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentPlayerProfiles();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.PLAYER_PROFILES;
    }

}
