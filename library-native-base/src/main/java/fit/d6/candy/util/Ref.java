package fit.d6.candy.util;

import org.bukkit.Bukkit;

public final class Ref {

    public static String getObcVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    private Ref() {
    }

}
