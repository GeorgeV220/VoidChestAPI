package com.georgev22.voidchest.api.storage.data.voidstorage;

import org.jetbrains.annotations.NotNull;

/**
 * A class that represents an upgrade for a void storage.
 *
 * @param <U> the type of the upgrade object
 */
public class Upgrade<U> {

    private final String upgradeName;
    private final U object;

    /**
     * Constructs a new upgrade with a name and an object.
     *
     * @param upgradeName the name of the upgrade
     * @param upgrade     the upgrade object
     */
    public Upgrade(String upgradeName, U upgrade) {
        this.upgradeName = upgradeName;
        this.object = upgrade;
    }

    /**
     * Returns the name of the upgrade.
     *
     * @return the name of the upgrade
     */
    public String getUpgradeName() {
        return upgradeName;
    }

    /**
     * Returns the upgrade object.
     *
     * @return the upgrade object
     */
    public U getObject() {
        return object;
    }

    /**
     * Returns the class of the upgrade object.
     *
     * @return the class of the upgrade object
     */
    public Class<?> getObjectClass() {
        return object.getClass();
    }

    /**
     * Checks if the object is an instance of the specified class.
     *
     * @param clazz the class to check for instance
     * @return true if the object is an instance of the specified class, false otherwise
     */
    public boolean isInstanceOf(@NotNull Class<?> clazz) {
        return clazz.isInstance(object);
    }
}