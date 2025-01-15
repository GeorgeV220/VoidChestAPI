package com.georgev22.voidchest.api.storage.data.voidchest;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The Stats interface provides methods for managing statistics of a void chest.
 */
public interface Stats {

    /**
     * Retrieves the amount of money associated with the void chest.
     *
     * @return The amount of money as a BigDecimal.
     */
    BigDecimal money();

    /**
     * Sets the amount of money associated with the void chest.
     *
     * @param money The amount of money to set as a BigDecimal.
     */
    void money(BigDecimal money);

    /**
     * Adds the specified amount of money to the current amount associated with the void chest.
     *
     * @param money The amount of money to add as a BigDecimal.
     */
    void addMoney(BigDecimal money);

    /**
     * Retrieves the number of items stored in the void chest.
     *
     * @return The number of items stored as a BigInteger.
     */
    BigInteger itemsStored();

    /**
     * Sets the number of items stored in the void chest.
     *
     * @param itemsStored The number of items stored to set as a BigInteger.
     */
    void itemsStored(BigInteger itemsStored);

    /**
     * Adds the specified number of items stored to the current count associated with the void chest.
     *
     * @param itemsStored The number of items stored to add as a BigInteger.
     */
    void addItemsStored(BigInteger itemsStored);

    /**
     * Retrieves the number of items sold by the void chest.
     *
     * @return The number of items sold as a BigInteger.
     */
    BigInteger itemsSold();

    /**
     * Sets the number of items sold by the void chest.
     *
     * @param itemsSold The number of items sold to set as a BigInteger.
     */
    void itemsSold(BigInteger itemsSold);

    /**
     * Adds the specified number of items sold to the current count associated with the void chest.
     *
     * @param itemsSold The number of items sold to add as a BigInteger.
     */
    void addItemsSold(BigInteger itemsSold);

    /**
     * Retrieves the number of items purged by the void chest.
     *
     * @return The number of items purged as a BigInteger.
     */
    BigInteger itemsPurged();

    /**
     * Sets the number of items purged by the void chest.
     *
     * @param itemsPurged The number of items purged to set as a BigInteger.
     */
    void itemsPurged(BigInteger itemsPurged);

    /**
     * Adds the specified number of items purged to the current count associated with the void chest.
     *
     * @param itemsPurged The number of items purged to add as a BigInteger.
     */
    void addItemsPurged(BigInteger itemsPurged);

    /**
     * Adds custom data to the Stats with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Stats with the added custom data
     */
    default Stats addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the Stats with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Stats with the added custom data (if the key did not already exist)
     */
    default Stats addCustomDataIfNotExists(String key, Object value) {
        this.getCustomData().appendIfTrue(key, value, !this.getCustomData().containsKey(key));
        return this;
    }

    /**
     * Retrieves the value of the custom data associated with the specified key.
     *
     * @param key the key of the custom data
     * @param <T> the type of the value to retrieve
     * @return the value associated with the specified key, or {@code null} if the key does not exist
     */
    default <T> T getCustomData(String key) {
        return (T) getCustomData().get(key);
    }

    /**
     * Retrieves the map of custom data associated with the Stats.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the void chest stats
     */
    ConcurrentObjectMap<String, Object> getCustomData();
}
