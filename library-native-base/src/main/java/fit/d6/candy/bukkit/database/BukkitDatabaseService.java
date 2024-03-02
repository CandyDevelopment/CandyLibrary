package fit.d6.candy.bukkit.database;

import fit.d6.candy.api.database.DatabaseService;
import fit.d6.candy.bukkit.BukkitService;
import org.jetbrains.annotations.NotNull;

public class BukkitDatabaseService extends BukkitService implements DatabaseService {

    private final BukkitDatabaseManager databaseManager = new BukkitDatabaseManager();

    @Override
    @NotNull
    public BukkitDatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public @NotNull String getId() {
        return "database";
    }

}
