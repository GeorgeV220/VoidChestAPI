package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.datastructures.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.storage.data.filter.FilterData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Interface for managing item filters in VoidChest.
 * Provides methods for checking, adding, and removing filtered items globally,
 * per individual VoidChest, or based on VoidChest type.
 */
public interface IFilterManager {

    /**
     * Reloads all filters from storage or configuration.
     * This should be called to refresh the filter data.
     */
    void reloadAllFilters();

    /**
     * Checks whether an item is allowed in a specific VoidChest.
     * <p>
     * If the VoidChest is null, the global filter is used.
     *
     * @param item      The {@link ItemStack} to check.
     * @param voidChest The VoidChest to check against.
     * @return {@code true} if the item is allowed, otherwise {@code false}.
     */
    boolean isAllowed(ItemStack item, @Nullable IVoidChest voidChest);

    /**
     * Adds an item to the global filter list.
     * This affects all chests using global filtering.
     *
     * @param item         The {@link ItemStack} to be added to the global filter.
     * @param ignoreMeta   Whether to ignore item metadata (e.g., name, lore, enchantments).
     * @param ignoreAmount Whether to ignore item stack size.
     */
    void addGlobalFilterItem(ItemStack item, boolean ignoreMeta, boolean ignoreAmount);

    /**
     * Removes an item from the global filter list using the item's UUID.
     *
     * @param itemUUID The UUID of the item to be removed from the global filter.
     */
    void removeGlobalFilterItem(UUID itemUUID);

    /**
     * Adds an item to the filter for a specific VoidChest.
     *
     * @param chestUUID    The UUID of the VoidChest.
     * @param item         The {@link ItemStack} to be added to the filter.
     * @param ignoreMeta   Whether to ignore item metadata (e.g., name, lore, enchantments).
     * @param ignoreAmount Whether to ignore item stack size.
     */
    void addUUIDFilterItem(UUID chestUUID, ItemStack item, boolean ignoreMeta, boolean ignoreAmount);

    /**
     * Removes an item from the filter of a specific VoidChest using the item's UUID.
     *
     * @param chestUUID The UUID of the VoidChest.
     * @param itemUUID  The UUID of the item to be removed from the filter.
     */
    void removeUUIDFilterItem(UUID chestUUID, UUID itemUUID);

    /**
     * Adds an item to the filter for a specific VoidChest type.
     *
     * @param type         The type of the VoidChest (e.g., "iron", "gold").
     * @param item         The {@link ItemStack} to be added to the type filter.
     * @param ignoreMeta   Whether to ignore item metadata (e.g., name, lore, enchantments).
     * @param ignoreAmount Whether to ignore item stack size.
     */
    void addTypeFilterItem(String type, ItemStack item, boolean ignoreMeta, boolean ignoreAmount);

    /**
     * Removes an item from the filter of a specific VoidChest type using the item's UUID.
     *
     * @param type     The type of the VoidChest (e.g., "iron", "gold").
     * @param itemUUID The UUID of the item to be removed from the type filter.
     */
    void removeTypeFilterItem(String type, UUID itemUUID);

    /**
     * Retrieves all filters associated with specific VoidChests.
     *
     * @return An {@link UnmodifiableObjectMap} mapping VoidChest UUIDs to their respective {@link FilterData}.
     */
    UnmodifiableObjectMap<UUID, FilterData> getUUIDFilters();

    /**
     * Retrieves all filters associated with specific VoidChest types.
     *
     * @return An {@link UnmodifiableObjectMap} mapping VoidChest types to their respective {@link FilterData}.
     */
    UnmodifiableObjectMap<String, FilterData> getTypeFilters();

    /**
     * Retrieves the global filter data.
     *
     * @return The {@link FilterData} representing the global filter, or {@code null} if not found.
     */
    FilterData getGlobalFilter();

    /**
     * Retrieves the filter data for a specific VoidChest.
     *
     * @param chestUUID The UUID of the VoidChest.
     * @return The {@link FilterData} for the specified VoidChest, or {@code null} if not found.
     */
    FilterData getUUIDFilter(UUID chestUUID);

    /**
     * Retrieves the filter data for a specific VoidChest type.
     *
     * @param type The type of the VoidChest.
     * @return The {@link FilterData} for the specified VoidChest type, or {@code null} if not found.
     */
    FilterData getTypeFilter(String type);

    /**
     * Enum representing the filtering mode for items in a VoidChest.
     * {@code ALLOW} mode permits only the listed items, while {@code BLOCK} mode prevents them from being stored.
     */
    enum FilterMode {
        ALLOW, BLOCK;

        public static FilterMode fromString(String mode) {
            if (mode == null) throw new IllegalArgumentException("Mode cannot be null.");
            if (mode.equalsIgnoreCase("allow") || mode.equalsIgnoreCase("whitelist")) return ALLOW;
            if (mode.equalsIgnoreCase("block") || mode.equalsIgnoreCase("deny") || mode.equalsIgnoreCase("blacklist"))
                return BLOCK;
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
    }
}
