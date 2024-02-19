package fit.d6.candy.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fit.d6.candy.api.database.DataHolder;
import fit.d6.candy.api.database.DataTable;
import fit.d6.candy.api.database.DataTableCreator;
import fit.d6.candy.api.database.Database;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MysqlDatabase implements Database {

    private final static String NAME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static String randomName(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(NAME_CHARS.charAt(random.nextInt(NAME_CHARS.length())));
        }
        return stringBuilder.toString();
    }

    private final HikariConfig hikariConfig = new HikariConfig();
    private DataSource dataSource;
    private final String threadName;
    private final String tablePrefix;

    private final Map<String, String> tables = new HashMap<>();
    private final Map<String, DataTable<?>> loadedTables = new HashMap<>();

    private final Connection connection;

    public MysqlDatabase(String host, int port, String username, String password, String database, String tablePrefix) {
        this.hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        this.hikariConfig.setUsername(username);
        this.hikariConfig.setPassword(password);
        this.dataSource = new HikariDataSource(this.hikariConfig);
        this.threadName = "mysql#" + randomName(8);
        this.tablePrefix = tablePrefix;

        try {
            this.connection = this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initDataTable();
    }

    private void initDataTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS `" + this.tablePrefix + "tabledata` (`REFERENCE_CLASS` TEXT, `TABLE_NAME` TEXT);";
        String selectAll = "SELECT * FROM `" + this.tablePrefix + "tabledata`;";
        Statement statement = this.statement();
        try {
            statement.executeUpdate(createTable);

            ResultSet resultSet = statement.executeQuery(selectAll);
            while (resultSet.next())
                this.tables.put(resultSet.getString("REFERENCE_CLASS"), resultSet.getString("TABLE_NAME"));

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull <T extends DataHolder> DataTableCreator<T> createTable(@NotNull Class<T> dataClass) {
        if (this.tables.containsKey(dataClass.getName()))
            throw new RuntimeException("Table exists");
        return new MysqlDataTableCreator<>(this, dataClass, this.tablePrefix);
    }

    @Override
    public @Nullable <T extends DataHolder> DataTable<T> findTable(@NotNull Class<T> dataClass) {
        if (!this.tables.containsKey(dataClass.getName()))
            return null;
        if (this.loadedTables.containsKey(dataClass.getName()))
            return (DataTable<T>) this.loadedTables.get(dataClass.getName());
        DataTable<T> table = new MysqlDataTable<>(this, dataClass, this.tables.get(dataClass.getName()));
        this.loadedTables.put(dataClass.getName(), table);
        return table;
    }

    @Override
    public <T extends DataHolder> boolean hasTable(@NotNull Class<T> dataClass) {
        return this.tables.containsKey(dataClass.getName());
    }

    @Override
    public @NotNull Connection connection() {
        return this.connection;
    }

    @Override
    public @NotNull Statement statement() {
        try {
            return this.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull PreparedStatement preparedStatement(@NotNull String sql) {
        try {
            return this.connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    <T extends DataHolder> void finishCreating(Class<T> dataClass, DataTable<T> table) {
        this.tables.put(dataClass.getName(), table.getName());
        this.loadedTables.put(dataClass.getName(), table);
        PreparedStatement preparedStatement = this.preparedStatement("INSERT INTO `" + this.tablePrefix + "tabledata` (`REFERENCE_CLASS`, `TABLE_NAME`) VALUES (?, ?);");
        try {
            preparedStatement.setString(1, dataClass.getName());
            preparedStatement.setString(2, table.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isIdUsed(String id) {
        return this.tables.containsValue(this.tablePrefix + id);
    }

}
