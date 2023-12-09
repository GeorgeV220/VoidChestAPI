
package com.georgev22.voidchest.api.utilities;

import java.util.ArrayList;

/**
 * An extension of ArrayList that allows pre-filling with null elements and ensures a maximum size limit.
 * This class is designed to hold a fixed number of elements, pre-filled with null values,
 * and can be used to update the elements at specific positions.
 */
public class NullablePreFilledArrayList<E> extends ArrayList<E> {

    /**
     * The maximum size allowed for this list.
     */
    private final int maxSize;

    /**
     * Constructs a new NullablePreFilledArrayList with the specified maximum size.
     *
     * @param maxSize the maximum size of the list (must be greater than 0)
     * @throws IllegalArgumentException if the specified maximum size is not greater than 0
     */
    public NullablePreFilledArrayList(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Maximum size must be greater than 0");
        }
        this.maxSize = maxSize;
        initializeListWithNull();
    }

    /**
     * Initializes the list with null values up to the maximum size.
     */
    private void initializeListWithNull() {
        for (int i = 0; i < maxSize; i++) {
            super.add(null);
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
        int nullIndex = super.indexOf(null);
        if (nullIndex == -1) {
            return false;
        }
        super.set(nullIndex, element);
        return true;
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
}
