package fit.d6.candy.api.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The column will be not null</br>
 * Example: name TEXT NOT NULL -> @MysqlObject(type = MySqlType.TEXT) @NotNull public String name;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
}
