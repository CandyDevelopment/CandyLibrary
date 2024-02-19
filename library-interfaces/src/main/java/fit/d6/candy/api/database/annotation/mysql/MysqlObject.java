package fit.d6.candy.api.database.annotation.mysql;

import fit.d6.candy.api.database.mysql.MysqlType;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field annotated with this annotation will be a column in a mysql table
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MysqlObject {

    /**
     * @return Data type of the column
     */
    @NotNull
    MysqlType type();

}
