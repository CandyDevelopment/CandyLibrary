package fit.d6.candy.configuration;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.configuration.ConfigurationService;
import fit.d6.candy.api.configuration.ConfigurationType;
import fit.d6.candy.api.configuration.annotation.Configuration;
import fit.d6.candy.exception.ConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BukkitConfigurationService extends BukkitService implements ConfigurationService {

    @Override
    public @NotNull String getId() {
        return "configuration";
    }

    @Override
    public @NotNull Object load(@NotNull Class<?> clazz, @NotNull File file, @NotNull ConfigurationType type) {
        if (clazz.getAnnotation(Configuration.class) == null)
            throw new ConfigurationException("The object is not a configuration object");

        if (type == ConfigurationType.YAML) {
            return YamlProvider.load(clazz, file);
        } else if (type == ConfigurationType.TOML) {
            return TomlProvider.load(clazz, file);
        } else if (type == ConfigurationType.JSON) {
            return JsonProvider.load(clazz, file);
        }
        throw new ConfigurationException("Unknown configuration type");
    }

    @Override
    public void save(@NotNull Object configuration, @NotNull File file, @NotNull ConfigurationType type) {
        if (configuration.getClass().getAnnotation(Configuration.class) == null)
            throw new ConfigurationException("The object is not a configuration object");

        if (type == ConfigurationType.YAML) {
            try {
                YamlProvider.save(configuration, file);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else if (type == ConfigurationType.TOML) {
            try {
                TomlProvider.save(configuration, file);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else if (type == ConfigurationType.JSON) {
            try {
                JsonProvider.save(configuration, file);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new ConfigurationException("Unknown configuration type");
        }
    }

}
