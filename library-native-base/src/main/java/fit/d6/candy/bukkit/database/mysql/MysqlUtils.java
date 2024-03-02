package fit.d6.candy.bukkit.database.mysql;

import fit.d6.candy.api.database.DataHolder;
import fit.d6.candy.api.database.DataProperty;
import fit.d6.candy.api.database.annotation.EnumReflection;
import fit.d6.candy.api.database.annotation.Immutable;
import fit.d6.candy.api.database.annotation.mysql.MysqlObject;
import fit.d6.candy.api.database.mysql.MysqlType;
import fit.d6.candy.bukkit.database.BaseDataProperty;
import fit.d6.candy.bukkit.database.BaseImmutableDataProperty;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.*;
import java.util.UUID;

final class MysqlUtils {

    public static <T extends DataHolder> T parseResult(Class<T> clazz, ResultSet resultSet) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T t = constructor.newInstance();
            for (Field field : clazz.getFields()) {
                MysqlObject mySqlObject = field.getAnnotation(MysqlObject.class);
                if (mySqlObject == null)
                    continue;
                Type type = field.getGenericType();
                if (!(type instanceof ParameterizedType parameterizedType))
                    break;
                if (!DataProperty.class.isAssignableFrom((Class<?>) parameterizedType.getRawType()))
                    break;
                Type propertyType = parameterizedType.getActualTypeArguments()[0];
                Object object = parse(field, resultSet, field.getName(), (Class<?>) propertyType);
                if (field.getAnnotation(Immutable.class) != null)
                    field.set(t, new BaseImmutableDataProperty<>(object));
                else {
                    BaseDataProperty<Object> property = new BaseDataProperty<>();
                    property.set(object);
                    field.set(t, property);
                }
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private static Object parse(Field field, ResultSet resultSet, String name, Class<?> type) {
        if (field.getAnnotation(MysqlObject.class).type() == MysqlType.ENUM && field.getAnnotation(EnumReflection.class) != null) {
            try {
                Class<? extends Enum<?>> clazz = field.getAnnotation(EnumReflection.class).value();
                return parseEnum(clazz, resultSet.getString(name));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (type == Integer.class) {
                    return resultSet.getInt(name);
                } else if (type == Long.class) {
                    return resultSet.getLong(name);
                } else if (type == Double.class) {
                    return resultSet.getDouble(name);
                } else if (type == Float.class) {
                    return resultSet.getFloat(name);
                } else if (type == Date.class) {
                    return resultSet.getDate(name);
                } else if (type == Time.class) {
                    return resultSet.getTime(name);
                } else if (type == Timestamp.class) {
                    return resultSet.getTimestamp(name);
                } else if (type == Array.class) {
                    return resultSet.getArray(name);
                } else if (type == BigDecimal.class) {
                    return resultSet.getBigDecimal(name);
                } else if (type == Blob.class) {
                    return resultSet.getBlob(name);
                } else if (type == Clob.class) {
                    return resultSet.getClob(name);
                } else if (type == NClob.class) {
                    return resultSet.getNClob(name);
                } else if (type == Byte.class) {
                    return resultSet.getByte(name);
                } else if (type == Short.class) {
                    return resultSet.getShort(name);
                } else if (type == Boolean.class) {
                    return resultSet.getBoolean(name);
                } else if (type == UUID.class) {
                    return UUID.fromString(resultSet.getString(name));
                } else {
                    return resultSet.getString(name);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Enum<E>> Object parseEnum(Class<?> clazz, String name) {
        return Enum.valueOf((Class<E>) clazz, name);
    }

    private MysqlUtils() {
    }

}
