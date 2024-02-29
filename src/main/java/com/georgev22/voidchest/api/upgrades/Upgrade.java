package com.georgev22.voidchest.api.upgrades;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * A class that represents an upgrade for a void storage.
 *
 * @param <U> the type of the upgrade object
 */
public abstract class Upgrade<U> {

    private final NamespacedKey upgradeName;
    private U object;
    private int level;
    private final int maxLevel;

    /**
     * Constructs a new upgrade with a name, an object, a level, and a maximum level.
     *
     * @param upgradeName the name of the upgrade
     * @param upgrade     the upgrade object
     * @param level       the level of the upgrade
     * @param maxLevel    the maximum level the upgrade can reach
     */
    public Upgrade(NamespacedKey upgradeName, U upgrade, int level, int maxLevel) {
        this.upgradeName = upgradeName;
        this.object = upgrade;
        this.level = level;
        this.maxLevel = maxLevel;
    }

    /**
     * Attempts to upgrade the object by accepting a consumer. Returns true if the upgrade is successful, false otherwise.
     *
     * @param consumer the consumer that applies the upgrade
     * @return true if the upgrade is successful, false otherwise
     */
    public boolean upgrade(Consumer<U> consumer) {
        if (this.level < this.maxLevel) {
            consumer.accept(object);
            this.level++;
            return true;
        }

        return false;
    }

    /**
     * Upgrades the object directly to the provided upgrade if the level is within acceptable bounds.
     *
     * @param upgrade the new upgrade object
     */
    public void upgrade(U upgrade) {
        if (level < maxLevel) {
            this.object = upgrade;
            this.level++;
        }
    }

    /**
     * Upgrades the object.
     * <p>
     * This method should be overridden in subclasses.
     */
    public abstract void upgrade();

    /**
     * Returns the name of the upgrade.
     *
     * @return the name of the upgrade
     */
    public String getUpgradeName() {
        return upgradeName.asString();
    }

    /**
     * Returns the NamespacedKey of the upgrade.
     *
     * @return the NamespacedKey of the upgrade
     */
    public NamespacedKey getUpgradeNamespacedKey() {
        return upgradeName;
    }

    /**
     * Returns the upgrade object.
     *
     * @return the upgrade object
     */
    public Optional<U> getObject() {
        return Optional.of(object);
    }

    /**
     * Sets the upgrade object.
     *
     * @param object the upgrade object
     */
    public void setObject(U object) {
        this.object = object;
    }

    /**
     * Sets the level of the upgrade.
     *
     * @param level the level of the upgrade
     */
    public void setLevel(int level) {
        this.level = level;
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
     * Returns the level of the upgrade.
     *
     * @return the level of the upgrade
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the maximum level of the upgrade.
     *
     * @return the maximum level of the upgrade
     */
    public int getMaxLevel() {
        return maxLevel;
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
