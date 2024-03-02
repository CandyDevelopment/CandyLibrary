package fit.d6.candy.bukkit.util;

import java.util.ArrayList;
import java.util.List;

public final class ListUtils {

    public static <T> List<T> reverse(List<T> list) {
        List<T> newList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--)
            newList.add(list.get(i));
        return newList;
    }

    private ListUtils() {
    }

}
