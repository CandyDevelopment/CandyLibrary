package fit.d6.candy.api.command;

import fit.d6.candy.api.annotation.FoliaSupport;
import org.jetbrains.annotations.NotNull;

@FoliaSupport
public interface AnnotationCommandManager {

    @NotNull
    Command register(@NotNull Object command);

    @NotNull
    Command register(@NotNull String prefix, @NotNull Object command);

    @NotNull
    static AnnotationCommandManager getManager() {
        return CommandService.getService().getAnnotationCommandManager();
    }

}
