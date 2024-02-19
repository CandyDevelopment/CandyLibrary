package fit.d6.candy.api.database.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set the default value of the column</br>
 * Example: name TEXT DEFAULT 'dummy' -> @MysqlObject(type = MySqlType.TEXT) @Default("'dummy'") public String name;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {

    /**
     * String should be quoted with "" or '' manually
     *
     * @return The default value of this column
     */
    @NotNull
    String value();

}
