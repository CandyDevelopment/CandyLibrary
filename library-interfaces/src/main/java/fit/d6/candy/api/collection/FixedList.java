package fit.d6.candy.api.collection;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FixedList<E> extends List<E> {

    int getMaxSize();

    @NotNull
    static <E> FixedList<E> ofArray(int maxSize) {
        return CollectionService.getService().createFixedArrayList(maxSize);
    }

    @NotNull
    static <E> FixedList<E> ofLinked(int maxSize) {
        return CollectionService.getService().createFixedLinkedList(maxSize);
    }

}
