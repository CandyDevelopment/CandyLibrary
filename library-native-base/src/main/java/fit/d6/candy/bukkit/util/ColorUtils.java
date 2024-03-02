package fit.d6.candy.bukkit.util;

import com.catppuccin.Color;
import net.kyori.adventure.text.format.TextColor;

public final class ColorUtils {

    public static TextColor textColor(Color color) {
        return TextColor.color(color.getRed(), color.getGreen(), color.getBlue());
    }

    private ColorUtils() {
    }

}
