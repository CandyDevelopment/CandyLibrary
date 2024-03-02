package fit.d6.candy.bukkit.collection;

import fit.d6.candy.api.collection.FixedList;
import fit.d6.candy.bukkit.exception.CollectionException;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class FixedLinkedList<E> extends LinkedList<E> implements FixedList<E> {

    private final int maxSize;

    public FixedLinkedList(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(E e) {
        while (this.size() >= maxSize)
            this.removeFirst();
        return super.add(e);
    }

    @Override
    public void add(int index, E element) {
        throw new CollectionException("FixedList cannot insert an element");
    }

    @Override
    public void addFirst(E e) {
        throw new CollectionException("FixedList cannot insert an element");
    }

    @Override
    public void addLast(E e) {
        while (this.size() >= maxSize)
            this.removeFirst();
        super.addLast(e);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> collection) {
        int overSize = this.size() + collection.size();
        while (overSize > maxSize) {
            this.removeFirst();
            overSize -= 1;
        }
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> collection) {
        throw new CollectionException("FixedList cannot insert an element");
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
