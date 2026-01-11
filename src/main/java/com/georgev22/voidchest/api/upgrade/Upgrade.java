package com.georgev22.voidchest.api.upgrade;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.config.OptionsUtil;
import com.georgev22.voidchest.api.economy.player.AEconomy;
import com.georgev22.voidchest.api.registry.Registries;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.*;

/**
 * Abstract class for defining upgrades in VoidChest plugin.
 * This class is immutable and thread-safe.
 *
 * @param <U> the type of the upgrade object associated with each level.
 */
public abstract class Upgrade<U> implements Cloneable, Keyed {

    private final String name;
    private final NamespacedKey key;
    private final int maxLevel;
    private final List<String> voidchestTypes = Collections.synchronizedList(new ArrayList<>());
    private List<UpgradeLevel<U>> levels = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constructs a new Upgrade.
     *
     * @param key      the unique key identifying this upgrade.
     * @param maxLevel the maximum level this upgrade can reach.
     */
    public Upgrade(String name, NamespacedKey key, int maxLevel) {
        this.name = name;
        if (maxLevel < 1) {
            throw new IllegalArgumentException("Max level must be at least 1.");
        }
        this.key = key;
        this.maxLevel = maxLevel;
    }

    public Upgrade(String name, NamespacedKey key, int maxLevel, List<String> voidchestTypes) {
        this(name, key, maxLevel);
        this.voidchestTypes.addAll(voidchestTypes);
    }

    public Upgrade(String name, NamespacedKey key, int maxLevel, List<String> voidchestTypes, List<UpgradeLevel<U>> levels) {
        this(name, key, maxLevel, voidchestTypes);
        this.levels.addAll(levels);
    }

    /**
     * Gets the unique key of this upgrade.
     *
     * @return the NamespacedKey.
     */
    public @NonNull NamespacedKey getKey() {
        return key;
    }

    /**
     * Gets the maximum level of this upgrade.
     *
     * @return the maximum level.
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Retrieves the upgrade level details for a specific level.
     *
     * @param level the level to retrieve.
     * @return an Optional containing the UpgradeLevel if present.
     */
    public Optional<UpgradeLevel<U>> getUpgradeLevel(int level) {
        return levels.stream().filter(upgradeLevel -> upgradeLevel.level() == level).findFirst();
    }

    /**
     * Gets the list of upgrade levels for this upgrade.
     *
     * @return the list of upgrade levels.
     */
    public List<UpgradeLevel<U>> getLevels() {
        return Collections.unmodifiableList(levels);
    }

    /**
     * Adds an upgrade level to this upgrade.
     *
     * @param level         the level to add.
     * @param price         the price of this level.
     * @param upgradeObject the upgrade object associated with this level.
     * @param displayItem   the display item for this level.
     * @param placeholders  the placeholders for this upgrade level.
     */
    public void addUpgradeLevel(int level, @NonNull BigDecimal price, @NonNull U upgradeObject, ItemStack displayItem, Map<String, String> placeholders) {
        addUpgradeLevel(new UpgradeLevel<>(level, price, upgradeObject, displayItem, placeholders));
    }

    /**
     * Adds an upgrade level to this upgrade.
     *
     * @param upgradeLevel the upgrade level to add.
     */
    public void addUpgradeLevel(@NonNull UpgradeLevel<U> upgradeLevel) {
        if (upgradeLevel.level() < 1 || upgradeLevel.level() > maxLevel) {
            throw new IllegalArgumentException("Level must be between 1 and " + maxLevel);
        }
        if (levels.stream().anyMatch(level -> level.level() == upgradeLevel.level())) {
            throw new IllegalArgumentException("Upgrade level " + upgradeLevel.level() + " already exists.");
        }
        levels.add(upgradeLevel);
    }

    /**
     * Checks if a specific level is defined for this upgrade.
     *
     * @param level the level to check.
     * @return true if the level is defined, false otherwise.
     */
    public boolean hasUpgradeLevel(int level) {
        return levels.stream().anyMatch(upgradeLevel -> upgradeLevel.level() == level);
    }

    /**
     * Retrieves the next upgrade level after a specific level.
     *
     * @param currentLevel the current level.
     * @return an Optional containing the next UpgradeLevel if present.
     */
    public Optional<UpgradeLevel<U>> getNextUpgradeLevel(int currentLevel) {
        for (int i = currentLevel + 1; i <= maxLevel; i++) {
            UpgradeLevel<U> level = getUpgradeLevel(i).orElse(null);
            if (level != null) {
                return Optional.of(level);
            }
        }
        return Optional.empty();
    }

    /**
     * Adds a voidchest type to this upgrade.
     *
     * @param voidchestType the voidchest type to add
     */
    public void addVoidchestType(String voidchestType) {
        voidchestTypes.add(voidchestType);
    }

    /**
     * Removes a voidchest type from this upgrade.
     *
     * @param voidchestType the voidchest type to remove
     */
    public void removeVoidchestType(String voidchestType) {
        voidchestTypes.remove(voidchestType);
    }

    /**
     * Checks if this upgrade is applicable to a specific voidchest type.
     *
     * @param voidchestType the voidchest type to check.
     * @return true if the upgrade is applicable, false otherwise.
     */
    public boolean isApplicableTo(String voidchestType) {
        return voidchestTypes.contains(voidchestType);
    }

    /**
     * Gets the list of voidchest types this upgrade is applicable to.
     *
     * @return the list of voidchest types.
     */
    public List<String> getVoidchestTypes() {
        return voidchestTypes;
    }

    /**
     * Returns the JSON representation of this upgrade.
     *
     * @return The JSON representation of this upgrade.
     */
    public abstract String toString();

