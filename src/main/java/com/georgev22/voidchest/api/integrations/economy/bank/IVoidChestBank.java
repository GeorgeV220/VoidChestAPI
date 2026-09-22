package com.georgev22.voidchest.api.integrations.economy.bank;

import com.georgev22.voidchest.api.integrations.economy.SellInfo;
import org.bukkit.Keyed;
import org.jspecify.annotations.NonNull;


/**
 * The IVoidChestBank interface provides methods for managing a VoidChest bank.
 */
public interface IVoidChestBank extends Keyed {

    /**
     * Deposits the amount represented by the provided {@link SellInfo}
     * into the associated VoidChest bank.
     *
     * @param sellInfo the sell transaction information containing the amount,
     *                 player data, VoidChest, and any associated custom data
     * @return {@code true} if the deposit was successful, {@code false} otherwise
     */
    boolean depositToBank(@NonNull SellInfo sellInfo);

    /**
     * Retrieves the simple name of the VoidChest bank. E.g. SuperiorSkyblock2
     *
     * @return The simple name of the VoidChest bank as a String.
     */
    String getName();
}
