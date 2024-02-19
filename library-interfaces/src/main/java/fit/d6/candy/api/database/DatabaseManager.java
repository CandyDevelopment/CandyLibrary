package fit.d6.candy.api.database;

import org.jetbrains.annotations.NotNull;

public interface DatabaseManager {

    @NotNull
    DatabaseInfo createDatabaseInfo(
            @NotNull String host,
            int port,
            @NotNull String username,
            @NotNull String password,
            @NotNull String name,
            @NotNull String tablePrefix
    );

    @NotNull
    Database connect(@NotNull DatabaseType type, @NotNull DatabaseInfo info);

}
