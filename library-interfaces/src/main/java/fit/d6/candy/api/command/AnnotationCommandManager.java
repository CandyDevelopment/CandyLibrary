package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface AnnotationCommandManager {

    @NotNull
    Command register(@NotNull Object command);

    @NotNull
    Command register(@NotNull String prefix, @NotNull Object command);

}
