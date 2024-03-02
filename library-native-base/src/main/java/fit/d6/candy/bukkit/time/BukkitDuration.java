package fit.d6.candy.bukkit.time;

import fit.d6.candy.api.time.Duration;
import fit.d6.candy.bukkit.exception.TimeException;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BukkitDuration implements Duration {

    private final static long SECOND = 1000L;
    private final static long MINUTE = 60 * 1000L;
    private final static long HOUR = 60 * 60 * 1000L;
    private final static long DAY = 24 * 60 * 60 * 1000L;
    private final static long WEEK = 7 * 24 * 60 * 60 * 1000L;
    private final static long MONTH = 30 * 24 * 60 * 60 * 1000L;
    private final static long YEAR = 365 * 24 * 60 * 60 * 1000L;

    private final long time;

    public BukkitDuration(long time) {
        this.time = time;
    }

    @Override
    public int getYearsUnit() {
        return (int) (this.time / YEAR);
    }

    @Override
    public int getMonthsUnit() {
        return (int) ((this.time - this.getYearsTime()) / MONTH);
    }

    @Override
    public int getWeeksUnit() {
        return (int) ((this.time - this.getYearsTime() - this.getMonthsTime()) / MONTH);
    }

    @Override
    public int getDaysUnit() {
        return (int) ((this.time - this.getYearsTime() - this.getMonthsTime() - this.getWeeksTime()) / MONTH);
    }

    @Override
    public int getHoursUnit() {
        return (int) ((this.time - this.getYearsTime() - this.getMonthsTime() - this.getWeeksTime() - this.getDaysTime()) / MONTH);
    }

    @Override
    public int getMinutesUnit() {
        return (int) ((this.time - this.getYearsTime() - this.getMonthsTime() - this.getWeeksTime() - this.getDaysTime() - this.getHoursTime()) / MONTH);
    }

    @Override
    public int getSecondsUnit() {
        return (int) ((this.time - this.getYearsTime() - this.getMonthsTime() - this.getWeeksTime() - this.getDaysTime() - this.getHoursTime() - this.getMinutesTime()) / MONTH);
    }

    @Override
    public long getYearsTime() {
        return this.getYearsUnit() * YEAR;
    }

    @Override
    public long getMonthsTime() {
        return this.getMonthsUnit() * MONTH;
    }

    @Override
    public long getWeeksTime() {
        return this.getWeeksUnit() * WEEK;
    }

    @Override
    public long getDaysTime() {
        return this.getDaysUnit() * DAY;
    }

    @Override
    public long getHoursTime() {
        return this.getHoursUnit() * HOUR;
    }

    @Override
    public long getMinutesTime() {
        return this.getMinutesUnit() * MINUTE;
    }

    @Override
    public long getSecondsTime() {
        return this.getSecondsUnit() * SECOND;
    }

    @Override
    public long getTime() {
        return this.time;
    }

    @Override
    public @NotNull Date after(@NotNull Date date) {
        return new Date(date.getTime() + this.time);
    }

    @Override
    public @NotNull Date before(@NotNull Date date) {
        return new Date(date.getTime() - this.time);
    }

    @Override
    public @NotNull Duration add(@NotNull Duration another) {
        return new BukkitDuration(this.time + another.getTime());
    }

    @Override
    public @NotNull Duration subtract(@NotNull Duration another) {
        if (this.smaller(another))
            throw new TimeException("The result is smaller than zero");
        return new BukkitDuration(time);
    }

    @Override
    public boolean greater(@NotNull Duration duration) {
        return this.time > duration.getTime();
    }

    @Override
    public boolean smaller(@NotNull Duration duration) {
        return this.time < duration.getTime();
    }

    @Override
    public boolean equals(@NotNull Duration duration) {
        if (this == duration)
            return true;
        if (duration == null)
            return false;
        return this.time == duration.getTime();
    }

}
