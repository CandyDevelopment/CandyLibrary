package fit.d6.candy.database.mysql;

import fit.d6.candy.api.database.DataHolder;
import fit.d6.candy.api.database.DataTable;
import fit.d6.candy.api.database.DataTableCreator;
import fit.d6.candy.api.database.Database;
import fit.d6.candy.api.database.annotation.*;
import fit.d6.candy.api.database.annotation.mysql.MysqlObject;
import fit.d6.candy.api.database.mysql.MysqlType;
import fit.d6.candy.exception.DatabaseException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MysqlDataTableCreator<T extends DataHolder> implements DataTableCreator<T> {

    private final static String NAME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static String randomId(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(NAME_CHARS.charAt(random.nextInt(NAME_CHARS.length())));
        }
        return stringBuilder.toString();
    }

    private String randomName(int length) {
        String name = randomId(length);
        MysqlDatabase mysql = (MysqlDatabase) this.database;
        while (mysql.isIdUsed(name))
            name = randomId(length);
        return name;
    }

    private final Database database;
    private final Class<T> clazz;
    private final String tablePrefix;
    private final List<String> parameters = new ArrayList<>();

    public MysqlDataTableCreator(Database database, Class<T> clazz, String tablePrefix) {
        this.database = database;
        this.clazz = clazz;
        this.tablePrefix = tablePrefix;
    }

    @Override
    public DataTableCreator<T> withParameter(String key, String value) {
        this.parameters.add(key + "=" + value);
        return this;
    }

    @Override
    public DataTableCreator<T> withParameter(String value) {
        this.parameters.add(value);
        return this;
    }

    @Override
    public DataTable<T> create() {
        StringBuilder sqlBuilder = new StringBuilder();
        String tableName = this.tablePrefix + randomName(12);
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS `").append(tableName).append("` (");
        int primaryKeys = countPrimaryKeys();
        if (primaryKeys > 1)
            throw new DatabaseException("Multiple primary keys");
        List<String> primaryKeyColumns = new ArrayList<>();
        for (Field field : this.clazz.getFields()) {
            MysqlObject mysqlObject = field.getAnnotation(MysqlObject.class);
            if (mysqlObject == null)
                continue;
            String columnName = field.getName();
            sqlBuilder.append(columnName).append("` ");
            EnumReflection reflectJavaEnum = field.getAnnotation(EnumReflection.class);
            if (mysqlObject.type() == MysqlType.ENUM && reflectJavaEnum != null) {
                StringBuilder enumBuilder = new StringBuilder();
                try {
                    Object[] objects = (Object[]) reflectJavaEnum.value().getMethod("values").invoke(null);
                    Method nameMethod = reflectJavaEnum.value().getMethod("name");
                    for (Object object : objects)
                        enumBuilder.append("'").append(nameMethod.invoke(object)).append("',");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new DatabaseException(e);
                }
                sqlBuilder.append("ENUM(").append(enumBuilder.substring(0, enumBuilder.length() - 1)).append(")");
            } else {
                sqlBuilder.append(mysqlObject.type().name());
                Argument argument = field.getAnnotation(Argument.class);
                if (argument != null) {
                    if (argument.value().length > 0) {
                        sqlBuilder.append("(");
                        StringBuilder argumentBuilder = new StringBuilder();
                        for (String arg : argument.value()) {
                            argumentBuilder.append(arg).append(",");
                        }
                        sqlBuilder.append(argumentBuilder.substring(0, argumentBuilder.length() - 1)).append(")");
                    }
                }
            }
            if (field.getAnnotation(Unsigned.class) != null)
                sqlBuilder.append(" UNSIGNED");
            if (field.getAnnotation(NotNull.class) != null)
                sqlBuilder.append(" NOT NULL");
            else if (field.getAnnotation(Null.class) != null)
                sqlBuilder.append(" NULL");
            if (field.getAnnotation(Zerofill.class) != null)
                sqlBuilder.append(" ZEROFILL");
            if (field.getAnnotation(AutoIncrement.class) != null)
                sqlBuilder.append(" AUTO_INCREMENT");
            if (primaryKeys > 1)
                primaryKeyColumns.add(columnName);
            else if (field.getAnnotation(PrimaryKey.class) != null)
                sqlBuilder.append(" PRIMARY KEY");
            Default defaultValue = field.getAnnotation(Default.class);
            if (defaultValue != null)
                sqlBuilder.append(" DEFAULT ").append(defaultValue.value());
            Comment comment = field.getAnnotation(Comment.class);
            if (comment != null)
                sqlBuilder.append(" COMMENT '").append(String.join("", comment.value())).append("'");
            sqlBuilder.append(", ");
        }
        if (primaryKeyColumns.size() > 1) {
            StringBuilder primaryKeyBuilder = new StringBuilder();
            for (String primaryKey : primaryKeyColumns) {
                primaryKeyBuilder.append("`").append(primaryKey).append("`,");
            }
            sqlBuilder.append("PRIMARY KEY(").append(primaryKeyBuilder.substring(0, primaryKeyBuilder.length() - 1)).append(")");
        } else
            sqlBuilder = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.length() - 2));
        sqlBuilder.append(")");
        for (String parameter : this.parameters) {
            sqlBuilder.append(" ").append(parameter);
        }
        sqlBuilder.append(";");
        try {
            Statement statement = this.database.statement();
            statement.executeUpdate(sqlBuilder.toString());
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DataTable<T> dataTable = new MysqlDataTable<>(this.database, this.clazz, tableName);
        ((MysqlDatabase) this.database).finishCreating(this.clazz, dataTable);
        return dataTable;
    }

    @Override
    public Database database() {
        return this.database;
    }

    private int countPrimaryKeys() {
        int primaryKeys = 0;
        for (Field field : this.clazz.getFields()) {
            MysqlObject mysqlObject = field.getAnnotation(MysqlObject.class);
            if (mysqlObject == null)
                continue;
            if (field.getAnnotation(PrimaryKey.class) != null)
                primaryKeys += 1;
        }
        return primaryKeys;
    }

}
