package fit.d6.candy.api.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The column will be nullable</br>
 * Example: name TEXT NULL -> @MysqlObject(type = MySqlType.TEXT) @Null public String name;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Null {
}
