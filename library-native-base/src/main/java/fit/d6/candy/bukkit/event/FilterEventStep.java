package fit.d6.candy.bukkit.event;

import org.bukkit.event.Event;

@FunctionalInterface
public interface FilterEventStep<E extends Event> extends EventStep<E> {

    boolean step(E e);

}
