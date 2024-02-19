package fit.d6.candy.time;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fit.d6.candy.api.time.Duration;
import fit.d6.candy.api.time.TimeManager;
import fit.d6.candy.exception.TimeException;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class BukkitTimeManager implements TimeManager {
    @Override
    public @NotNull Duration of(@NotNull TemporalUnit unit, long time) {
        return new BukkitDuration(unit.getDuration().toMillis() * time);
    }

    @Override
    public @NotNull Duration parse(@NotNull String timeString) {
        StringReader reader = new StringReader(timeString);
        boolean hasYear = false;
        boolean hasMonth = false;
        boolean hasWeek = false;
        boolean hasDay = false;
        boolean hasHour = false;
        boolean hasMinute = false;
        boolean hasSecond = false;
        Duration duration = new BukkitDuration(0);
        while (reader.canRead()) {
            int time;
            try {
                time = reader.readInt();
            } catch (CommandSyntaxException e) {
                throw new TimeException("Failed to parse time");
            }
            String unit = reader.readUnquotedString();
            if (unit.equalsIgnoreCase("yr")) {
                if (hasYear)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                duration = duration.add(Duration.of(ChronoUnit.YEARS, time));
            } else if (unit.equalsIgnoreCase("mo")) {
                if (hasMonth)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                hasMonth = true;
                duration = duration.add(Duration.of(ChronoUnit.DAYS, 30L * time));
            } else if (unit.equalsIgnoreCase("wk")) {
                if (hasWeek)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                duration = duration.add(Duration.of(ChronoUnit.WEEKS, time));
            } else if (unit.equalsIgnoreCase("d")) {
                if (hasDay)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                duration = duration.add(Duration.of(ChronoUnit.DAYS, time));
            } else if (unit.equalsIgnoreCase("h")) {
                if (hasHour)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                duration = duration.add(Duration.of(ChronoUnit.HOURS, time));
            } else if (unit.equalsIgnoreCase("min")) {
                if (hasMinute)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                hasMinute = true;
                duration = duration.add(Duration.of(ChronoUnit.MINUTES, time));
            } else if (unit.equalsIgnoreCase("s")) {
                if (hasSecond)
                    throw new TimeException("Failed to parse time");
                hasYear = true;
                hasMonth = true;
                hasWeek = true;
                hasDay = true;
                hasHour = true;
                hasMinute = true;
                hasSecond = true;
                duration = duration.add(Duration.of(ChronoUnit.SECONDS, time));
            } else {
                throw new TimeException("Failed to parse time");
            }
        }
        return duration;
    }

}
