package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.utilities.Entity;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The Stats interface extends the Entity interface and provides methods for managing statistics of an entity.
 */
public interface Stats extends Entity {

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

}