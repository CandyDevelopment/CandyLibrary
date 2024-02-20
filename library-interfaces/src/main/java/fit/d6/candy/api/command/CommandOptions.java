package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface CommandOptions {

    @NotNull
    CommandOptions aliases(@NotNull String... aliases);

    @NotNull
    CommandOptions description(@NotNull String description);

    @NotNull
    CommandOptions usage(@NotNull String usage);

    @NotNull
    static CommandOptions options() {
        return CommandService.getService().getCommandManager().createOptions();
    }

}
