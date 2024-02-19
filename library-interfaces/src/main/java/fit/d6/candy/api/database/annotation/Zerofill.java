package fit.d6.candy.api.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The column will be zerofill</br>
 * Example: id INT ZEROFILL -> @MysqlObject(type = MySqlType.INT) @Zerofill public int id;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Zerofill {
}
