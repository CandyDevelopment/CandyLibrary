package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class GameModeArgumentType extends BukkitArgumentType {

    public final static GameModeArgumentType GAME_MODE = new GameModeArgumentType();

    @Override
    public ArgumentType<?> toBrigadier() {
        return NmsAccessor.getAccessor().argumentGameMode();
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return ArgumentTypes.GAME_MODE;
    }
}
