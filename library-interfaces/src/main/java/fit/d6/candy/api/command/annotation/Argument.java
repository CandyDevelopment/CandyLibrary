package fit.d6.candy.api.command.annotation;

import fit.d6.candy.api.command.ArgumentTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Argument {

    String name();

    ArgumentTypes type();

    String[] suggestions() default {};

}
