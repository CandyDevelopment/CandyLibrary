package fit.d6.candy.collection;

import fit.d6.candy.api.collection.FixedMap;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements FixedMap<K, V> {

    private final int maxSize;

    public FixedLinkedHashMap(int maxSize, boolean accessOrder) {
        super(maxSize, 0.75f, accessOrder);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() >= this.maxSize;
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

}
