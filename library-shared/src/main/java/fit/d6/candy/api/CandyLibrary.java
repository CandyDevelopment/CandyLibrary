package fit.d6.candy.api;

import org.jetbrains.annotations.NotNull;

public interface CandyLibrary {

    @NotNull <S extends Service> S getService(@NotNull Class<S> clazz);

    <S extends Service> boolean isServiceConfigured(@NotNull Class<S> clazz);

    @NotNull
    CandyVersion getVersion();

    @NotNull
    static CandyLibrary getLibrary() {
        throw new RuntimeException();
    }

    @NotNull
    static CandyVersion version() {
        throw new RuntimeException();
    }

}
