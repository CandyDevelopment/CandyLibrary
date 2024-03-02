package fit.d6.candy.bukkit.database.mysql;

import fit.d6.candy.api.database.*;
import fit.d6.candy.api.database.annotation.Immutable;
import fit.d6.candy.api.database.annotation.PrimaryKey;
import fit.d6.candy.api.database.annotation.mysql.MysqlObject;
import fit.d6.candy.bukkit.database.BaseDataProperty;
import fit.d6.candy.bukkit.database.BaseImmutableDataProperty;
import fit.d6.candy.bukkit.exception.DatabaseException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlDataTable<T extends DataHolder> implements DataTable<T> {

    private final Database database;
    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final String tableName;
    private boolean hasPrimaryKey = false;

    public MysqlDataTable(Database database, Class<T> clazz, String tableName) {
        this.database = database;
        this.clazz = clazz;
        this.tableName = tableName;

        try {
            this.constructor = clazz.getDeclaredConstructor();
            this.constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new DatabaseException("This data holder has no empty constructor");
        }
    }

    @Override
    public @NotNull T create() {
        try {
            T t = constructor.newInstance();
            for (Field field : this.clazz.getFields()) {
                MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
                if (mySqlObject == null)
                    continue;
                if (field.getAnnotation(PrimaryKey.class) != null)
                    this.hasPrimaryKey = true;
                field.set(t, field.getAnnotation(Immutable.class) != null ? new BaseImmutableDataProperty<>(null) : new BaseDataProperty<>());
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public @NotNull T store(@NotNull T t, boolean replace) {
        StringBuilder sqlBuilder = new StringBuilder();
        if (this.hasPrimaryKey && replace) {
            sqlBuilder.append("REPLACE ");
        } else {
            sqlBuilder.append("INSERT ");
        }
        sqlBuilder.append("INTO `").append(this.tableName).append("` (");
        Map<String, String> kv = new HashMap<>();

        for (Field field : this.clazz.getFields()) {
            MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
            if (mySqlObject == null)
                continue;
            if (field.getAnnotation(Immutable.class) != null)
                continue;
            try {
                DataProperty<?> dataProperty = (DataProperty<?>) field.get(t);
                if (dataProperty.get() == null)
                    continue;
                String columnName = field.getName();
                kv.put(columnName, dataProperty.get().toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        StringBuilder keysBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> key : kv.entrySet()) {
            keysBuilder.append("`").append(key.getKey()).append("`,");
            valuesBuilder.append("?,");
            values.add(key.getValue());
        }
        sqlBuilder.append(keysBuilder.substring(0, keysBuilder.length() - 1)).append(") VALUES (").append(valuesBuilder.substring(0, valuesBuilder.length() - 1)).append(");");
        PreparedStatement preparedStatement = this.getDatabase().preparedStatement(sqlBuilder.toString());
        try {
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }
            preparedStatement.executeUpdate();
            // ResultSet resultSet = preparedStatement.executeQuery();
            // T result = null;
            // if (resultSet.next())
            //     result = MysqlUtils.parseResult(this.clazz, resultSet);
            // resultSet.close();
            preparedStatement.close();
            // return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // FIXME
        DataLookup<T> lookup = this.lookup();
        for (Field field : this.clazz.getFields()) {
            MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
            if (mySqlObject == null)
                continue;
            if (field.getAnnotation(Immutable.class) != null)
                continue;
            try {
                DataProperty<?> dataProperty = (DataProperty<?>) field.get(t);
                if (dataProperty.get() == null)
                    continue;
                lookup.condition(field.getName(), dataProperty.get().toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        List<T> list = lookup.lookup();
        if (list.isEmpty())
            throw new DatabaseException("Error occurs when storing");
        return list.get(0);
    }

    @Override
    public @NotNull DataDelete<T> delete() {
        return new MysqlDataDelete<>(this.clazz, this);
    }

    @Override
    public @NotNull DataLookup<T> lookup() {
        return new MysqlDataLookup<>(this.clazz, this);
    }

    @Override
    public @NotNull Database getDatabase() {
        return this.database;
    }

    @Override
    public @NotNull String getName() {
        return this.tableName;
    }

}
