package fit.d6.candy.event;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.event.EventManager;
import fit.d6.candy.api.event.EventService;
import org.jetbrains.annotations.NotNull;

public class BukkitEventService extends BukkitService implements EventService {

    private final BukkitEventManager eventManager = new BukkitEventManager();

    @Override
    public @NotNull String getId() {
        return "event";
    }

    @Override
    public @NotNull EventManager getEventManager() {
        return this.eventManager;
    }

}
