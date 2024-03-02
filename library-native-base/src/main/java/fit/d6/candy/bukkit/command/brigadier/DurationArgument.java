package fit.d6.candy.bukkit.command.brigadier;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fit.d6.candy.api.time.Duration;
import fit.d6.candy.bukkit.time.BukkitDuration;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class DurationArgument implements ArgumentType<Duration> {

    public static boolean hasNumber(String string) {
        return string.contains("0") ||
                string.contains("1") ||
                string.contains("2") ||
                string.contains("3") ||
                string.contains("4") ||
                string.contains("5") ||
                string.contains("6") ||
                string.contains("7") ||
                string.contains("8") ||
                string.contains("9");
    }

    private final static Collection<String> EXAMPLES = List.of("1yr2mo3wk4d5min6s", "2yr6mo", "43yr22s");
    private static final SimpleCommandExceptionType ERROR_UNIT_DUPLICATED = new SimpleCommandExceptionType(new LiteralMessage("Unit duplicated"));

    private static final DynamicCommandExceptionType ERROR_UNKNOWN_TIME_UNIT = new DynamicCommandExceptionType((unit) -> new LiteralMessage("Unknown time unit: " + unit));

    private DurationArgument() {
    }

    public static DurationArgument duration() {
        return new DurationArgument();
    }

    public static Duration getDuration(CommandContext<?> commandContext, String name) throws CommandSyntaxException {
        StringReader reader = new StringReader(StringArgumentType.getString(commandContext, name));
        boolean hasYear = false;
        boolean hasMonth = false;
        boolean hasWeek = false;
        boolean hasDay = false;
        boolean hasHour = false;
        boolean hasMinute = false;
        boolean hasSecond = false;
        Duration duration = new BukkitDuration(0);
        while (reader.canRead()) {
            int time = reader.readInt();
            StringBuilder unit = new StringBuilder();
            while (reader.canRead()) {
                if (hasNumber(String.valueOf(reader.peek())))
                    break;
                unit.append(reader.read());
            }
            if (unit.toString().equalsIgnoreCase("yr") || unit.toString().equalsIgnoreCase("y")) {
                if (hasYear)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                duration = duration.add(Duration.of(ChronoUnit.YEARS, time));
            } else if (unit.toString().equalsIgnoreCase("mo")) {
                if (hasMonth)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                duration = duration.add(Duration.of(ChronoUnit.DAYS, 30L * time));
            } else if (unit.toString().equalsIgnoreCase("wk")) {
                if (hasWeek)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                duration = duration.add(Duration.of(ChronoUnit.WEEKS, time));
            } else if (unit.toString().equalsIgnoreCase("d")) {
                if (hasDay)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                duration = duration.add(Duration.of(ChronoUnit.DAYS, time));
            } else if (unit.toString().equalsIgnoreCase("h")) {
                if (hasHour)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                duration = duration.add(Duration.of(ChronoUnit.HOURS, time));
            } else if (unit.toString().equalsIgnoreCase("min")) {
                if (hasMinute)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                hasMinute = true;
                duration = duration.add(Duration.of(ChronoUnit.MINUTES, time));
            } else if (unit.toString().equalsIgnoreCase("s")) {
                if (hasSecond)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                hasMinute = true;
                hasSecond = true;
                duration = duration.add(Duration.of(ChronoUnit.SECONDS, time));
            } else {
                throw ERROR_UNKNOWN_TIME_UNIT.create(unit.toString());
            }
        }
        return duration;
    }

    @Override
    public Duration parse(StringReader reader) throws CommandSyntaxException {
        boolean hasYear = false;
        boolean hasMonth = false;
        boolean hasWeek = false;
        boolean hasDay = false;
        boolean hasHour = false;
        boolean hasMinute = false;
        boolean hasSecond = false;
        Duration duration = new BukkitDuration(0);
        while (reader.canRead()) {
            int time = reader.readInt();
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.canRead()) {
                if (hasNumber(String.valueOf(reader.peek())))
                    break;
                stringBuilder.append(reader.read());
            }

            String unit = stringBuilder.toString();
            if (unit.equalsIgnoreCase("yr")) {
                if (hasYear)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                duration = duration.add(Duration.of(ChronoUnit.YEARS, time));
            } else if (unit.equalsIgnoreCase("mo")) {
                if (hasMonth)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                duration = duration.add(Duration.of(ChronoUnit.DAYS, 30L * time));
            } else if (unit.equalsIgnoreCase("wk")) {
                if (hasWeek)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                duration = duration.add(Duration.of(ChronoUnit.WEEKS, time));
            } else if (unit.equalsIgnoreCase("d")) {
                if (hasDay)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                duration = duration.add(Duration.of(ChronoUnit.DAYS, time));
            } else if (unit.equalsIgnoreCase("h")) {
                if (hasHour)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                duration = duration.add(Duration.of(ChronoUnit.HOURS, time));
            } else if (unit.equalsIgnoreCase("min")) {
                if (hasMinute)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                hasMinute = true;
                duration = duration.add(Duration.of(ChronoUnit.MINUTES, time));
            } else if (unit.equalsIgnoreCase("s")) {
                if (hasSecond)
                    throw ERROR_UNIT_DUPLICATED.create();
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                hasMinute = true;
                hasSecond = true;
                duration = duration.add(Duration.of(ChronoUnit.SECONDS, time));
            } else {
                throw ERROR_UNKNOWN_TIME_UNIT.create(unit);
            }
        }
        return duration;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringReader = new StringReader(builder.getRemaining());
        if (!stringReader.canRead()) {
            return builder.buildFuture();
        }

        boolean hasYear = false;
        boolean hasMonth = false;
        boolean hasWeek = false;
        boolean hasDay = false;
        boolean hasHour = false;
        boolean hasMinute = false;
        boolean hasSecond = false;
        while (stringReader.canRead()) {
            try {
                stringReader.readInt();
            } catch (CommandSyntaxException e) {
                return builder.buildFuture();
            }
            String unit = stringReader.readUnquotedString();
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
            stringReader.readInt();
            if (!hasSecond) {
                if (!hasMinute) {
                    if (!hasHour) {
                        if (!hasDay) {
                            if (!hasWeek) {
                                if (!hasMonth) {
                                    if (!hasYear) {
                                        return suggest(List.of("y", "mo", "wk", "d", "h", "min", "s"), builder.createOffset(builder.getStart() + stringReader.getCursor()));
                                    } else {
                                        return suggest(List.of("mo", "wk", "d", "h", "min", "s"), builder.createOffset(builder.getStart() + stringReader.getCursor()));
                                    }
                                } else {
                                    return suggest(List.of("wk", "d", "h", "min", "s"), builder.createOffset(builder.getStart() + stringReader.getCursor()));
                                }
                            } else {
                                return suggest(List.of("d", "h", "min", "s"), builder.createOffset(builder.getStart() + stringReader.getCursor()));
                            }
                        } else {
                            return suggest(List.of("h", "min", "s"), builder.createOffset(builder.getStart() + stringReader.getCursor()));
                        }
                    } else {
                        return suggest(List.of("min", "s"), builder.createOffset(builder.getStart() + stringReader.getCursor()));
                    }
                } else {
                    return suggest("s", builder.createOffset(builder.getStart() + stringReader.getCursor()));
                }
            } else {
                return builder.buildFuture();
            }
        } catch (CommandSyntaxException var5) {
            return builder.buildFuture();
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private static CompletableFuture<Suggestions> suggest(String value, SuggestionsBuilder builder) {
        return suggest(List.of(value), builder);
    }


    private static CompletableFuture<Suggestions> suggest(List<String> list, SuggestionsBuilder builder) {
        String string = builder.getRemaining().toLowerCase(Locale.ROOT);

        for (String value : list) {
            if (matchesSubStr(string, value.toLowerCase(Locale.ROOT))) {
                builder.suggest(value);
            }
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
