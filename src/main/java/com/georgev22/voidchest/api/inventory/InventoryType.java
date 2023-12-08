package com.georgev22.voidchest.api.inventory;

/**
 * An enumeration representing different types of void chest inventories.
 */
public enum InventoryType {
    /**
     * Represents the storage inventory type for storing items in a void chest.
     */
    STORAGE,

    /**
     * Represents the whitelist inventory type for managing whitelist entries in a void chest.
     */
    WHITELIST,

    /**
     * Represents the blacklist inventory type for managing blacklist entries in a void chest.
     */
    BLACKLIST,

    /**
     * Represents the menu inventory type for displaying a menu in a void chest.
     */
    MENU
}