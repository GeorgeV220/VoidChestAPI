package com.georgev22.voidchest.api.shop;

import com.georgev22.library.yaml.serialization.ConfigurationSerializable;
import com.georgev22.library.yaml.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * The base class for defining items in a shop.
 *
 * <p>This class is intended to be extended for specific types of shop items.
 * It implements the {@link ConfigurationSerializable} interface for YAML serialization
 * and is annotated with {@link SerializableAs} to specify the serialized name as "ShopItem".
 * </p>
 *
 * <p>Each shop item has an associated {@link ItemStack} and a price represented by a {@link BigDecimal}.
 * The item and price can be set or retrieved using the corresponding methods.
 * </p>
 *
 * <p>There are two constructors available:
 * <ul>
 *     <li>{@code public ShopItem(@NotNull ItemStack item)}</li>
 *     <li>{@code public ShopItem(@NotNull ItemStack item, @NotNull BigDecimal price)}</li>
 * </ul>
 * </p>
 *
 * <p>Additionally, standard getter and setter methods are provided for the item and price.
 * The class overrides the {@code toString()} method to provide a string representation of the shop item.
 * </p>
 */
@SerializableAs("ShopItem")
public abstract class ShopItem implements ConfigurationSerializable, Cloneable {

    /**
     * The ItemStack representing the item in the shop.
     */
    private ItemStack item;

    /**
     * The price of the item in the shop.
     */
    private BigDecimal price;

    /**
     * Constructs a new ShopItem with the given ItemStack.
     *
     * @param item The ItemStack representing the item.
     */
    public ShopItem(@NotNull ItemStack item) {
        this(item, BigDecimal.ZERO);
    }

    /**
     * Constructs a new ShopItem with the given ItemStack and price.
     *
     * @param item  The ItemStack representing the item.
     * @param price The price of the item.
     */
    public ShopItem(@NotNull ItemStack item, @NotNull BigDecimal price) {
        this.item = item;
        this.price = price;
    }

    /**
     * Gets the ItemStack associated with this shop item.
     *
     * @return The ItemStack of the shop item, or {@code null} if not set.
     */
    public @Nullable ItemStack getItem() {
        return item;
    }

    /**
     * Gets the price of this shop item.
     *
     * @return The price of the shop item.
     */
    public @NotNull BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of this shop item.
     *
     * @param price The new price for the shop item.
     */
    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    /**
     * Sets the ItemStack for this shop item.
     *
     * @param item The new ItemStack for the shop item.
     */
    public void setItem(@NotNull ItemStack item) {
        this.item = item;
    }

    /**
     * Returns a string representation of the shop item.
     *
     * @return A string containing the item and its price.
     */
    @Override
    public String toString() {
        return "ShopItem{" +
                "item=" + item +
                ", price=" + price +
                '}';
    }

    /**
     * Returns a new instance of {@code ShopItem} that is a shallow copy of this object.
     *
     * <p>The method calls {@code super.clone()} to create a shallow copy of the object and then adjusts
     * the cloned object by creating a deep copy of the {@link #item} field using {@link ItemStack#clone()}.
     * This ensures that the cloned {@code ShopItem} is independent of the original, preventing unintended
     * side effects from modifications to the original {@code ItemStack} affecting the cloned {@code ShopItem}.
     * </p>
     *
     * <p>Overrides the {@code clone()} method in {@code Object} class. This method is intended to be
     * used when creating copies of {@code ShopItem} instances.</p>
     *
     * @return A new instance of {@code ShopItem} that is a shallow copy of this object.
     * @see Cloneable
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        ShopItem clone = (ShopItem) super.clone();


        if (item != null) {
            clone.setItem(item.clone());
        }

        return clone;
    }

}
