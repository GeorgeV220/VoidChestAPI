package com.georgev22.voidchest.api.tasks;

import com.georgev22.voidchest.api.scheduler.SchedulerTask;
import com.georgev22.voidchest.api.storage.data.IPlayerData;

/**
 * The SellTaskHandler interface provides methods for managing the sale handling of a void chest entity.
 */
public interface SellTaskHandler {

    /**
     * Retrieves the attempt sale time for the specified VoidChest.
     *
     * @param playerData    The player data associated with the sell handler.
     * @param voidChestName The identifier of the VoidChest.
     * @return The attempt sale time for the VoidChest in milliseconds as a long.
     */
    long attemptSaleTime(IPlayerData playerData, String voidChestName);

    /**
     * Sets the attempt sale time for the specified VoidChest.
     *
     * @param playerData      The player data associated with the sell handler.
     * @param voidChestName   The identifier of the VoidChest.
     * @param attemptSaleTime The attempt sale time to set in milliseconds as a Long.
     */
    void attemptSaleTime(IPlayerData playerData, String voidChestName, Long attemptSaleTime);

    /**
     * Sets the task for the specified VoidChest.
     *
     * @param playerData    The player data associated with the sell handler.
     * @param voidChestName The identifier of the VoidChest.
     * @param task          The SchedulerTask to set.
     */
    void setTask(IPlayerData playerData, String voidChestName, SchedulerTask task);

    /**
     * Retrieves the task for the specified VoidChest.
     *
     * @param playerData    The player data associated with the sell handler.
     * @param voidChestName The identifier of the VoidChest.
     * @return The SchedulerTask for the VoidChest.
     */
    SchedulerTask task(IPlayerData playerData, String voidChestName);

    /**
     * Retrieves the task associated with all VoidChests.
     *
     * @param playerData The player data associated with the sell handler.
     * @return The SchedulerTask for all VoidChests.
     */
    SchedulerTask task(IPlayerData playerData);

    /**
     * Sets the task for all VoidChests.
     *
     * @param playerData The player data associated with the sell handler.
     * @param task       The SchedulerTask to set for all VoidChests.
     */
    void setTask(IPlayerData playerData, SchedulerTask task);

    /**
     * Attempts to start the sell task for the specified player data.
     *
     * @param data The player data to start the sell task for.
     */
    void attemptStartSellTask(IPlayerData data);

    /**
     * Attempts to stop the sell task for the specified player data.
     *
     * @param data The player data to stop the sell task for.
     */
    void attemptStopSellTask(IPlayerData data);
}
