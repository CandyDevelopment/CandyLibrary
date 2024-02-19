package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface CommandManager {

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

}
