package fit.d6.candy.api.command;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.annotation.FoliaSupport;
import org.jetbrains.annotations.NotNull;

@FoliaSupport
public interface CommandService extends Service {

    @NotNull
    AnnotationCommandManager getAnnotationCommandManager();

    @NotNull
    CommandManager getCommandManager();

    @NotNull
    ArgumentManager getArgumentManager();

    @NotNull
    static CommandService getService() {
        return CandyLibrary.getLibrary().getService(CommandService.class);
    }

}
