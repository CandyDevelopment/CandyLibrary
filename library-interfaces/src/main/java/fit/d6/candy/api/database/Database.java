package fit.d6.candy.api.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Database base class
 */
public interface Database {

    /**
     * Create table, table name will be the name of the data class
     *
     * @param dataClass data class
     * @param <T>       data holder
     * @return table creator
     */
    @NotNull <T extends DataHolder> DataTableCreator<T> createTable(@NotNull Class<T> dataClass);

    /**
     * Find the table, return null if not exists
     *
     * @param dataClass data class
     * @param <T>       data holder
     * @return table or null
     */
    @Nullable <T extends DataHolder> DataTable<T> findTable(@NotNull Class<T> dataClass);

    /**
     * Find table whose name equals to the name of the data class
     *
     * @param dataClass data class
     * @param <T>       data holder
     * @return if the table exists
     */
    <T extends DataHolder> boolean hasTable(@NotNull Class<T> dataClass);

    /**
     * Get java sql connection
     *
     * @return java sql connection
     */
    @NotNull
    Connection connection();

    /**
     * Create statement
     *
     * @return statement
     */
    @NotNull
    Statement statement();

    /**
     * Create prepared statement
     *
     * @return prepared statement
     */
    @NotNull
    PreparedStatement preparedStatement(@NotNull String sql);

    void close();

}
