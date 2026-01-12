package com.georgev22.voidchest.api.storage.model;

import com.georgev22.voidchest.api.integrations.economy.balance.EconomyMode;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.model.player.Stats;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The AbstractPlayerData interface provides methods for managing player data.
 */
public abstract class AbstractPlayerData extends Entity {

    public AbstractPlayerData(UUID uniqueId) {
        super(uniqueId);
    }

    /**
     * Retrieves the name of the player associated with the data.
     *
     * @return The name of the player as a String.
     */
    public abstract String name();

    /**
     * Sets the name of the player associated with the data.
     *
     * @param name The name of the player as a String.
     */
    public abstract void name(String name);

    /**
     * Retrieves the list of UUIDs of the VoidChests associated with the player.
     *
     * @return The list of UUIDs of VoidChests as an ArrayList.
     */
    public abstract ArrayList<UUID> voidChests();

    /**
     * Retrieves the statistics of the player.
     *
     * @return The statistics of the player as a Stats object.
     */
    public abstract Stats stats();

    /**
     * Sets the statistics of the player.
     *
     * @param stats The statistics of the player as a Stats object.
     */
    public abstract void stats(Stats stats);

    /**
     * Retrieves the current balance of the player.
     * <p>
     * Note: This method does not update the balance of the player.
     * The balance is retrieved as a BigDecimal
     * to ensure precision, especially when dealing with large values.
     * <p>
     * Important: Some Economy implementations may not return accurate balances.
     * For instance, when using Vault,
     * the limitation arises from Vault's use of double precision,
     * which can lead to inaccuracies if the balance exceeds
     * the limits of a double.
     * In such cases, consider alternative methods or implementations to handle large balances.
     *
     * @param economyMode The {@link EconomyMode} to use.
     * @return The current balance of the player as a BigDecimal.
     */
    public abstract BigDecimal balance(EconomyMode economyMode);

    /**
     * Closes all open VoidChest inventories for the player.
     */
    public abstract void closeVoidInventories();

    /**
     * Empties all VoidChests associated with the player.
     */
    public abstract void emptyStorages();

    /**
     * Removes all VoidChests associated with the player.
     */
    public abstract void removeAllChests();

    /**
     * Removes the specified VoidChest from the player's list of associated storages.
     *
     * @param voidChest The VoidChest to remove.
     */
    public abstract void removeVoidChest(final AbstractVoidChest voidChest);

    /**
     * Removes the specified VoidChest from the player's list of associated storages.
     *
     * @param voidChest The VoidChest to remove.
     */
    public abstract void removeVoidChest(final UUID voidChest);

    /**
     * Adds the specified VoidChest to the player's list of associated storages.
     *
     * @param voidChest The VoidChest to add.
     */
    public abstract void addVoidChest(final AbstractVoidChest voidChest);

    /**
     * Adds the specified VoidChest to the player's list of associated storages.
     *
     * @param voidChest The VoidChest to add.
     */
    public abstract void addVoidChest(final UUID voidChest);

    /**
     * Reloads the player data.
     */
    public abstract void reloadPlayerData();

    /**
     * Retrieves a map of placeholders for player and the specified VoidChest.
     *
     * @param voidChest The VoidChest to retrieve placeholders for.
     * @return A map of placeholders.
     */
    public abstract ObjectMap<String, String> getPlaceHolders(AbstractVoidChest voidChest);
}
