package fit.d6.candy.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;

public abstract class BukkitArgumentType implements fit.d6.candy.api.command.ArgumentType {

    public abstract ArgumentType<?> toBrigadier();

}
