package fit.d6.candy.api.database;

import org.jetbrains.annotations.NotNull;

public interface DatabaseInfo {

    @NotNull
    String getHost();

    int getPort();

    String getUsername();

    String getPassword();

    String getName();

    String getTablePrefix();

}
