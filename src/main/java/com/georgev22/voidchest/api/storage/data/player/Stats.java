package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The Stats interface provides methods for managing statistics of the player stats.
 */
public interface Stats {

    /**
     * Checks if messaging is enabled.
     *
     * @return True if messaging is enabled, false otherwise.
     */
    boolean message();

    /**
     * Checks if offline messaging is enabled.
     *
     * @return True if offline messaging is enabled, false otherwise.
     */
    boolean offlineMessage();

    /**
     * Sets the messaging state.
     *
     * @param message True to enable messaging, false to disable it.
     */
    void message(boolean message);

    /**
     * Sets the offline messaging state.
     *
     * @param offlineMessage True to enable offline messaging, false to disable it.
     */
    void offlineMessage(boolean offlineMessage);

    /**
     * Retrieves the amount of money stored.
     *
     * @return The amount of money stored as a BigDecimal.
     */
    BigDecimal moneyStored();

    /**
     * Sets the amount of money stored.
     *
     * @param moneyStored The amount of money stored to set as a BigDecimal.
     */
    void moneyStored(BigDecimal moneyStored);

    /**
     * Adds the specified amount of money to the current amount stored.
     *
     * @param bigDecimal The amount of money to add as a BigDecimal.
     */
    void addMoneyStored(BigDecimal bigDecimal);

    /**
     * Retrieves the amount of total money stored.
     *
     * @return The amount of money stored as a BigDecimal.
     */
    BigDecimal totalMoneyStored();

    /**
     * Sets the amount of total money stored.
     *
     * @param moneyStored The amount of money stored to set as a BigDecimal.
     */
    void totalMoneyStored(BigDecimal moneyStored);

    /**
     * Adds the specified amount of money to the current total amount stored.
     *
     * @param bigDecimal The amount of money to add as a BigDecimal.
     */
    void addTotalMoneyStored(BigDecimal bigDecimal);

    /**
     * Retrieves the number of items sold stored.
     *
     * @return The number of items sold stored as a BigInteger.
     */
    BigInteger itemsSoldStored();

    /**
     * Sets the number of items sold stored.
     *
     * @param itemsSoldStored The number of items sold stored to set as a BigInteger.
     */
    void itemsSoldStored(BigInteger itemsSoldStored);

    /**
     * Adds the specified number of items sold to the current count stored.
     *
     * @param itemsSoldStored The number of items sold to add as a BigInteger.
     */
    void addItemsSoldStored(BigInteger itemsSoldStored);

    /**
     * Retrieves the number of total items sold stored.
     *
     * @return The number of total items sold stored as a BigInteger.
     */
    BigInteger totalItemsSoldStored();

    /**
     * Sets the number of total items sold stored.
     *
     * @param itemsSoldStored The number of total items sold stored to set as a BigInteger.
     */
    void totalItemsSoldStored(BigInteger itemsSoldStored);

    /**
     * Adds the specified number of total items sold to the current count stored.
     *
     * @param itemsSoldStored The number of items sold to add as a BigInteger.
     */
    void addTotalItemsSoldStored(BigInteger itemsSoldStored);

    /**
     * Retrieves the number of items purged stored.
     *
     * @return The number of items purged stored as a BigInteger.
     */
    BigInteger itemsPurgedStored();

    /**
     * Sets the number of items purged stored.
     *
     * @param itemsPurgedStored The number of items purged stored to set as a BigInteger.
     */
    void itemsPurgedStored(BigInteger itemsPurgedStored);

    /**
     * Adds the specified number of items purged to the current count stored.
     *
     * @param itemsPurgedStored The number of items purged to add as a BigInteger.
     */
    void addItemsPurgedStored(BigInteger itemsPurgedStored);

    /**
     * Retrieves the number of total items purged stored.
     *
     * @return The number of total items purged stored as a BigInteger.
     */
    BigInteger totalItemsPurgedStored();

    /**
     * Sets the number of total items purged stored.
     *
     * @param itemsPurgedStored The number of total items purged stored to set as a BigInteger.
     */
    void totalItemsPurgedStored(BigInteger itemsPurgedStored);

    /**
     * Adds the specified number of total items purged to the current count stored.
     *
     * @param itemsPurgedStored The number of items purged to add as a BigInteger.
     */
    void addTotalItemsPurgedStored(BigInteger itemsPurgedStored);

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
     * @return the {@link ConcurrentObjectMap} containing the custom data of the player stats
     */
    ConcurrentObjectMap<String, Object> getCustomData();
}
