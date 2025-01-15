package com.georgev22.voidchest.api.hologram;

/**
 * The IVoidChestHologram interface provides methods for managing a VoidChest hologram.
 */
public interface IVoidChestHologram {

    /**
     * Checks if the hologram has been deleted.
     *
     * @return True if the hologram has been deleted, false otherwise.
     */
    boolean isDeleted();

    /**
     * Removes the hologram.
     */
    void remove();

    /**
     * Updates the hologram.
     */
    void update();

    /**
     * Calculates the location of the hologram.
     */
    void calculateLocation();

    /**
     * Retrieves the name of the hologram implementation.
     *
     * @return The name of the hologram implementation as a String.
     */
    String getName();

    /**
     * Retrieves the name of the hologram implementation. E.g. FancyHolograms
     *
     * @return The name of the hologram implementation as a String.
     */
    String getSimpleName();

}
