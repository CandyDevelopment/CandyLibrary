package fit.d6.candy.collection;

import fit.d6.candy.BukkitService;
import fit.d6.candy.api.collection.CollectionService;
import fit.d6.candy.api.collection.FixedList;
import fit.d6.candy.api.collection.FixedMap;
import org.jetbrains.annotations.NotNull;

public class BukkitCollectionService extends BukkitService implements CollectionService {

    @Override
    public @NotNull String getId() {
        return "collection";
    }

    @Override
    public @NotNull <K, V> FixedMap<K, V> createFixedMap(int maxSize, boolean accessOrder) {
        return new FixedLinkedHashMap<>(maxSize, accessOrder);
    }

    @Override
    public @NotNull <E> FixedList<E> createFixedArrayList(int maxSize) {
        return new FixedArrayList<>(maxSize);
    }

    @Override
    public @NotNull <E> FixedList<E> createFixedLinkedList(int maxSize) {
        return new FixedLinkedList<>(maxSize);
    }

}
