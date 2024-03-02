package fit.d6.candy.bukkit.database;

import fit.d6.candy.api.database.DatabaseInfo;
import org.jetbrains.annotations.NotNull;

public class BukkitDatabaseInfo implements DatabaseInfo {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String name;
    private final String tablePrefix;

    public BukkitDatabaseInfo(
            String host,
            int port,
            String username,
            String password,
            String name,
            String tablePrefix
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.name = name;
        this.tablePrefix = tablePrefix;
    }

    @NotNull
    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTablePrefix() {
        return tablePrefix;
    }

}
