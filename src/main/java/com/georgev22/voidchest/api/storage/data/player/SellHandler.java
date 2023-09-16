package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.scheduler.interfaces.Task;
import com.georgev22.library.utilities.Entity;

/**
 * The SellHandler interface extends the Entity interface and provides methods for managing the sale handling of an entity.
 */
public interface SellHandler extends Entity {

    /**
     * Retrieves the attempt sale time for the specified VoidChest.
     *
     * @param voidChest The VoidChest to retrieve the attempt sale time for.
     * @return The attempt sale time for the VoidChest as a long.
     */
    long attemptSaleTime(final String voidChest);

    /**
     * Sets the attempt sale time for the specified VoidChest.
     *
     * @param voidChest       The VoidChest to set the attempt sale time for.
     * @param attemptSaleTime The attempt sale time to set as a Long.
     */
    void attemptSaleTime(final String voidChest, Long attemptSaleTime);

    /**
     * Sets the task for the specified VoidChest.
     *
     * @param voidChest The VoidChest to set the task for.
     * @param task      The task to set.
     */
    void setTask(final String voidChest, Task task);

    /**
     * Retrieves the task for the specified VoidChest.
     *
     * @param voidChest The VoidChest to retrieve the task for.
     * @return The task for the VoidChest.
     */
    Task task(final String voidChest);

    /**
     * Retrieves the task for all VoidChests.
     *
     * @return The task for all VoidChests.
     */
    Task task();

    /**
     * Sets the task for all VoidChests.
     *
     * @param task The task to set for all VoidChests.
     */
    void setTask(Task task);

}