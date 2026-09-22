package com.georgev22.voidchest.api.integrations.economy;

import com.georgev22.voidchest.api.storage.model.AbstractPlayerData;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import com.georgev22.voidchest.api.utilities.CustomData;

import java.math.BigDecimal;

/**
 * Contains information about a VoidChest sell transaction.
 * <p>
 * A {@code SellInfo} instance provides the calculated sell price,
 * the player associated with the transaction, the VoidChest performing
 * the sale, and a {@link CustomData} container that can be used by
 * integrations to attach additional transaction-specific data.
 */
public class SellInfo {

    private final BigDecimal sellPrice;
    private final AbstractPlayerData playerData;
    private final AbstractVoidChest voidChest;
    private final CustomData customData;

    /**
     * Creates a new sell information instance.
     *
     * @param sellPrice  the calculated price of the items being sold
     * @param playerData the data of the player associated with the transaction
     * @param voidChest  the VoidChest performing the sale
     */
    public SellInfo(BigDecimal sellPrice, AbstractPlayerData playerData, AbstractVoidChest voidChest) {
        this.sellPrice = sellPrice;
        this.playerData = playerData;
        this.voidChest = voidChest;
        this.customData = new CustomData();
    }

    /**
     * Gets the calculated sell price for this transaction.
     *
     * @return the sell price
     */
    public BigDecimal sellPrice() {
        return this.sellPrice;
    }

    /**
     * Gets the player data associated with this transaction.
     *
     * @return the associated player data
     */
    public AbstractPlayerData playerData() {
        return this.playerData;
    }

    /**
     * Gets the VoidChest performing this transaction.
     *
     * @return the associated VoidChest
     */
    public AbstractVoidChest voidChest() {
        return this.voidChest;
    }

    /**
     * Gets the custom data container associated with this transaction.
     * <p>
     * Integrations may use this container to store additional
     * transaction-specific information.
     *
     * @return the custom data container
     */
    public CustomData customData() {
        return this.customData;
    }

}