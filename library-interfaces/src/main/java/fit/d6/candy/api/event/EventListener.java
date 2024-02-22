package fit.d6.candy.api.event;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface EventListener {

    @NotNull
    Class<?> getEventType();

    @NotNull
    Plugin getPlugin();

    void unregister();

}
