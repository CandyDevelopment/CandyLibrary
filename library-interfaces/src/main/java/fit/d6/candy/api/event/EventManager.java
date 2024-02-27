package fit.d6.candy.api.event;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public interface EventManager {

    @NotNull <E extends Event> EventListenerBuilder<E> subscribe(Class<E> eventType);

}
