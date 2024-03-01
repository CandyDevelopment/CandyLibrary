package fit.d6.candy.api;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public interface CandyLibrary {

    @NotNull <S extends Service> S getService(@NotNull Class<S> clazz);

    @NotNull
    static CandyLibrary getLibrary() {
        try {
            return (CandyLibrary) Class.forName("fit.d6.candy.CandyLibraryPlugin").getDeclaredMethod("getInstance").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
