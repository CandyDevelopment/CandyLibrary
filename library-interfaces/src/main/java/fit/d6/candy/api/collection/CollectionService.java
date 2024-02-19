package fit.d6.candy.api.collection;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface CollectionService extends Service {

    @NotNull <K, V> FixedMap<K, V> createFixedMap(int maxSize, boolean accessOrder);

    @NotNull <E> FixedList<E> createFixedArrayList(int maxSize);

    @NotNull <E> FixedList<E> createFixedLinkedList(int maxSize);

    @NotNull
    static CollectionService getService() {
        return CandyLibrary.getLibrary().getService(CollectionService.class);
    }

}
