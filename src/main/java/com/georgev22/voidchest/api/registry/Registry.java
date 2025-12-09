package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.economy.bank.IVoidChestBank;
import com.georgev22.voidchest.api.economy.banktnt.IVoidChestBankTNT;
import com.georgev22.voidchest.api.economy.player.AEconomy;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import com.georgev22.voidchest.api.item.VoidSpecialItem;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.stacker.Stacker;
import com.georgev22.voidchest.api.upgrade.Upgrade;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A generic key-to-value registry with support for querying, replacement,
 * and optional automatic key extraction. This acts as a central mechanism
 * for storing configurable and extensible API components.
 *
 * @param <K> the type used as the registry key
 * @param <V> the type of values stored in the registry
 * @see KeyedRegistry
 * @see AbstractRegistry
 */
public interface Registry<K, V> {

    /**
     * Global registry for VoidChest banks.
     * <p>
     * Used for registering and retrieving bank implementations that handle
     * standard item economy storage in VoidChests.
     * </p>
     */
    KeyedRegistry<IVoidChestBank> BANK = new KeyedRegistry<>();

    /**
     * Global registry for TNT-based VoidChest banks.
     * <p>
     * Used for registering bank implementations specialized in TNT item
     * conversion and storage behavior.
     * </p>
     */
    KeyedRegistry<IVoidChestBankTNT> BANK_TNT = new KeyedRegistry<>();

    /**
     * Global registry for VoidChest upgrades.
     * <p>
     * Upgrades may modify capacity, speed, auto-sell features,
     * or provide custom-enhancing behavior.
     * </p>
     */
    KeyedRegistry<Upgrade<?>> UPGRADE = new KeyedRegistry<>();

    /**
     * Global registry for special items used by VoidChest features.
     * <p>
     * This may include custom items such as VoidKeys, filter tools,
     * or items that interact with VoidChest mechanics.
     * </p>
     */
    @ApiStatus.Experimental
    KeyedRegistry<VoidSpecialItem> SPECIAL_ITEM = new KeyedRegistry<>();

    /**
     * Global registry for profit calculator implementations.
     * <p>
     * Profit calculators determine how item values are computed during
     * auto-sell.
     * </p>
     */
    KeyedRegistry<ProfitCalculator> PROFIT_CALCULATOR = new KeyedRegistry<>();

    KeyedRegistry<Stacker> STACKER = new KeyedRegistry<>();

    /**
     * Global registry for player-related economy implementations.
     * <p>
     * Handles economy modes such as payouts, charges, and upgrades,
     * based on the plugin configuration.
     * </p>
     */
    KeyedRegistry<AEconomy> ECONOMY = new KeyedRegistry<>();

    /**
     * Registers a value with the given key.
     *
     * @param key   the key used to identify the value
     * @param value the value to register
     * @throws IllegalArgumentException if a value is already registered with the given key
     */
    void register(@NotNull K key, @NotNull V value) throws IllegalArgumentException;

    /**
     * Registers a value that can provide its own key.
     * <p>
     * The value type {@code V} must have a way of exposing its key (for example,
     * via a {@code getKey()} method). The registry will extract the key from
     * the value and use it for registration.
     * </p>
     *
     * @param value the value to register, which must provide its own key
     * @throws IllegalArgumentException if a value is already registered with the same key
     */
    void register(@NotNull V value) throws IllegalArgumentException;

    /**
     * Registers or replaces an existing value with the given key.
     *
     * @param key   the key used to identify the value
     * @param value the value to register or replace
     * @return {@code true} if an existing value was replaced, {@code false} if this is a new registration
     */
    boolean replaceOrRegister(@NotNull K key, @NotNull V value);

    /**
     * Registers or replaces a value that can provide its own key.
     * <p>
     * The value type {@code V} must have a way of exposing its key (for example,
     * via a {@code getKey()} method). The registry will extract the key from
     * the value and use it for registration.
     * </p>
     *
     * @param value the value to register or replace, which must provide its own key
     * @return {@code true} if an existing value was replaced, {@code false} if this is a new registration
     */
    boolean replaceOrRegister(@NotNull V value);

    /**
     * Unregisters a value by its key.
     *
     * @param key the key used to identify the value
     * @return {@code true} if the value was unregistered, {@code false} if not registered
     */
    boolean unregister(@NotNull K key);

    /**
     * Retrieves a registered value by its key.
     *
     * @param key the key used to look up the value
     * @return an {@link Optional} containing the value if found, or {@link Optional#empty()} if not registered
     */
    @NotNull Optional<V> get(K key);

    /**
     * Checks whether a value is registered under the given key.
     *
     * @param key the key to check
     * @return {@code true} if a value is registered under the key, {@code false} otherwise
     */
    boolean contains(@NotNull K key);

    /**
     * Returns an unmodifiable view of the registry entries.
     *
     * @return an {@link ObjectMap} containing all key–value pairs currently registered
     */
    @NotNull ObjectMap<K, V> entries();

    /**
     * Clears all entries from the registry.
     * <p>
     * If you don't know what this does, you probably don't need it.<br>
     * Intended for internal use only but kept public for API purposes.
     * </p>
     */
    @ApiStatus.Internal
    void clear();

    /**
     * Retrieves the currently selected value from the registry.
     * <p>
     * The selection logic is left to the implementation, which may
     * provide a default, config preference, or other means of determining
     * the selected value.
     * </p>
     *
     * @return an {@link Optional} containing the selected value if available,
     * or {@link Optional#empty()} if no selection is made
     */
    @NotNull Optional<V> getSelected();
}
