package fit.d6.candy.api.event;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public interface Events {

    @NotNull
    static <E extends Event> EventListenerBuilder<E> subscribe(Class<E> eventType) {
        return EventService.getService().getEventManager().subscribe(eventType);
    }

}
