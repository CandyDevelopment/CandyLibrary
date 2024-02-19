package fit.d6.candy.api.configuration;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface ConfigurationService extends Service {

    @NotNull
    Object load(@NotNull Class<?> clazz, @NotNull File file, @NotNull ConfigurationType type);

    void save(@NotNull Object configuration, @NotNull File file, @NotNull ConfigurationType type);

    @NotNull
    static ConfigurationService getService() {
        return CandyLibrary.getLibrary().getService(ConfigurationService.class);
    }

}
