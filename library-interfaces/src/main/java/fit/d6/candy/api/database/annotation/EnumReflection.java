package fit.d6.candy.api.database.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example: type ENUM(A,B,C) -> @MysqlObject(type = MySqlType.ENUM) @ReflectJavaEnum(Type.class) public Type type;</br>
 * public enum Type {</br>
 * A, B, C;</br>
 * }</br>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumReflection {

    /**
     * @return The enum class to be reflected
     */
    @NotNull
    Class<? extends Enum<?>> value();

}
