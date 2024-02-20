package fit.d6.candy.command;

import fit.d6.candy.api.command.CommandOptions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BukkitCommandOptions implements CommandOptions {

    private final List<String> aliases = new ArrayList<>();

    private String description = "A CandyLibrary provided command";

    private String usage = null;

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public @NotNull CommandOptions aliases(@NotNull String... aliases) {
        Arrays.stream(aliases).forEach(this::singleAlias);
        return this;
    }

    private void singleAlias(String alias) {
        if (alias.isEmpty())
            return;
        alias = alias.toLowerCase();
        if (this.aliases.contains(alias))
            return;
        this.aliases.add(alias);
    }


    @Override
    public @NotNull CommandOptions description(@NotNull String description) {
        this.description = description;
        return this;
    }

    @Override
    public @NotNull CommandOptions usage(@NotNull String usage) {
        this.usage = usage;
        return this;
    }

}
