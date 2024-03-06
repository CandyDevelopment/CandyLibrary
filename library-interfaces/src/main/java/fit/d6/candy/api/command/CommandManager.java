package fit.d6.candy.api.command;

import fit.d6.candy.api.annotation.FoliaSupport;
import org.jetbrains.annotations.NotNull;

@FoliaSupport
public interface CommandManager {

    void removeVanillaCommands();

    void removePrefixedCommands();

    @NotNull
    CommandOptions createOptions();

    @NotNull
    CommandBuilder createCommand(@NotNull String name);

    @NotNull
    CommandBuilder createCommand(@NotNull String name, @NotNull ArgumentType argumentType);

    /**
     * Only literal command will be registered, argument command will only be built
     *
     * @param builder command builder
     * @return built command
     */
    @NotNull
    Command register(@NotNull CommandBuilder builder);

    /**
     * Only literal command will be registered, argument command will only be built
     *
     * @param prefix  command prefix
     * @param builder command builder
     * @return built command
     */
    @NotNull
    Command register(@NotNull String prefix, @NotNull CommandBuilder builder);

    /**
     * Only literal command will be registered, argument command will only be built
     *
     * @param prefix  command prefix
     * @param builder command builder
     * @param options command options
     * @return built command
     */
    @NotNull
    Command register(@NotNull String prefix, @NotNull CommandBuilder builder, @NotNull CommandOptions options);

    @NotNull
    static CommandManager getManager() {
        return CommandService.getService().getCommandManager();
    }

}
