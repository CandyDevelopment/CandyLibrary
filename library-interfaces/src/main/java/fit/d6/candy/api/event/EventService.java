package fit.d6.candy.api.event;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface EventService extends Service {

    @NotNull
    EventManager getEventManager();

    @NotNull
    static EventService getService() {
        return CandyLibrary.getLibrary().getService(EventService.class);
    }

}
