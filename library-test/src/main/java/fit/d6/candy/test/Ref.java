package fit.d6.candy.test;

import org.bukkit.Bukkit;

public final class Ref {

    public static String getObcVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    private Ref() {
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
