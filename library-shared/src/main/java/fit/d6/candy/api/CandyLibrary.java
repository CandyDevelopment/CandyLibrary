package fit.d6.candy.api;

import org.jetbrains.annotations.NotNull;

public interface CandyLibrary {

    @NotNull <S extends Service> S getService(@NotNull Class<S> clazz);

    @NotNull
    static CandyLibrary getLibrary() {
        throw new RuntimeException();
    }

}
