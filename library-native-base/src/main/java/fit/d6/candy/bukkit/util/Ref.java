package fit.d6.candy.bukkit.util;

import org.bukkit.Bukkit;

public final class Ref {

    public static String getObcVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private Ref() {
    }

}
