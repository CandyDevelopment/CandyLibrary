package fit.d6.candy.event;

import org.bukkit.event.Event;

@FunctionalInterface
public interface FilterEventStep<E extends Event> extends EventStep<E> {

    boolean step(E e);

}
