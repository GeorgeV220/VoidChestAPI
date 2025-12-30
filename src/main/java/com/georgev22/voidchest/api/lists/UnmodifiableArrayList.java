package com.georgev22.voidchest.api.lists;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * An unmodifiable implementation of the {@link List} interface.
 *
 * @param <E> the type of elements in this list
 */
public class UnmodifiableArrayList<E> implements List<E> {

    private final List<E> underlyingList;

    /**
     * Constructs an unmodifiable list that is a copy of the specified list.
     *
     * @param originalList the list to be copied into an unmodifiable list
     */
    public UnmodifiableArrayList(List<E> originalList) {
        this.underlyingList = new ArrayList<>(originalList);
    }

    public UnmodifiableArrayList(Set<E> originalSet) {
        this.underlyingList = new ArrayList<>(originalSet);
    }

    @Override
    public int size() {
        return underlyingList.size();
    }

    @Override
    public boolean isEmpty() {
        return underlyingList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return underlyingList.contains(o);
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<? extends E> i = underlyingList.iterator();

            public boolean hasNext() {
                return i.hasNext();
            }

            public E next() {
                return i.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public Object @NotNull [] toArray() {
        return underlyingList.toArray();
    }

    @Override
    public <T> T @NotNull [] toArray(T @NotNull [] a) {
        return underlyingList.toArray(a);
    }

    @Override
    public boolean add(E t) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return new HashSet<>(underlyingList).containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }


    @Override
    public E get(int index) {
        return underlyingList.get(index);
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Cannot modify the unmodifiable list");
    }

    @Override
    public int indexOf(Object o) {
        return underlyingList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return underlyingList.lastIndexOf(o);
    }

    @Override
    public @NotNull ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            private final ListIterator<? extends E> i
                    = underlyingList.listIterator();

            public boolean hasNext() {
                return i.hasNext();
            }

            public E next() {
                return i.next();
            }

            public boolean hasPrevious() {
                return i.hasPrevious();
            }

            public E previous() {
                return i.previous();
            }

            public int nextIndex() {
                return i.nextIndex();
            }

            public int previousIndex() {
                return i.previousIndex();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public void set(E e) {
                throw new UnsupportedOperationException();
            }

            public void add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public @NotNull ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            private final ListIterator<? extends E> i
                    = underlyingList.listIterator(index);

            public boolean hasNext() {
                return i.hasNext();
            }

            public E next() {
                return i.next();
            }

            public boolean hasPrevious() {
                return i.hasPrevious();
            }

            public E previous() {
                return i.previous();
            }

            public int nextIndex() {
                return i.nextIndex();
            }

            public int previousIndex() {
                return i.previousIndex();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public void set(E e) {
                throw new UnsupportedOperationException();
            }

            public void add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public @NotNull List<E> subList(int fromIndex, int toIndex) {
        return new UnmodifiableArrayList<>(underlyingList.subList(fromIndex, toIndex));
    }
}