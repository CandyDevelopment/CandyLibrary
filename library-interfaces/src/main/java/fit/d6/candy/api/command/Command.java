package fit.d6.candy.api.command;

import org.jetbrains.annotations.NotNull;

public interface Command extends CommandNode {

    @NotNull
    String getName();


}
