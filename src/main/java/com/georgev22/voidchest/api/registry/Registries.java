package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.config.OptionsUtil;
import com.georgev22.voidchest.api.economy.bank.IVoidChestBank;
import com.georgev22.voidchest.api.economy.banktnt.IVoidChestBankTNT;
import com.georgev22.voidchest.api.economy.player.AEconomy;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import com.georgev22.voidchest.api.hologram.VoidHologram;
import com.georgev22.voidchest.api.item.VoidSpecialItem;
import com.georgev22.voidchest.api.registry.economy.ProfitCalculatorSelectorRegistry;
import com.georgev22.voidchest.api.stacker.Stacker;
import com.georgev22.voidchest.api.upgrade.Upgrade;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class Registries {

    /**
     * Global registry for VoidChest banks.
     * <p>
     * Used for registering and retrieving bank implementations that handle
     * standard item economy storage in VoidChests.
     * </p>
     */
    public static KeyedRegistry<IVoidChestBank> BANK = new KeyedRegistry<>() {
        @Override
        public @NotNull Optional<IVoidChestBank> getSelected() {
            return get(NamespacedKey.fromString(OptionsUtil.BANK.getStringValue()));
        }
    };

    /**
     * Global registry for TNT-based VoidChest banks.
     * <p>
     * Used for registering bank implementations specialized in TNT item
     * conversion and storage behavior.
     * </p>
     */
    public static KeyedRegistry<IVoidChestBankTNT> BANK_TNT = new KeyedRegistry<>() {
        @Override
        public @NotNull Optional<IVoidChestBankTNT> getSelected() {
            return get(NamespacedKey.fromString(OptionsUtil.BANK_TNT.getStringValue()));
        }
    };

    /**
     * Global registry for VoidChest upgrades.
     * <p>
     * Upgrades may modify capacity, speed, auto-sell features,
     * or provide custom-enhancing behavior.
     * </p>
     */
    public static KeyedRegistry<Upgrade<?>> UPGRADE = new KeyedRegistry<>();

    /**
     * Global registry for special items used by VoidChest features.
     * <p>
     * This may include custom items such as VoidKeys, filter tools,
     * or items that interact with VoidChest mechanics.
     * </p>
     */
    @ApiStatus.Experimental
    public static KeyedRegistry<VoidSpecialItem> SPECIAL_ITEM = new KeyedRegistry<>();

    /**
     * Global registry for profit calculator implementations.
     * <p>
     * Profit calculators determine how item values are computed during
     * auto-sell.
     * </p>
     */
    public static KeyedRegistry<ProfitCalculator> PROFIT_CALCULATOR = new KeyedRegistry<>();

    /**
     * Global registry for profit calculator selectors.
     * <p>
     * Profit calculator selectors determine which profit calculator to use
     * based on the plugin configuration.
     * </p>
     */
    public static ProfitCalculatorSelectorRegistry PROFIT_CALCULATOR_SELECTOR = ProfitCalculatorSelectorRegistry.getInstance();

    /**
     * Global registry for stacker implementations.
     * <p>
     * Stacker implementations determine how stacked items are handled.
     * </p>
     */
    public static KeyedRegistry<Stacker> STACKER = new KeyedRegistry<>() {
        @Override
        public @NotNull Optional<Stacker> getSelected() {
            return get(NamespacedKey.fromString(OptionsUtil.STACKER.getStringValue()));
        }
    };

    /**
     * Global registry for player-related economy implementations.
     * <p>
     * Handles economy modes such as payouts, charges, and upgrades,
     * based on the plugin configuration.
     * </p>
     */
    public static KeyedRegistry<AEconomy> ECONOMY = new KeyedRegistry<>();

    /**
     * Global registry for VoidChest hologram implementations.
     * <p>
     * Holograms are used to display information about VoidChests.
     * </p>
     */
    public static KeyedRegistry<VoidHologram> HOLOGRAM = new KeyedRegistry<>() {
        @Override
        public @NotNull Optional<VoidHologram> getSelected() {
            return get(NamespacedKey.fromString(OptionsUtil.HOLOGRAM.getStringValue()));
        }

        @Override
        public boolean unregister(@NonNull NamespacedKey key) {
            Optional<VoidHologram> hologramOptional = get(key);
            if (hologramOptional.isEmpty()) return false;
            hologramOptional.get().removeAll();
            return super.unregister(key);
        }
    };

    /**
     * Global registry for {@link com.georgev22.voidchest.api.storage.EntityManager} implementations.
     *
     * <p>This registry stores handlers responsible for loading, saving,
     * and managing persistent {@link com.georgev22.voidchest.api.storage.data.Entity}
     * data types such as player data and VoidChest records.</p>
     *
     * <h3>⚠️ Modification Warning</h3>
     * <ul>
     *     <li>This registry is managed internally by VoidChest.</li>
     *     <li>Plugins <strong>must not</strong> unregister or replace built-in managers.</li>
     *     <li>Plugins <strong>must not</strong> register new managers.</li>
     *     <li>Doing so may break data persistence and corrupt stored entities.</li>
     * </ul>
     */
    public static EntityManagerRegistry ENTITY_MANAGER = EntityManagerRegistry.getInstance();

}
