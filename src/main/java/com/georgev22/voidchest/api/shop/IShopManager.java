package com.georgev22.voidchest.api.shop;

import java.util.List;

/**
 * Manages the creation, loading, and saving of shops within the VoidChest plugin.
 *
 * <p>The {@code IShopManager} interface provides methods for retrieving, loading, and saving shops.
 * It is responsible for managing the collection of shops associated with the VoidStorage plugin.
 * </p>
 *
 * <p>The methods in this interface include:
 * <ul>
 *     <li>{@code List<Shop> getShops()} - Returns a list of all registered shops.</li>
 *     <li>{@code Shop getShop(String voidStorageName)} - Returns the shop associated with the specified VoidStorage name.</li>
 *     <li>{@code void loadShop(String name)} - Loads a shop with the given name.</li>
 *     <li>{@code void saveShop(String name)} - Saves the state of a shop with the given name.</li>
 *     <li>{@code void loadShops()} - Loads all registered shops.</li>
 *     <li>{@code void saveShops()} - Saves the state of all registered shops.</li>
 * </ul>
 * </p>
 *
 * <p>Implementations of this interface should handle the storage and retrieval of shop data,
 * such as loading from and saving to a data source (e.g., a file, database).
 * </p>
 *
 * <p>Implementors may choose to throw appropriate exceptions or define additional methods for more specific behavior.
 * </p>
 *
 * @author com.georgev22
 * @version 1.0
 * @since 2023-11-10
 */
public interface IShopManager {

    /**
     * Gets a list of all registered shops.
     *
     * @return A list of all registered shops.
     */
    List<Shop> getShops();

    /**
     * Gets the shop associated with the specified VoidStorage name.
     *
     * @param voidStorageName The name of the VoidStorage associated with the shop.
     * @return The shop associated with the specified VoidStorage name, or {@code null} if not found.
     */
    Shop getShop(String voidStorageName);

    /**
     * Loads a shop with the given name.
     *
     * @param name The name of the shop to load.
     */
    void loadShop(String name);

    /**
     * Saves the state of a shop with the given name.
     *
     * @param name The name of the shop to save.
     */
    void saveShop(String name);

    /**
     * Loads all registered shops.
     */
    void loadShops();

    /**
     * Saves the state of all registered shops.
     */
    void saveShops();
}
