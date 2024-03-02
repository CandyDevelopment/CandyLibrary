package fit.d6.candy.bukkit.time;

import fit.d6.candy.api.time.TimeService;
import fit.d6.candy.bukkit.BukkitService;
import org.jetbrains.annotations.NotNull;

public class BukkitTimeService extends BukkitService implements TimeService {

    private final BukkitTimeManager timeManager = new BukkitTimeManager();

    @Override
    public @NotNull String getId() {
        return "time";
    }

    @Override
    @NotNull
    public BukkitTimeManager getTimeManager() {
        return timeManager;
    }

}
