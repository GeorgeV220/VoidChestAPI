package com.georgev22.voidchest.api.hologram;

import com.georgev22.voidchest.api.storage.data.IVoidChest;

/**
 * The IHologramManager interface provides methods for managing holograms.
 */
public interface IHologramManager {

    /**
     * Retrieves the type of the hologram manager.
     *
     * @return The type of the hologram manager as an Enum.
     */
    Enum<?> type();

    /**
     * Sets the type of the hologram manager.
     *
     * @param enu The type of the hologram manager to set as an Enum.
     */
    void type(Enum<?> enu);

    /**
     * Retrieves the hologram associated with the specified VoidChest.
     *
     * @param voidChest The VoidChest to retrieve the hologram for.
     * @return The hologram associated with the VoidChest.
     */
    IVoidChestHologram hologram(IVoidChest voidChest);

    /**
     * Hooks the hologram manager.
     */
    void hook();

}
