package fit.d6.candy.api.collection;

import java.util.Map;

public interface FixedMap<K, V> extends Map<K, V> {

    int getMaxSize();

}
