package fit.d6.candy.command;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.command.AnnotationCommandManager;
import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitCommandService extends BukkitService implements CommandService {

    private final BukkitAnnotationCommandManager annotationCommandManager = new BukkitAnnotationCommandManager();
    private final BukkitCommandManager commandManager = new BukkitCommandManager();
    private final BukkitArgumentManager argumentManager = new BukkitArgumentManager();

    @Override
    public @NotNull String getId() {
        return "command";
    }

    @Override
    public @NotNull AnnotationCommandManager getAnnotationCommandManager() {
        return this.annotationCommandManager;
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return this.commandManager;
    }

    @Override
    public @NotNull ArgumentManager getArgumentManager() {
        return this.argumentManager;
    }

    @Override
    public void enable(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this.commandManager, plugin);
    }

}
