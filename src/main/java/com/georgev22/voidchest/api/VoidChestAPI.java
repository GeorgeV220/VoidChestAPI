package com.georgev22.voidchest.api;

import com.georgev22.voidchest.api.booster.BoosterManager;
import com.georgev22.voidchest.api.chunkseemanager.IChunkSeeManager;
import com.georgev22.voidchest.api.hologram.IHologramManager;
import com.georgev22.voidchest.api.link.ILinkManager;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.registry.IRegistryManager;
import com.georgev22.voidchest.api.scheduler.MinecraftScheduler;
import com.georgev22.voidchest.api.shop.IShopManager;
import com.georgev22.voidchest.api.stacker.IStackerManager;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.IFilterManager;
import com.georgev22.voidchest.api.storage.InvalidEntityManager;
import com.georgev22.voidchest.api.storage.cache.IVoidItemManager;
import com.georgev22.voidchest.api.storage.cache.VoidChestCacheController;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.task.ITimedTaskManager;
import com.georgev22.voidchest.api.config.voidchests.VoidChestConfigurationFileCache;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The primary public API access point for VoidChest plugin functionality.
 * <p>
 * This class exposes all major subsystems such as shops, entity data managers,
 * schedulers, boosters, and configuration caching systems.
 * <p>
 * To access the API:
 * <pre>{@code
 * VoidChestAPI api = VoidChestAPI.getInstance();
 * api.<method>
 * }</pre>
 *
 */
public final class VoidChestAPI {

    /**
     * The globally available API instance once initialized.
     */
    private static VoidChestAPI instance;

    /**
     * Debug logging flag. When enabled, additional internal logs may appear.
     */
    private static boolean debug = false;

    /**
     * Whether the server environment is Folia-based.
     * Used to switch scheduling strategies where applicable.
     */
    private static boolean isFolia = false;

    private final JavaPlugin plugin;
    private final IRegistryManager registryManager;
    private final IShopManager shopManager;
    private final IHologramManager hologramManager;
    private final IChunkSeeManager chunkSeeManager;
    private final IVoidItemManager voidItemManager;
    private final VoidChestCacheController voidChestCacheController;
    private final IFilterManager filterManager;
    private final ILinkManager linkManager;
    private final MinecraftScheduler<Location, World, Chunk, Entity> minecraftScheduler;
    private final VoidChestConfigurationFileCache voidChestConfigurationFileCache;
    private final ITimedTaskManager timedTaskManager;
    private final BoosterManager boosterManager;

    /**
     * Constructs the API and provides all service dependencies from the plugin core.
     *
     * @param plugin             Bukkit plugin instance
     * @param shopManager        shop manager
     * @param hologramManager    hologram rendering service
     * @param chunkSeeManager    chunk preview/visualization manager
     * @param voidItemManager    void item storage/cache manager
     * @param filterManager      filter configuration manager
     * @param linkManager        chest linking manager
     * @param minecraftScheduler multi-platform safe task scheduler
     * @param timedTaskManager   timed task control system
     * @param boosterManager     booster effects manager
     */
    public VoidChestAPI(
            JavaPlugin plugin,
            IRegistryManager registryManager,
            IShopManager shopManager,
            IHologramManager hologramManager,
            IChunkSeeManager chunkSeeManager,
            IVoidItemManager voidItemManager,
            IFilterManager filterManager,
            ILinkManager linkManager,
            MinecraftScheduler<Location, World, Chunk, Entity> minecraftScheduler,
            ITimedTaskManager timedTaskManager,
            BoosterManager boosterManager) {
        this.plugin = plugin;
        this.registryManager = registryManager;
        this.shopManager = shopManager;
        this.hologramManager = hologramManager;
        this.chunkSeeManager = chunkSeeManager;
        this.voidItemManager = voidItemManager;
        this.voidChestCacheController = new VoidChestCacheController();
        this.filterManager = filterManager;
        this.linkManager = linkManager;
        this.minecraftScheduler = minecraftScheduler;
        this.voidChestConfigurationFileCache = new VoidChestConfigurationFileCache(plugin);
        this.timedTaskManager = timedTaskManager;
        this.boosterManager = boosterManager;
    }

