package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.upgrade.Upgrade;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Registry for managing {@link Upgrade} instances keyed by {@link NamespacedKey}.
 */
public final class UpgradeRegistry
        extends AbstractRegistry<NamespacedKey, Upgrade<?>> {

    private static final UpgradeRegistry INSTANCE = new UpgradeRegistry();

    private UpgradeRegistry() {
    }

    public static UpgradeRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a new upgrade by its {@link NamespacedKey}.
     *
     * @param upgrade the upgrade to register
     * @throws IllegalArgumentException if the key is already registered
     */
    @Override
    public void register(@NotNull Upgrade<?> upgrade) throws IllegalArgumentException {
        super.register(upgrade.getKey(), upgrade);
    }

    /**
     * Registers or replaces an upgrade by its {@link NamespacedKey}.
     *
     * @param upgrade the upgrade to register or replace
     * @return true if replaced, false if newly registered
     */
    @Override
    public boolean replaceOrRegister(@NotNull Upgrade<?> upgrade) {
        return super.replaceOrRegister(upgrade.getKey(), upgrade);
    }
}
