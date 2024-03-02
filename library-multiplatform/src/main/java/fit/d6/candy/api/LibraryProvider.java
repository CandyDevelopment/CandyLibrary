package fit.d6.candy.api;

import java.lang.reflect.InvocationTargetException;

class LibraryProvider {

    private static CandyLibrary library;

    static CandyLibrary search() {
        if (library == null) {
            try {
                library = (CandyLibrary) org.bukkit.Bukkit.getPluginManager().getPlugin("CandyLibrary");
            } catch (NoClassDefFoundError e) {
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