    /**
     * Sets the global API instance.
     *
     * @param voidChestAPI the implementation instance
     * @return the assigned instance
     */
    public static VoidChestAPI setInstance(VoidChestAPI voidChestAPI) {
        return instance = voidChestAPI;
    }

    /**
     * @return the active VoidChest API instance
     */
    public static VoidChestAPI getInstance() {
        return instance;
    }

    /**
     * @return {@code true} if debug mode is enabled
     */
    public static boolean debug() {
        return debug;
    }

    /**
     * Enables or disables debug logging.
     *
     * @param debug whether debugging is active
     */
    public static void setDebug(boolean debug) {
        VoidChestAPI.debug = debug;
    }

    /**
     * @return {@code true} if running under Folia scheduling rules
     */
    public static boolean isFolia() {
        return isFolia;
    }

    /**
     * Sets whether the server environment is Folia.
     *
     * @param isFolia {@code true} if using Folia
     */
    public static void setIsFolia(boolean isFolia) {
        VoidChestAPI.isFolia = isFolia;
    }

    /**
     * Retrieves the entity manager for void chest storage.
     *
     * @return a non-null {@link EntityManager} for {@link IVoidChest}
     */
    public @NotNull EntityManager<IVoidChest> voidChestManager() {
        Optional<EntityManager<IVoidChest>> manager =
                EntityManagerRegistry.getInstance().getTyped(IVoidChest.class);
        return manager.orElseGet(() -> new InvalidEntityManager<>(IVoidChest.class));
    }

    /**
     * Retrieves the entity manager for player persistent storage.
     *
     * @return a non-null {@link EntityManager} for {@link IPlayerData}
     */
    public @NotNull EntityManager<IPlayerData> playerManager() {
        Optional<EntityManager<IPlayerData>> manager =
                EntityManagerRegistry.getInstance().getTyped(IPlayerData.class);
        return manager.orElseGet(() -> new InvalidEntityManager<>(IPlayerData.class));
    }

    /**
     * @return the plugin providing this API
     */
    public JavaPlugin plugin() {
        return plugin;
    }

    /**
     * @return the registry manager
     */
    public IRegistryManager getRegistryManager() {
        return registryManager;
    }

    /**
     * @return the shop manager
     */
    public IShopManager shopManager() {
        return shopManager;
    }

    /**
     * @return hologram manager
     */
    public IHologramManager hologramManager() {
        return hologramManager;
    }

    /**
     * @return chunk visualization manager
     */
    public IChunkSeeManager chunkSeeManager() {
        return chunkSeeManager;
    }

    /**
     * @return voidchest item caching manager
     */
    public IVoidItemManager voidItemManager() {
        return voidItemManager;
    }

    /**
     * @return chest caching subsystem
     */
    public VoidChestCacheController voidChestCacheController() {
        return voidChestCacheController;
    }

    /**
     * @return filter configuration manager
     */
    public IFilterManager filterManager() {
        return filterManager;
    }

    /**
     * @return chest linking manager
     */
    public ILinkManager linkManager() {
        return linkManager;
    }

    /**
     * @return minecraft scheduler abstraction (Bukkit + Folia safe)
     */
    public MinecraftScheduler<Location, World, Chunk, Entity> minecraftScheduler() {
        return minecraftScheduler;
    }

    /**
     * @return void chest configuration cache management
     */
    public VoidChestConfigurationFileCache voidChestConfigurationFileCache() {
        return voidChestConfigurationFileCache;
    }

    /**
     * @return timed task controller
     */
    public ITimedTaskManager timedTaskManager() {
        return timedTaskManager;
    }

    /**
     * @return booster controller
     */
    public BoosterManager boosterManager() {
        return boosterManager;
    }
}
