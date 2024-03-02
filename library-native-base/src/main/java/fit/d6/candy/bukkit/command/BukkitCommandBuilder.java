package fit.d6.candy.bukkit.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import fit.d6.candy.api.command.CommandBuilder;

public abstract class BukkitCommandBuilder<T extends ArgumentBuilder<?, T>> implements CommandBuilder {

    public abstract ArgumentBuilder<Object, ?> toBrigadier();

}
