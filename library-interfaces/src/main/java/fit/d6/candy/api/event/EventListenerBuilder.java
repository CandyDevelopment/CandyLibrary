package fit.d6.candy.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface EventListenerBuilder<E extends Event> {

    @NotNull
    EventListenerBuilder<E> priority(@NotNull EventPriority priority);

    @NotNull
    EventListenerBuilder<E> ignoreCancelled(boolean isIgnoreCancelled);

    @NotNull
    EventListenerBuilder<E> filter(@NotNull Predicate<@NotNull E> filter);

    @NotNull
    EventListenerBuilder<E> handler(@NotNull Consumer<@NotNull E> handler);

    @NotNull
    EventListener bind(@NotNull Plugin plugin);

}
