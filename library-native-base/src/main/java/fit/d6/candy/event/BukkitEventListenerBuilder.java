package fit.d6.candy.event;

import fit.d6.candy.api.event.EventListener;
import fit.d6.candy.api.event.EventListenerBuilder;
import fit.d6.candy.exception.EventException;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BukkitEventListenerBuilder<E extends Event> implements EventListenerBuilder<E> {

    private final Class<E> eventType;
    private EventPriority priority = EventPriority.NORMAL;
    private boolean ignoreCancelled = false;
    private final List<EventStep<E>> filters = new ArrayList<>();

    final Map<Plugin, BukkitEventListener> registered = new HashMap<>();

    public BukkitEventListenerBuilder(Class<E> clazz) {
        this.eventType = clazz;
    }

    @Override
    public @NotNull EventListenerBuilder<E> priority(@NotNull EventPriority priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public @NotNull EventListenerBuilder<E> ignoreCancelled(boolean isIgnoreCancelled) {
        this.ignoreCancelled = isIgnoreCancelled;
        return this;
    }

    @Override
    public @NotNull EventListenerBuilder<E> filter(@NotNull Predicate<E> filter) {
        this.filters.add((FilterEventStep<E>) filter::test);
        return this;
    }

    @Override
    public @NotNull EventListenerBuilder<E> handler(@NotNull Consumer<E> handler) {
        this.filters.add((HandlerEventStep<E>) handler::accept);
        return this;
    }

    @Override
    public @NotNull EventListener bind(@NotNull Plugin plugin) {
        if (this.registered.containsKey(plugin))
            throw new EventException("This plugin has already bound this listener");

        Consumer<E> solver = (event) -> {
            for (EventStep<E> predicate : this.filters) {
                if (predicate instanceof FilterEventStep<E> filterEventStep) {
                    if (!filterEventStep.step(event))
                        return;
                } else if (predicate instanceof HandlerEventStep<E> handlerEventStep) {
                    handlerEventStep.step(event);
                }
            }
        };

        BukkitEventListener eventListener = null;
        if (ignoreCancelled) {
            if (this.priority == EventPriority.LOWEST) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.LOWEST,
                            ignoreCancelled = true
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.LOW) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.LOW,
                            ignoreCancelled = true
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.NORMAL) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.NORMAL,
                            ignoreCancelled = true
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.HIGH) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.HIGH,
                            ignoreCancelled = true
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.HIGHEST) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.HIGHEST,
                            ignoreCancelled = true
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.MONITOR) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.MONITOR,
                            ignoreCancelled = true
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            }
        } else {
            if (this.priority == EventPriority.LOWEST) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.LOWEST
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.LOW) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.LOW
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.NORMAL) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.NORMAL
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.HIGH) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.HIGH
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.HIGHEST) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.HIGHEST
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            } else if (this.priority == EventPriority.MONITOR) {
                eventListener = new BukkitEventListener(this, this.eventType, plugin, this.priority, this.ignoreCancelled) {

                    @EventHandler(
                            priority = EventPriority.MONITOR
                    )
                    public void event(E e) {
                        solver.accept(e);
                    }

                };
            }
        }
        if (eventListener == null)
            throw new EventException("Failed to build listener");

        this.registered.put(plugin, eventListener);

        return eventListener;
    }

}
