package com.georgev22.voidchest.api;

import com.georgev22.library.minecraft.scheduler.MinecraftScheduler;
import com.georgev22.voidchest.api.bank.IBankManager;
import com.georgev22.voidchest.api.banktnt.IBankTNTManager;
import com.georgev22.voidchest.api.chunkseemanager.IChunkSeeManager;
import com.georgev22.voidchest.api.economy.IEconomyManager;
import com.georgev22.voidchest.api.event.EventManager;
import com.georgev22.voidchest.api.hologram.IHologramManager;
import com.georgev22.voidchest.api.shop.IShopManager;
import com.georgev22.voidchest.api.stacker.IStackerManager;
import com.georgev22.voidchest.api.storage.IPlayerManager;
import com.georgev22.voidchest.api.storage.IVoidStorageManager;
import com.georgev22.voidchest.api.storage.voidmanager.IVoidItemManager;
import com.georgev22.voidchest.api.utilities.config.voidchests.VoidStorageConfigurationFileCache;
import com.georgev22.voidchest.api.voideconomy.IVoidEconomyManager;

/**
 * The VoidChestAPI class provides access to various managers and services for the VoidChest plugin.
 */
public record VoidChestAPI(IEconomyManager economyManager,
                           IVoidEconomyManager voidEconomyManager,
                           IShopManager shopManager,
                           IBankManager bankManager,
                           IBankTNTManager bankTNTManager,
                           IStackerManager stackerManager,
                           IHologramManager hologramManager,
                           IChunkSeeManager chunkSeeManager,
                           IVoidItemManager voidItemManager,
                           IPlayerManager playerManager,
                           IVoidStorageManager voidStorageManager,
                           EventManager eventManager,
                           MinecraftScheduler minecraftScheduler,
                           VoidStorageConfigurationFileCache voidStorageConfigurationFileCache) {

    /**
     * The singleton instance of the VoidChestAPI class.
     */
    private static VoidChestAPI instance;

    /**
     * Sets the singleton instance of the VoidChestAPI class.
     *
     * @param voidChestAPI The instance to set.
     * @return The instance that was set.
     */
    public static VoidChestAPI setInstance(VoidChestAPI voidChestAPI) {
        return instance = voidChestAPI;
    }

    /**
     * Retrieves the singleton instance of the VoidChestAPI class.
     *
     * @return The singleton instance.
     */
    public static VoidChestAPI getInstance() {
        return instance;
    }
}
