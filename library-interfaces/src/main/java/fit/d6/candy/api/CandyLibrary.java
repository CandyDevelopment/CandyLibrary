package fit.d6.candy.api;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface CandyLibrary {

    @NotNull <S extends Service> S getService(@NotNull Class<S> clazz);

    @NotNull
    CandyVersion getVersion();

    @NotNull
    static CandyLibrary getLibrary() {
        return (CandyLibrary) Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("CandyLibrary"));
    }

    @NotNull
    static CandyVersion version() {
        return getLibrary().getVersion();
    }

}
