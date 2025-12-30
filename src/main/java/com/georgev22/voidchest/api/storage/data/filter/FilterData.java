package com.georgev22.voidchest.api.storage.data.filter;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FilterData implements Cloneable {
    private Map<String, ItemContainer> itemContainers = new HashMap<>();
    private transient Map<String, ItemStack> itemCache = new HashMap<>();

    /**
     * Adds an item to the filter with specified options.
     * <p>
     * <b>Note:</b> This operation only affects the in-memory data. Changes will not be saved
     * to persistent storage unless handled by {@link com.georgev22.voidchest.api.storage.IFilterManager}.
     * </p>
     *
     * @param uuid    The unique identifier for the filter entry
     * @param item    The {@link ItemStack} to add to the filter
     * @param options The {@link ItemOptions} configuring this filter entry
     */
    public void addItem(@NonNull UUID uuid, ItemStack item, ItemOptions options) {
        String key = uuid.toString();
        ItemContainer container = new ItemContainer(item, options);
        itemContainers.put(key, container);
        itemCache.put(key, container.getItem());
    }

    /**
     * Removes an item from the filter by its UUID.
     * <p>
     * <b>Note:</b> This operation only affects the in-memory data. Changes will not be saved
     * to persistent storage unless handled by {@link com.georgev22.voidchest.api.storage.IFilterManager}.
     * </p>
     *
     * @param uuid The unique identifier of the filter entry to remove
     */
    public void removeItem(@NonNull UUID uuid) {
        String key = uuid.toString();
        itemContainers.remove(key);
        itemCache.remove(key);
    }

    /**
     * Retrieves all filtered items mapped by their UUIDs.
     * <p>
     * The internal cache is built if empty by deserializing stored NBT data.
     * </p>
     *
     * @return A {@link Map} of UUIDs to their associated {@link ItemStack}s
     */
    public Map<UUID, ItemStack> getItems() {
        if (itemCache.isEmpty()) {
            itemContainers.forEach((key, container) ->
                    itemCache.put(key, container.getItem())
            );
        }
        return itemCache.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        UUID.fromString(entry.getKey()), entry.getValue())
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Retrieves the configuration options for all filtered items.
     *
     * @return A {@link Map} of UUIDs to their associated {@link ItemOptions}
     */
    public Map<UUID, ItemContainer> getItemContainers() {
        return itemContainers.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        UUID.fromString(entry.getKey()), entry.getValue())
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Creates a deep copy of this FilterData instance.
     *
     * @return A cloned {@link FilterData} with all current data
     */
    @Override
    public FilterData clone() {
        FilterData fData;
        try {
            fData = (FilterData) super.clone();
        } catch (CloneNotSupportedException e) {
            fData = new FilterData();
        }
        final FilterData filterData = fData;
        filterData.itemContainers = new HashMap<>();
        this.itemContainers.forEach((key, container) -> {
            ItemOptions clonedOptions = container.getOptions().clone();
            filterData.itemContainers.put(key, new ItemContainer(container.getNbt(), clonedOptions));
        });
        filterData.itemCache = new HashMap<>(this.itemCache);
        return filterData;
    }
}