package com.georgev22.voidchest.api;

import com.georgev22.voidchest.api.bank.IBankManager;
import com.georgev22.voidchest.api.banktnt.IBankTNTManager;
import com.georgev22.voidchest.api.chunkseemanager.IChunkSeeManager;
import com.georgev22.voidchest.api.economy.IEconomyManager;
import com.georgev22.voidchest.api.event.EventManager;
import com.georgev22.voidchest.api.hologram.IHologramManager;
import com.georgev22.voidchest.api.inventory.VoidInventoryUtils;
import com.georgev22.voidchest.api.link.ILinkManager;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.scheduler.MinecraftScheduler;
import com.georgev22.voidchest.api.shop.IShopManager;
import com.georgev22.voidchest.api.stacker.IStackerManager;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.IFilterManager;
import com.georgev22.voidchest.api.storage.InvalidEntityManager;
import com.georgev22.voidchest.api.storage.cache.VoidChestCacheController;
import com.georgev22.voidchest.api.storage.cache.IVoidItemManager;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.tasks.SellTaskHandler;
import com.georgev22.voidchest.api.utilities.config.voidchests.VoidChestConfigurationFileCache;
import com.georgev22.voidchest.api.voideconomy.SellHandler;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidChestAPI class provides access to various managers and services for the VoidChest plugin.
 */
public record VoidChestAPI(JavaPlugin plugin,
                           IEconomyManager economyManager,
                           SellHandler sellHandler,
                           IShopManager shopManager,
                           IBankManager bankManager,
                           IBankTNTManager bankTNTManager,
                           IStackerManager stackerManager,
                           IHologramManager hologramManager,
                           IChunkSeeManager chunkSeeManager,
                           IVoidItemManager voidItemManager,
                           VoidChestCacheController voidChestCacheController,
                           IFilterManager filterManager,
                           ILinkManager linkManager,
                           EventManager eventManager,
                           MinecraftScheduler<Plugin, Location, World, Chunk, Entity> minecraftScheduler,
                           VoidChestConfigurationFileCache voidChestConfigurationFileCache,
                           SellTaskHandler sellTaskHandler,
                           VoidInventoryUtils voidInventoryUtils) {

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


    /**
     * Retrieves the {@link EntityManager} associated with {@link IVoidChest} entities.
     *
     * @return The {@link EntityManager} associated with {@link IVoidChest} entities.
     */
    public @NotNull EntityManager<IVoidChest> voidChestManager() {
        EntityManager<IVoidChest> voidEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
        if (voidEntityManager == null) {
            voidEntityManager = new InvalidEntityManager<>();
        }
        return voidEntityManager;
    }

    /**
     * Retrieves the {@link EntityManager} associated with {@link IPlayerData} entities.
     *
     * @return The {@link EntityManager} associated with {@link IPlayerData} entities.
     */
    public @NotNull EntityManager<IPlayerData> playerManager() {
        EntityManager<IPlayerData> playerEntityManager = EntityManagerRegistry.getManager(IPlayerData.class);
        if (playerEntityManager == null) {
            playerEntityManager = new InvalidEntityManager<>();
        }
        return playerEntityManager;
    }
}
