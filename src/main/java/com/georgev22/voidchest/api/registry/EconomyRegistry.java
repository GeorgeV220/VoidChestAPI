package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.config.OptionsUtil;
import com.georgev22.voidchest.api.economy.player.AEconomy;
import com.georgev22.voidchest.api.economy.player.EconomyMode;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Registry for managing {@link AEconomy} implementations, each identified by a {@link NamespacedKey}.
 * <p>
 * This registry also provides accessors to retrieve specific economy types
 * such as payout, charge, and upgrade economies as configured through {@link OptionsUtil}.
 */
public final class EconomyRegistry extends AbstractRegistry<NamespacedKey, AEconomy> {

    private static final EconomyRegistry INSTANCE = new EconomyRegistry();

    private EconomyRegistry() {
    }

    /**
     * Retrieves the singleton instance of this registry.
     *
     * @return the global {@link EconomyRegistry} instance
     */
    public static EconomyRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a new economy using its associated {@link NamespacedKey}.
     *
     * @param value the economy implementation to register
     * @throws IllegalArgumentException if the economy key is already registered
     */
    @Override
    public void register(@NotNull AEconomy value) throws IllegalArgumentException {
        super.register(value.getKey(), value);
    }

    /**
     * Registers a new economy or replaces an existing one if the key already exists.
     *
     * @param value the economy implementation to register or replace
     * @return true if replaced, false if newly registered
     */
    @Override
    public boolean replaceOrRegister(@NotNull AEconomy value) {
        return super.replaceOrRegister(value.getKey(), value);
    }

    /**
     * Retrieves the configured payout economy.
     * <p>
     * This refers to the economy used for distributing rewards,
     * determined by {@link OptionsUtil#ECONOMY_PAYOUT}.
     *
     * @return an {@link Optional} containing the payout economy if registered
     */
    public @NotNull Optional<AEconomy> getPayoutEconomy() {
        try {
            NamespacedKey namespacedKey = NamespacedKey.fromString(OptionsUtil.ECONOMY_PAYOUT.getStringValue());
            return get(namespacedKey);
        } catch (ArrayIndexOutOfBoundsException e) {
            VoidChestAPI.getInstance().plugin().getLogger().log(Level.WARNING, "Failed to get payout economy.", e);
            return Optional.empty();
        }
    }

    /**
     * Retrieves the configured charge economy.
     * <p>
     * This refers to the economy used when costs are applied,
     * determined by {@link OptionsUtil#ECONOMY_CHARGE}.
     *
     * @return an {@link Optional} containing the charge economy if registered
     */
    public @NotNull Optional<AEconomy> getChargeEconomy() {
        try {
            NamespacedKey namespacedKey = NamespacedKey.fromString(OptionsUtil.ECONOMY_CHARGE.getStringValue());
            return get(namespacedKey);
        } catch (ArrayIndexOutOfBoundsException e) {
            VoidChestAPI.getInstance().plugin().getLogger().log(Level.WARNING, "Failed to get charge economy.", e);
            return Optional.empty();
        }
    }

    /**
     * Retrieves the configured upgrades economy.
     * <p>
     * This refers to the economy used for purchasing or upgrading features,
     * determined by {@link OptionsUtil#ECONOMY_UPGRADES}.
     *
     * @return an {@link Optional} containing the upgrades economy if registered
     */
    public @NotNull Optional<AEconomy> getUpgradesEconomy() {
        try {
            NamespacedKey namespacedKey = NamespacedKey.fromString(OptionsUtil.ECONOMY_UPGRADES.getStringValue());
            return get(namespacedKey);
        } catch (ArrayIndexOutOfBoundsException e) {
            VoidChestAPI.getInstance().plugin().getLogger().log(Level.WARNING, "Failed to get upgrades economy.", e);
            return Optional.empty();
        }
    }

    /**
     * Retrieves the economies that are registered for a specific economy mode.
     *
     * @param mode the economy mode to retrieve economies for
     * @return an unmodifiable list of economies registered for the specified mode
     */
    public @NotNull @Unmodifiable List<AEconomy> getEconomy(EconomyMode mode) {
        return this.entries().values().stream().filter(economy -> economy.getEconomyModes().contains(mode)).toList();
    }
}
