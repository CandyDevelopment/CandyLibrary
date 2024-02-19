package fit.d6.candy.api.database;

import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface DatabaseService extends Service {

    @NotNull
    DatabaseManager getDatabaseManager();

}
