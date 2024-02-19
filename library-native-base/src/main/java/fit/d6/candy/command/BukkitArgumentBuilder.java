package fit.d6.candy.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fit.d6.candy.api.command.*;
import fit.d6.candy.command.argument.BukkitArgumentType;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fit.d6.candy.command.brigadier.DurationArgument.hasNumber;

@SuppressWarnings("unchecked")
public class BukkitArgumentBuilder extends BukkitCommandBuilder<RequiredArgumentBuilder<Object, Object>> {

    private final RequiredArgumentBuilder<Object, Object> brigadier;

    private final String name;
    private final List<CommandNode> children = new ArrayList<>();
    private CommandExecutor executor;
    private CommandExecutor playerExecutor;
    private CommandSuggester suggester;
    private Command redirection;
    private Requirement requirement;

    public BukkitArgumentBuilder(String name, BukkitArgumentType argumentType) {
        this.brigadier = (RequiredArgumentBuilder<Object, Object>) NmsAccessor.getAccessor().createArgumentCommand(name, argumentType.toBrigadier());

        this.name = name;

        if (argumentType.getType() == ArgumentTypes.SUMMONABLE_ENTITY_TYPE)
            this.brigadier.suggests((SuggestionProvider<Object>) NmsAccessor.getAccessor().getSummonableEntitiesProvider());
        else if (argumentType.getType() == ArgumentTypes.DURATION) {
            this.brigadier.suggests((commandContext, builder) -> {
                StringReader reader = new StringReader(builder.getRemaining());
                if (!reader.canRead()) {
                    return builder.buildFuture();
                }

                boolean hasYear = false;
                boolean hasMonth = false;
                boolean hasWeek = false;
                boolean hasDay = false;
                boolean hasHour = false;
                boolean hasMinute = false;
                boolean hasSecond = false;
                while (reader.canRead()) {
                    try {
                        int cursor = reader.getCursor();
                        reader.readInt();
                        if (!reader.canRead()) {
                            reader.setCursor(cursor);
                            break;
                        }
                    } catch (CommandSyntaxException e) {
                        return builder.buildFuture();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    while (reader.canRead()) {
                        if (hasNumber(String.valueOf(reader.peek())))
                            break;
                        stringBuilder.append(reader.read());
                    }

                    String unit = stringBuilder.toString();

                    if (unit.equalsIgnoreCase("yr")) {
                        if (hasYear)
                            continue;
                        hasYear = true;
                    } else if (unit.equalsIgnoreCase("mo")) {
                        if (hasMonth)
                            continue;
                        hasYear = true;
                        hasMonth = true;
                    } else if (unit.equalsIgnoreCase("wk")) {
                        if (hasWeek)
                            continue;
                        hasYear = true;
                        hasMonth = true;
                        hasWeek = true;
                    } else if (unit.equalsIgnoreCase("d")) {
                        if (hasDay)
                            continue;
                        hasYear = true;
                        hasMonth = true;
                        hasWeek = true;
                        hasDay = true;
                    } else if (unit.equalsIgnoreCase("h")) {
                        if (hasHour)
                            continue;
                        hasYear = true;
                        hasMonth = true;
                        hasWeek = true;
                        hasDay = true;
                        hasHour = true;
                    } else if (unit.equalsIgnoreCase("min")) {
                        if (hasMinute)
                            continue;
                        hasYear = true;
                        hasMonth = true;
                        hasWeek = true;
                        hasDay = true;
                        hasHour = true;
                        hasMinute = true;
                    } else if (unit.equalsIgnoreCase("s")) {
                        if (hasSecond)
                            continue;
                        hasYear = true;
                        hasMonth = true;
                        hasWeek = true;
                        hasDay = true;
                        hasHour = true;
                        hasMinute = true;
                        hasSecond = true;
                    }
                }
                try {
                    reader.readInt();
                    if (!hasSecond) {
                        if (!hasMinute) {
                            if (!hasHour) {
                                if (!hasDay) {
                                    if (!hasWeek) {
                                        if (!hasMonth) {
                                            if (!hasYear) {
                                                return suggest(List.of("y", "mo", "wk", "d", "h", "min", "s"), builder.createOffset(builder.getStart() + reader.getCursor()));
                                            } else {
                                                return suggest(List.of("mo", "wk", "d", "h", "min", "s"), builder.createOffset(builder.getStart() + reader.getCursor()));
                                            }
                                        } else {
                                            return suggest(List.of("wk", "d", "h", "min", "s"), builder.createOffset(builder.getStart() + reader.getCursor()));
                                        }
                                    } else {
                                        return suggest(List.of("d", "h", "min", "s"), builder.createOffset(builder.getStart() + reader.getCursor()));
                                    }
                                } else {
                                    return suggest(List.of("h", "min", "s"), builder.createOffset(builder.getStart() + reader.getCursor()));
                                }
                            } else {
                                return suggest(List.of("min", "s"), builder.createOffset(builder.getStart() + reader.getCursor()));
                            }
                        } else {
                            return suggest("s", builder.createOffset(builder.getStart() + reader.getCursor()));
                        }
                    } else {
                        return builder.buildFuture();
                    }
                } catch (CommandSyntaxException var5) {
                    return builder.buildFuture();
                }
            });
        }

        NmsAccessor.getAccessor().trySuggests(this.brigadier, argumentType, argumentType.toBrigadier());
    }

    @Override
    public @NotNull CommandBuilder then(CommandNode nextNode) {
        if (nextNode instanceof BukkitCommand) {
            this.brigadier.then(((BukkitCommand) nextNode).toBrigadier());
        } else if (nextNode instanceof BukkitCommandBuilder<?>) {
            this.brigadier.then(((BukkitCommandBuilder<?>) nextNode).toBrigadier());
        } else {
            return this;
        }
        this.children.add(nextNode);
        return this;
    }

    @Override
    public @NotNull CommandBuilder requires(Requirement requirement) {
        this.requirement = requirement;
        if (requirement == null) {
            this.brigadier.requires(it -> true);
        } else {
            this.brigadier.requires(source -> this.requirement.check(NmsAccessor.getAccessor().commandSourceStackGetBukkitSender(source)));
        }
        return this;
    }

    @Override
    public @NotNull CommandBuilder executes(CommandExecutor executor) {
        this.executor = executor;
        this.checkExecutor();
        return this;
    }

    @Override
    public @NotNull CommandBuilder executesPlayer(CommandExecutor executor) {
        this.playerExecutor = executor;
        this.checkExecutor();
        return this;
    }

    private void checkExecutor() {
        if (this.executor == null && this.playerExecutor == null) {
            this.brigadier.executes(null);
        } else {
            this.brigadier.executes(context -> {
                CommandArgumentHelper argumentHelper = new BukkitCommandArgumentHelper(context);
                CommandContext candyContext = new BukkitCommandContext(context, argumentHelper);
                if (this.playerExecutor != null && NmsAccessor.getAccessor().commandSourceStackIsPlayer(context.getSource())) {
                    return this.playerExecutor.executes(candyContext, argumentHelper);
                } else if (this.executor != null) {
                    return this.executor.executes(candyContext, argumentHelper);
                } else {
                    throw NmsAccessor.getAccessor().commandSourceStackNotPlayerException().create();
                }
            });
        }
    }

    @Override
    public @NotNull CommandBuilder suggests(CommandSuggester suggester) {
        this.suggester = suggester;
        if (suggester == null) {
            this.brigadier.suggests(null);
        } else {
            this.brigadier.suggests((commandContext, suggestionsBuilder) -> {
                CommandArgumentHelper argument = new BukkitCommandArgumentHelper(commandContext);
                CommandContext context = new BukkitCommandContext(commandContext, argument);
                CommandSuggestion suggestion = new BukkitCommandSuggestion(suggestionsBuilder);

                this.suggester.suggests(context, argument, suggestion);

                return suggestionsBuilder.buildFuture();
            });
        }
        return this;
    }

    @Override
    public @NotNull CommandBuilder redirects(Command redirection) {
        this.redirection = redirection;
        if (redirection != null) {
            this.brigadier.redirect(((BukkitCommand) redirection).toBrigadier());
        } else {
            this.brigadier.redirect(null);
        }
        return this;
    }

    @Override
    public ArgumentBuilder<Object, ?> toBrigadier() {
        return this.brigadier;
    }

    private static CompletableFuture<Suggestions> suggest(String value, SuggestionsBuilder builder) {
        return suggest(List.of(value), builder);
    }

    private static CompletableFuture<Suggestions> suggest(List<String> list, SuggestionsBuilder builder) {
        String string = builder.getRemaining().toLowerCase(Locale.ROOT);

        for (String value : list) {
            /*if (matchesSubStr(string, value.toLowerCase(Locale.ROOT))) {
                builder.suggest(value);
            }*/
            builder.suggest(value);
        }

        return builder.buildFuture();
    }

    private static boolean matchesSubStr(String remaining, String candidate) {
        for (int i = 0; !candidate.startsWith(remaining, i); ++i) {
            i = candidate.indexOf(95, i);
            if (i < 0) {
                return false;
            }
        }

        return true;
    }

}
