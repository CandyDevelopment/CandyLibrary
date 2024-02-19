package fit.d6.candy.database.mysql;

import fit.d6.candy.api.database.DataDelete;
import fit.d6.candy.api.database.DataHolder;
import fit.d6.candy.api.database.DataTable;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class MysqlDataDelete<T extends DataHolder> implements DataDelete<T> {

    private final Class<T> clazz;
    private final DataTable<T> table;
    private final List<String> conditions = new ArrayList<>();
    private final Map<String, Map.Entry<String, String>> conditionsKv = new HashMap<>();

    public MysqlDataDelete(Class<T> clazz, DataTable<T> table) {
        this.clazz = clazz;
        this.table = table;
    }

    @Override
    public DataDelete<T> condition(@NotNull String value) {
        this.conditions.add(value);
        return this;
    }

    @Override
    public DataDelete<T> condition(@NotNull String key, @NotNull String operator, @NotNull String value) {
        this.conditionsKv.put(key, new AbstractMap.SimpleEntry<>(operator, value));
        return this;
    }

    @Override
    public void delete() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM `").append(table.getName()).append("`");
        if (conditions.size() > 0) {
            sqlBuilder.append(" WHERE (").append(conditions.get(0)).append(")");
            for (int i = 1; i < conditions.size(); i++) {
                sqlBuilder.append(" AND (").append(conditions.get(i)).append(")");
            }
            if (this.conditionsKv.size() > 0)
                sqlBuilder.append(" AND (");
        } else if (this.conditionsKv.size() > 0)
            sqlBuilder.append(" WHERE (");
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, Map.Entry<String, String>> entry : this.conditionsKv.entrySet()) {
            sqlBuilder.append(entry.getKey()).append(entry.getValue().getKey()).append("?").append(") AND (");
            values.add(entry.getValue().getValue());
        }
        if (this.conditionsKv.size() > 0)
            sqlBuilder = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.length() - 6));
        sqlBuilder.append(";");
        try {
            PreparedStatement statement = this.table.getDatabase().preparedStatement(sqlBuilder.toString());
            int index = 1;
            for (String value : values) {
                statement.setString(index, value);
                index++;
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull DataTable<T> table() {
        return this.table;
    }

}
