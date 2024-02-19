package fit.d6.candy.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fit.d6.candy.api.command.ArgumentTypes;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

public class EntityArgumentType extends BukkitArgumentType {

    public final static EntityArgumentType SINGLE_ENTITY = new EntityArgumentType(NmsAccessor.getAccessor().argumentSingleEntity(), ArgumentTypes.SINGLE_ENTITY);
    public final static EntityArgumentType ENTITIES = new EntityArgumentType(NmsAccessor.getAccessor().argumentEntities(), ArgumentTypes.ENTITIES);
    public final static EntityArgumentType SINGLE_PLAYER = new EntityArgumentType(NmsAccessor.getAccessor().argumentSinglePlayer(), ArgumentTypes.SINGLE_PLAYER);
    public final static EntityArgumentType PLAYERS = new EntityArgumentType(NmsAccessor.getAccessor().argumentPlayers(), ArgumentTypes.PLAYERS);

    private final ArgumentType<?> argument;
    private final ArgumentTypes types;

    public EntityArgumentType(ArgumentType<?> argument, ArgumentTypes types) {
        this.argument = argument;
        this.types = types;
    }

    @Override
    public ArgumentType<?> toBrigadier() {
        return this.argument;
    }

    @Override
    public @NotNull ArgumentTypes getType() {
        return types;
    }
}
