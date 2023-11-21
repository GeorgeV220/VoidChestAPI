package com.georgev22.voidchest.api.storage;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.library.utilities.EntityManager;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The IVoidStorageManager interface extends the EntityManager interface for managing VoidStorages.
 */
public interface IVoidStorageManager extends EntityManager<IVoidStorage> {

    /**
     * Retrieves a list of all VoidStorages.
     *
     * @return A list of VoidStorages.
     */
    List<IVoidStorage> voidStorages();

    /**
     * Retrieves a CompletableFuture that resolves to the VoidStorage with the specified UUID.
     *
     * @param uuid The UUID of the VoidStorage to retrieve.
     * @return A CompletableFuture that resolves to the VoidStorage.
     */
    CompletableFuture<IVoidStorage> voidStorage(UUID uuid);

    /**
     * Retrieves the VoidStorage associated with the specified block.
     *
     * @param block The block to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the block, or null if not found.
     */
    @Nullable IVoidStorage voidStorage(Block block);

    /**
     * Retrieves the VoidStorage associated with the specified chunk.
     *
     * @param chunk The chunk to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the chunk, or null if not found.
     */
    @Nullable IVoidStorage voidStorage(Chunk chunk);

    /**
     * Internal method. Retrieves a map of loaded entities.
     *
     * @return The map of loaded entities.
     */
    @Override
    @ApiStatus.Internal
    ObjectMap<UUID, IVoidStorage> getLoadedEntities();

    @NotNull Inventory inventory(IVoidStorage voidStorage, InventoryType type);

    @NotNull Inventory inventory(UUID voidStorageUUID, InventoryType type);

    enum InventoryType {
        STORAGE,
        WHITELIST,
        BLACKLIST,
        MENU
    }
}
