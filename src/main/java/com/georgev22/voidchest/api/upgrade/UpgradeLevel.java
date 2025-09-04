package com.georgev22.voidchest.api.upgrade;

import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents an individual level of an upgrade.
 *
 * @param <U> the type of the upgrade object associated with this level.
 */
public record UpgradeLevel<U>(int level, BigDecimal price, U upgradeObject,
                              ItemStack displayItem,
                              Map<String, String> placeholders) implements Comparable<UpgradeLevel<U>> {

    /**
     * Gets the price of this level.
     *
     * @return the price.
     */
    @Override
    public BigDecimal price() {
        return price;
    }

    /**
     * Gets the upgrade object of this level.
     *
     * @return the upgrade object.
     */
    @Override
    public U upgradeObject() {
        return upgradeObject;
    }

    /**
     * Gets the level of this upgrade level.
     *
     * @return the level.
     */
    @Override
    public int level() {
        return level;
    }

    /**
     * Gets the display item of this upgrade level.
     *
     * @return the display item.
     */
    public ItemStack displayItem() {
        return displayItem;
    }

    /**
     * Gets the placeholders of this upgrade level.
     *
     * @return the placeholders.
     */
    public Map<String, String> levelPlaceholders() {
        return placeholders;
    }

    @Override
    public int compareTo(UpgradeLevel<U> o) {
        return this.level() - o.level();
    }
}