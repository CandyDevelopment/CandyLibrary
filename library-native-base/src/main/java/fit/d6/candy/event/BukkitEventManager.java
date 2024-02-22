package fit.d6.candy.event;

import fit.d6.candy.api.event.EventListenerBuilder;
import fit.d6.candy.api.event.EventManager;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class BukkitEventManager implements EventManager {

    @Override
    public @NotNull <E extends Event> EventListenerBuilder<E> subscribe(Class<E> eventType) {
        return new BukkitEventListenerBuilder<>(eventType);
    }

}
