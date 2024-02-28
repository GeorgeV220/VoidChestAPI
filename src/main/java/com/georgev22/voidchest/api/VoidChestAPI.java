package com.georgev22.voidchest.api;

import com.georgev22.library.minecraft.scheduler.MinecraftScheduler;
import com.georgev22.voidchest.api.bank.IBankManager;
import com.georgev22.voidchest.api.banktnt.IBankTNTManager;
import com.georgev22.voidchest.api.chunkseemanager.IChunkSeeManager;
import com.georgev22.voidchest.api.economy.IEconomyManager;
import com.georgev22.voidchest.api.event.EventManager;
import com.georgev22.voidchest.api.hologram.IHologramManager;
import com.georgev22.voidchest.api.inventory.VoidInventoryUtils;
import com.georgev22.voidchest.api.shop.IShopManager;
import com.georgev22.voidchest.api.stacker.IStackerManager;
import com.georgev22.voidchest.api.storage.IFilterManager;
import com.georgev22.voidchest.api.storage.IPlayerManager;
import com.georgev22.voidchest.api.storage.IVoidStorageManager;
import com.georgev22.voidchest.api.storage.voidmanager.IVoidItemManager;
import com.georgev22.voidchest.api.tasks.SellHandler;
import com.georgev22.voidchest.api.upgrades.UpgradeManager;
import com.georgev22.voidchest.api.utilities.config.voidchests.VoidStorageConfigurationFileCache;
import com.georgev22.voidchest.api.voideconomy.IVoidEconomyManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

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
                           IFilterManager filterManager,
                           EventManager eventManager,
                           MinecraftScheduler<Plugin, Location, World, Chunk, Entity> minecraftScheduler,
                           VoidStorageConfigurationFileCache voidStorageConfigurationFileCache,
                           SellHandler sellHandler,
                           VoidInventoryUtils voidInventoryUtils,
                           UpgradeManager upgradeManager) {

    /**
     * The singleton instance of the VoidChestAPI class.
     */
    private static VoidChestAPI instance;

    /**
     * The debug flag.
     */
    private static boolean debug = false;

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

    /**
     * Returns true if debug mode is enabled.
     *
     * @return true if debug mode is enabled
     */
    public static boolean debug() {
        return debug;
    }

    /**
     * Enables or disables debug mode.
     *
     * @param debug true to enable debug mode, false to disable
     */
    public static void setDebug(boolean debug) {
        VoidChestAPI.debug = debug;
    }
}
