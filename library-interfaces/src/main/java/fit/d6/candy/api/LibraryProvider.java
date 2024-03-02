package fit.d6.candy.api;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

class LibraryProvider {

    private static CandyLibrary library;

    static CandyLibrary search() {
        if (library == null) {
            try {
                library = (CandyLibrary) Bukkit.getPluginManager().getPlugin("CandyLibrary");
            } catch (Exception e) {
                try {
                    library = (CandyLibrary) Class.forName("fit.d6.candy.velocity.CandyLibraryPlugin").getDeclaredMethod("getInstance").invoke(null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                         ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return library;
    }

}
