package fit.d6.candy.database;

import fit.d6.candy.api.database.Database;
import fit.d6.candy.api.database.DatabaseInfo;
import fit.d6.candy.api.database.DatabaseManager;
import fit.d6.candy.api.database.DatabaseType;
import fit.d6.candy.database.mysql.MysqlDatabase;
import fit.d6.candy.exception.DatabaseException;
import org.jetbrains.annotations.NotNull;

public class BukkitDatabaseManager implements DatabaseManager {

    @Override
    public @NotNull DatabaseInfo createDatabaseInfo(@NotNull String host, int port, @NotNull String username, @NotNull String password, @NotNull String name, @NotNull String tablePrefix) {
        return new BukkitDatabaseInfo(host, port, username, password, name, tablePrefix);
    }

    @Override
    public @NotNull Database connect(@NotNull DatabaseType type, @NotNull DatabaseInfo info) {
        if (type == DatabaseType.MYSQL) {
            return new MysqlDatabase(
                    info.getHost(),
                    info.getPort(),
                    info.getUsername(),
                    info.getPassword(),
                    info.getHost(),
                    info.getTablePrefix()
            );
        }
        throw new DatabaseException("Unknown database type");
    }

}
