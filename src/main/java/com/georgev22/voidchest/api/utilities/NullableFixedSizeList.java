
package com.georgev22.voidchest.api.utilities;

import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An extension of ArrayList that allows pre-filling with null elements and ensures a maximum size limit.
 * This class is designed to hold a fixed number of elements, pre-filled with null values,
 * and can be used to update the elements at specific positions.
 */
public class NullableFixedSizeList<E> extends ArrayList<E> {

    /**
     * The maximum size allowed for this list.
     */
    private final int maxSize;

    /**
     * Constructs a new NullableFixedSizeList with the specified maximum size.
     *
     * @param maxSize the maximum size of the list (must be greater than 0)
     * @throws IllegalArgumentException if the specified maximum size is not greater than 0
     */
    public NullableFixedSizeList(int maxSize) {
        super(maxSize);
        if (maxSize == 0) {
            throw new IllegalArgumentException("Maximum size must be greater than 0");
        }
        this.maxSize = maxSize;
        initializeListWithNull();
    }

    public NullableFixedSizeList(int maxSize, Collection<? extends E> elements) {
        super(maxSize); // Initialize with the maximum size
        if (maxSize == 0) {
            throw new IllegalArgumentException("Maximum size must be greater than 0");
        }
        this.maxSize = maxSize;
        initializeListWithNull();
        addAll(elements);
    }

    /**
     * Initializes the list with null values up to the maximum size.
     */
    private void initializeListWithNull() {
        for (int i = 0; i < maxSize; i++) {
            super.add(null);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < maxSize; i++) {
            this.set(i, null);
        }
    }

    /**
     * Adds the specified element to the first null position in the list.
     *
     * @param element the element to be added
     * @return true if the element is added successfully, false if the list is already full
     * @throws IllegalStateException if the list is full and the element cannot be added
     */
    @Override
    public boolean add(E element) throws IllegalStateException {
        int nullIndex = this.indexOf(null);
        if (nullIndex == -1) {
            return false;
        }
        this.set(nullIndex, element);
        return true;
    }

    /**
     * Adds all the elements in the specified collection to the list,
     * replacing elements until the maximum size is reached.
     *
     * @param elements the collection containing elements to be added
     * @return true if the list is modified as a result of this operation
     */
    @Override
    public boolean addAll(Collection<? extends E> elements) {
        return replaceUntilMaxSize(elements).isEmpty();
    }

    /**
     * Replaces elements in the list with the provided elements until the maximum size is reached.
     *
     * @param elements the elements to be added
     * @return a map containing indexes of elements that were not added due to the list being full
     */
    public @NotNull ObjectMap<Integer, E> replaceUntilMaxSize(@NotNull Collection<? extends E> elements) {
        ObjectMap<Integer, E> indexMap = new HashObjectMap<>();

        int i = 0;
        for (E element : elements) {
            int nullIndex = this.indexOf(null);
            if (nullIndex == -1) {
                indexMap.put(i, element);
            } else {
                this.set(nullIndex, element);
            }
            i++;
        }

        return indexMap;
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index   the index of the element to replace
     * @param element the new element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public E set(int index, E element) {
        checkIndex(index);
        return super.set(index, element);
    }

    /**
     * Checks if the specified index is within the bounds of the list.
     *
     * @param index the index to be checked
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= maxSize) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    /**
     * Returns the maximum size allowed for this list.
     *
     * @return the maximum size of the list
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Checks if the list is empty by checking if all elements are null or if the size is 0 (super).
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        for (E e : this) {
            if (e != null) {
                return false;
            }
        }
        return super.isEmpty();
    }
}
