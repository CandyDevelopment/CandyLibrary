package fit.d6.candy.event;

import org.bukkit.event.Event;

@FunctionalInterface
public interface HandlerEventStep<E extends Event> extends EventStep<E> {

    void step(E e);

}
