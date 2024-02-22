package fit.d6.candy.event;

import fit.d6.candy.api.event.EventListener;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitEventListener implements EventListener, Listener {

    private final BukkitEventListenerBuilder<?> builder;
    private final Class<?> eventType;
    private final Plugin plugin;
    private final EventPriority priority;
    private final boolean ignoreCancelled;

    public BukkitEventListener(BukkitEventListenerBuilder<?> builder, Class<?> eventType, Plugin plugin, EventPriority priority, boolean ignoreCancelled) {
        this.builder = builder;
        this.eventType = eventType;
        this.plugin = plugin;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
    }

    @NotNull
    @Override
    public Class<?> getEventType() {
        return eventType;
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
        this.builder.registered.remove(this.plugin, this);
    }

}
