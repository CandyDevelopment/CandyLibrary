package fit.d6.candy.collection;

import fit.d6.candy.api.collection.FixedList;
import fit.d6.candy.exception.CollectionException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class FixedArrayList<E> extends ArrayList<E> implements FixedList<E> {

    private final int maxSize;

    public FixedArrayList(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @Override
    public void add(int index, E element) {
        int size = this.size();
        while (size >= maxSize) {
            index -= 1;
            size -= 1;
            this.removeRange(0, 1);
        }
        super.add(index, element);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> collection) {
        int size = this.size() + collection.size();
        while (size > maxSize) {
            size -= 1;
            this.removeRange(0, 1);
        }
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> collection) {
        int size = this.size() + collection.size();
        while (size > maxSize) {
            index = Math.max(0, index - 1);
            size -= 1;
            this.removeRange(0, 1);
        }
        return super.addAll(index, collection);
    }

    @Override
    public E set(int index, E element) {
        if (index >= this.size())
            throw new CollectionException("Out of elements");
        return super.set(index, element);
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

}
