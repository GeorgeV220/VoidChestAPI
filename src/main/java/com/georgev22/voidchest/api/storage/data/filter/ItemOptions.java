package com.georgev22.voidchest.api.storage.data.filter;

/**
 * Represents configuration options for how filtered items should be handled.
 * <p>
 * This class controls matching behavior for items in VoidChest filters, allowing
 * customization of how strictly items should be compared.
 * </p>
 */
public class ItemOptions implements Cloneable {
    private boolean ignoreItemMeta;
    private boolean ignoreItemAmount;

    /**
     * Creates a new ItemOptions configuration.
     *
     * @param ignoreItemMeta   If true, item metadata (enchantments, display name, lore, etc.)
     *                         will be ignored when matching items
     * @param ignoreItemAmount If true, item stack amounts will be ignored when matching items
     */
    public ItemOptions(boolean ignoreItemMeta, boolean ignoreItemAmount) {
        this.ignoreItemMeta = ignoreItemMeta;
        this.ignoreItemAmount = ignoreItemAmount;
    }

    /**
     * Gets whether item metadata should be ignored during comparisons.
     *
     * @return true if metadata is ignored, false if metadata must match exactly
     */
    public boolean isIgnoreItemMeta() {
        return ignoreItemMeta;
    }

    /**
     * Gets whether item stack amounts should be ignored during comparisons.
     *
     * @return true if stack size is ignored, false if amounts must match exactly
     */
    public boolean isIgnoreItemAmount() {
        return ignoreItemAmount;
    }

    /**
     * Creates a deep copy of this ItemOptions instance.
     *
     * @return A cloned {@link ItemOptions} with all current data
     */
    @Override
    public ItemOptions clone() {
        ItemOptions itemOptions;
        try {
            itemOptions = (ItemOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            itemOptions = new ItemOptions(this.ignoreItemMeta, this.ignoreItemAmount);
        }
        itemOptions.ignoreItemMeta = this.ignoreItemMeta;
        itemOptions.ignoreItemAmount = this.ignoreItemAmount;
        return itemOptions;
    }
}