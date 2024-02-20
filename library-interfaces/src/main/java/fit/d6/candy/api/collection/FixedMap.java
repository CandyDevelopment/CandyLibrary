package fit.d6.candy.api.collection;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface FixedMap<K, V> extends Map<K, V> {

    int getMaxSize();

    @NotNull
    static <K, V> FixedMap<K, V> of(int maxSize) {
        return CollectionService.getService().createFixedMap(maxSize, true);
    }

    @NotNull
    static <K, V> FixedMap<K, V> of(int maxSize, boolean accessOrder) {
        return CollectionService.getService().createFixedMap(maxSize, accessOrder);
    }

}
