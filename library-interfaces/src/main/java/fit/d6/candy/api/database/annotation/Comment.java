package fit.d6.candy.api.database.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add comment</br>
 * Example(mysql): name TEXT COMMENT -> 'the name of the user' @MysqlObject(type = MySqlType.TEXT) @Comment("the name of the user") public String name;
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {

    /**
     * @return The content of the comment
     */
    @NotNull
    String @NotNull [] value();

}
