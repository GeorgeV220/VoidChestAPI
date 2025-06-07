package com.georgev22.voidchest.api;

import com.georgev22.voidchest.api.bank.IBankManager;
import com.georgev22.voidchest.api.banktnt.IBankTNTManager;
import com.georgev22.voidchest.api.chunkseemanager.IChunkSeeManager;
import com.georgev22.voidchest.api.economy.player.IEconomyManager;
import com.georgev22.voidchest.api.hologram.IHologramManager;
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
import com.georgev22.voidchest.api.task.ITimedTaskManager;
import com.georgev22.voidchest.api.utilities.config.voidchests.VoidChestConfigurationFileCache;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The VoidChestAPI class provides access to various managers and services for the VoidChest plugin.
 */
public record VoidChestAPI(JavaPlugin plugin,
                           IEconomyManager economyManager,
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
                           MinecraftScheduler<Plugin, Location, World, Chunk, Entity> minecraftScheduler,
                           VoidChestConfigurationFileCache voidChestConfigurationFileCache,
                           ITimedTaskManager timedTaskManager) {

    /**
     * The singleton instance of the VoidChestAPI class.
     */
    private static VoidChestAPI instance;

    /**
     * The debug flag.
     */
    private static boolean debug = false;

    /**
     * Is folia flag.
     */
    private static boolean isFolia = false;

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
     * Checks if the server is running Folia
     *
     * @return if the server is running Folia
     */
    public static boolean isFolia() {
        return isFolia;
    }

    /**
     * Sets if the server is running Folia
     *
     * @param isFolia if the server is running Folia
     */
    public static void setIsFolia(boolean isFolia) {
        VoidChestAPI.isFolia = isFolia;
    }

    /**
     * Retrieves the {@link EntityManager} associated with {@link IVoidChest} entities.
     *
     * @return The {@link EntityManager} associated with {@link IVoidChest} entities.
     */
    public @NotNull EntityManager<IVoidChest> voidChestManager() {
        @NotNull Optional<EntityManager<IVoidChest>> voidEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
        if (voidEntityManager.isEmpty()) {
            voidEntityManager = Optional.of(new InvalidEntityManager<>());
        }
        return voidEntityManager.get();
    }

    /**
     * Retrieves the {@link EntityManager} associated with {@link IPlayerData} entities.
     *
     * @return The {@link EntityManager} associated with {@link IPlayerData} entities.
     */
    public @NotNull EntityManager<IPlayerData> playerManager() {
        @NotNull Optional<EntityManager<IPlayerData>> playerEntityManager = EntityManagerRegistry.getManager(IPlayerData.class);
        if (playerEntityManager.isEmpty()) {
            playerEntityManager = Optional.of(new InvalidEntityManager<>());
        }
        return playerEntityManager.get();
    }
}
