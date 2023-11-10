package com.georgev22.voidchest.api.shop;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a shop interface that provides methods for managing shop items.
 *
 * <p>A shop is defined by a name and contains a collection of {@link ShopItem}s.
 * The interface provides methods to retrieve information about the shop, manage shop items,
 * and interact with the shop's inventory.
 * </p>
 *
 * <p>The methods in this interface include:
 * <ul>
 *     <li>{@code @NotNull String getName()} - Returns the name of the shop.</li>
 *     <li>{@code @NotNull List<ShopItem> getShopItems()} - Returns a list of all shop items in the shop.</li>
 *     <li>{@code @NotNull ShopItem getShopItem(@NotNull ItemStack itemStack)} - Returns the shop item associated with the given ItemStack.</li>
 *     <li>{@code void addItem(@NotNull ShopItem item)} - Adds a single shop item to the shop.</li>
 *     <li>{@code void addItems(@NotNull List<ShopItem> items)} - Adds a list of shop items to the shop.</li>
 *     <li>{@code void removeItem(@NotNull ShopItem item)} - Removes a shop item from the shop.</li>
 * </ul>
 * </p>
 *
 * <p>Implementations of this interface should provide functionality for managing the shop's inventory,
 * handling transactions, and other related operations specific to the shop's behavior.
 * </p>
 *
 * <p>Implementors may choose to throw appropriate exceptions or define additional methods for more specific behavior.
 * </p>
 */
public interface Shop {

    /**
     * Gets the name of the shop.
     *
     * @return The name of the shop.
     */
    @NotNull String getName();

    /**
     * Gets a list of all shop items in the shop.
     *
     * @return A list of shop items in the shop.
     */
    @NotNull List<ShopItem> getShopItems();

    /**
     * Gets the shop item associated with the given ItemStack.
     *
     * @param itemStack The ItemStack to look for in the shop.
     * @return The ShopItem associated with the ItemStack, or {@code null} if not found.
     */
    @NotNull ShopItem getShopItem(@NotNull ItemStack itemStack);

    /**
     * Adds a single shop item to the shop.
     *
     * @param item The shop item to add.
     */
    void addItem(@NotNull ShopItem item);

    /**
     * Adds a list of shop items to the shop.
     *
     * @param items The list of shop items to add.
     */
    void addItems(@NotNull List<ShopItem> items);

    /**
     * Removes a shop item from the shop.
     *
     * @param item The shop item to remove.
     */
    void removeItem(@NotNull ShopItem item);
}
