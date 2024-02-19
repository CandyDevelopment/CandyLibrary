package fit.d6.candy.api.time;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalUnit;

public interface TimeManager {

    @NotNull
    Duration of(@NotNull TemporalUnit unit, long time);

    @NotNull
    Duration parse(@NotNull String timeString);

}
