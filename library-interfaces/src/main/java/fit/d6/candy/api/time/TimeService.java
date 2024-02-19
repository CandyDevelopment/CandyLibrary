package fit.d6.candy.api.time;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface TimeService extends Service {

    @NotNull
    TimeManager getTimeManager();

    @NotNull
    static TimeService getService() {
        return CandyLibrary.getLibrary().getService(TimeService.class);
    }

}