    /**
     * Retrieves the name of the Upgrade
     *
     * @return The name of the Upgrade as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Classes should override this method to clone the upgrade if they wish to do so.
     *
     * @return a deep copy of the upgrade
     */
    @Override
    public Upgrade<U> clone() {
        try {
            Upgrade<U> clone = (Upgrade<U>) super.clone();
            clone.levels = new ArrayList<>(this.levels);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Attempts to upgrade this upgrade key on the given {@link AbstractVoidChest}, charging the
     * player using the upgrade economy if enabled.
     *
     * <p><strong>Return codes:</strong></p>
     * <ul>
     *   <li>{@code >0} — The new upgrade level was successfully applied.</li>
     *   <li>{@code -1} — The next upgrade level could not be found (misconfiguration).</li>
     *   <li>{@code -2} — The player does not have enough balance.</li>
     *   <li>{@code -3} — Withdrawal from the economy plugin failed.</li>
     *   <li>{@code -4} — This upgrade is not applicable to the VoidChest's type.</li>
     *   <li>{@code -5} — The economy plugin is not enabled or not registered.</li>
     * </ul>
     *
     * @param voidChest    the VoidChest to upgrade
     * @param currentLevel the current level (ignored internally)
     * @param playerUUID   the UUID of the player performing the upgrade
     * @return the new level, or a negative error code
     * @deprecated Use {@link #upgrade(AbstractVoidChest, UUID)} instead.
     */
    @Deprecated(forRemoval = true)
    public int upgrade(@NonNull AbstractVoidChest voidChest, int currentLevel, UUID playerUUID) {
        return this.upgrade(voidChest, playerUUID, true);
    }

    /**
     * Attempts to upgrade this upgrade key on the given {@link AbstractVoidChest}, optionally
     * deducting the cost from the player's upgrade economy balance.
     *
     * <p><strong>Return codes:</strong></p>
     * <ul>
     *   <li>{@code >0} — The new upgrade level was successfully applied.</li>
     *   <li>{@code 0}  — The maximum level has already been reached; no upgrade was performed.</li>
     *   <li>{@code -1} — The next upgrade level could not be found (configuration error).</li>
     *   <li>{@code -2} — The player does not have sufficient funds.</li>
     *   <li>{@code -3} — Withdrawal from the economy failed.</li>
     *   <li>{@code -4} — This upgrade is not applicable to the given VoidChest type.</li>
     *   <li>{@code -5} — The economy plugin is not enabled or not registered</li>
     * </ul>
     *
     * @param voidChest  the VoidChest being upgraded
     * @param playerUUID the UUID of the player purchasing the upgrade
     * @return the applied level, or an error code
     */
    public int upgrade(@NonNull AbstractVoidChest voidChest, UUID playerUUID) {
        return this.upgrade(voidChest, playerUUID, true);
    }

    /**
     * Internal upgrade handler with optional economy enforcement.
     * <p><strong>Return codes:</strong></p>
     * <ul>
     *   <li>{@code >0} — The new upgrade level was successfully applied.</li>
     *   <li>{@code 0}  — The maximum level has already been reached; no upgrade was performed.</li>
     *   <li>{@code -1} — The next upgrade level could not be found (configuration error).</li>
     *   <li>{@code -2} — The player does not have sufficient funds.</li>
     *   <li>{@code -3} — Withdrawal from the economy failed.</li>
     *   <li>{@code -4} — This upgrade is not applicable to the given VoidChest type.</li>
     *   <li>{@code -5} — The economy plugin is not enabled or not registered</li>
     * </ul>
     *
     * @param voidChest    the VoidChest being upgraded
     * @param playerUUID   the UUID of the player performing the upgrade
     * @param economyCheck whether to check and charge the player's balance
     * @return the new level if successful, or an error code
     */
    public int upgrade(@NonNull AbstractVoidChest voidChest, UUID playerUUID, boolean economyCheck) {
        int currentLevel = this.getCurrentLevel(voidChest);
        boolean isApplicable = this.isApplicableTo(voidChest.type());
        if (!isApplicable) {
            return -4;
        }
        if (currentLevel >= maxLevel) return 0; // Max level reached

        return getNextUpgradeLevel(currentLevel).map(nextUpgradeLevel -> {
            BigDecimal upgradeCost = nextUpgradeLevel.price();
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
            Optional<AEconomy> optionalEcon = Registries.ECONOMY.get(NamespacedKey.fromString(OptionsUtil.ECONOMY_UPGRADES.getStringValue()));
            if (optionalEcon.isEmpty()) {
                return -5;
            }
            AEconomy econ = optionalEcon.get();

            if (economyCheck) {
                if (econ.getBalance(player).compareTo(upgradeCost) < 0) {
                    return -2;
                }

                if (!econ.withdraw(player, upgradeCost)) {
                    return -3;
                }
            }

            voidChest.addUpgrade(key, nextUpgradeLevel.level());
            if (VoidChestAPI.debug()) logUpgrades(voidChest);

            return nextUpgradeLevel.level();
        }).orElse(-1);
    }

    /**
     * Gets the current level of this upgrade for the given VoidChest.
     *
     * @param voidChest the VoidChest to get the current level for
     * @return the current level of this upgrade for the given VoidChest
     */
    public int getCurrentLevel(@NonNull AbstractVoidChest voidChest) {
        return voidChest.upgrades().entrySet().stream()
                .filter(upg -> upg.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(1);
    }

    private void logUpgrades(@NonNull AbstractVoidChest voidChest) {
        VoidChestAPI.getInstance().plugin().getLogger().info("Upgrades for VoidChest:");
        for (Map.Entry<NamespacedKey, Integer> upgrade : voidChest.upgrades().entrySet()) {
            VoidChestAPI.getInstance().plugin().getLogger().info(upgrade.getKey() + ": " + upgrade.getValue());
        }
    }
}