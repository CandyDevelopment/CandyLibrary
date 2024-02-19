package fit.d6.candy.api.time;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalUnit;
import java.util.Date;

public interface Duration {

    int getYearsUnit();

    int getMonthsUnit();

    int getWeeksUnit();

    int getDaysUnit();

    int getHoursUnit();

    int getMinutesUnit();

    int getSecondsUnit();

    long getYearsTime();

    long getMonthsTime();

    long getWeeksTime();

    long getDaysTime();

    long getHoursTime();

    long getMinutesTime();

    long getSecondsTime();

    long getTime();

    @NotNull
    Date after(@NotNull Date date);

    @NotNull
    Date before(@NotNull Date date);

    @NotNull
    Duration add(@NotNull Duration another);

    @NotNull
    Duration subtract(@NotNull Duration another);

    boolean greater(@NotNull Duration duration);

    boolean smaller(@NotNull Duration duration);

    boolean equals(@NotNull Duration duration);

    @NotNull
    static Duration of(@NotNull TemporalUnit unit, long time) {
        return TimeService.getService().getTimeManager().of(unit, time);
    }

}
