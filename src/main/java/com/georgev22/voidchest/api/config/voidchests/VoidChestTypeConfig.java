package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.integrations.economy.profit.ProfitCalculatorSelector;
import org.bukkit.Material;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public record VoidChestTypeConfig(AutoSell autoSell, ToggleFeature purge, ToggleFeature bank,
                                  ChunkCollector chunkCollector, Links links, Charge charge, Hologram hologram,
                                  Block block, Interaction interaction, Placement placement, Breaking breaking,
                                  Explosions explosions, Notifications notifications, Item item, Menus menus,
                                  Sounds sounds, Worlds worlds) {

    public static class Feature {

        private final boolean enabled;

        public Feature(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Whether this feature is available for this VoidChest type.
         */
        public boolean isEnabled() {
            return enabled;
        }
    }

    public static class ToggleFeature extends Feature {

        private final boolean startValue;

        public ToggleFeature(boolean enabled, boolean startValue) {
            super(enabled);
            this.startValue = startValue;
        }

        /**
         * Initial state assigned when a new VoidChest is placed.
         * <p>
         * This does not represent the current state of an existing VoidChest.
         */
        public boolean getStartValue() {
            return startValue;
        }
    }

    public static final class AutoSell extends ToggleFeature {

        private final long interval;
        private final double booster;
        private final List<String> profitCalculators;
        private final ProfitCalculatorSelector.SelectorType selectorType;

        public AutoSell(
                boolean enabled,
                boolean startValue,
                long interval,
                double booster,
                List<String> profitCalculators,
                ProfitCalculatorSelector.SelectorType selectorType
        ) {
            super(enabled, startValue);
            this.interval = interval;
            this.booster = booster;
            this.profitCalculators = Collections.unmodifiableList(profitCalculators);
            this.selectorType = selectorType;
        }

        public long getInterval() {
            return interval;
        }

        public double getBooster() {
            return booster;
        }

        public List<String> getProfitCalculators() {
            return profitCalculators;
        }

        public ProfitCalculatorSelector.SelectorType getSelectorType() {
            return selectorType;
        }
    }

    public static final class ChunkCollector extends ToggleFeature {

        private final CollectorMode mode;
        private final ToggleFeature transferNonSellables;
        private final Feature filters;

        public ChunkCollector(
                boolean enabled,
                boolean startValue,
                CollectorMode mode,
                ToggleFeature transferNonSellables,
                Feature filters
        ) {
            super(enabled, startValue);
            this.mode = mode;
            this.transferNonSellables = transferNonSellables;
            this.filters = filters;
        }

        public CollectorMode getMode() {
            return mode;
        }

        public ToggleFeature getTransferNonSellables() {
            return transferNonSellables;
        }

        public Feature getFilters() {
            return filters;
        }
    }

    public enum CollectorMode {
        CHUNK,
        BOUNDINGBOX
    }

    public static final class Links extends Feature {

        private final int limit;

        public Links(boolean enabled, int limit) {
            super(enabled);
            this.limit = limit;
        }

        public int getLimit() {
            return limit;
        }
    }

    public static final class Charge extends ToggleFeature {

        private final long maxTime;
        private final long renewalTime;
        private final BigDecimal price;
        private final ChargePlaceholders placeholders;

        public Charge(
                boolean enabled,
                boolean startValue,
                long maxTime,
                long renewalTime,
                BigDecimal price,
                ChargePlaceholders placeholders
        ) {
            super(enabled, startValue);
            this.maxTime = maxTime;
            this.renewalTime = renewalTime;
            this.price = price;
            this.placeholders = placeholders;
        }

        public long getMaxTime() {
            return maxTime;
        }

        public long getRenewalTime() {
            return renewalTime;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public ChargePlaceholders getPlaceholders() {
            return placeholders;
        }
    }

    public record ChargePlaceholders(String hologramNoFuel, String itemNoFuel) {
    }

    public static final class Hologram extends ToggleFeature {

        private final double height;
        private final List<String> text;

        public Hologram(
                boolean enabled,
                boolean startValue,
                double height,
                List<String> text
        ) {
            super(enabled, startValue);
            this.height = height;
            this.text = Collections.unmodifiableList(text);
        }

        public double getHeight() {
            return height;
        }

        public List<String> getText() {
            return text;
        }
    }

    public record Block(Material material) {
    }

    public record Interaction(OpenMode rightClick, OpenMode shiftRightClick, boolean ownerOffline,
                              boolean preventCreative) {
    }

    public enum OpenMode {
        MENU,
        CHEST,
        NONE,
        CONTAINER
    }

    public record Placement(int limit, boolean delayCheck, PlacementPrevention prevent) {
    }

    public record PlacementPrevention(boolean chestsNearVoidChests, boolean voidChestsNearChests,
                                      boolean voidChestsInSameChunk, boolean voidChestsNearVoidChests) {
    }

    public record Breaking(boolean dropToInventory, boolean storeStatistics, boolean storeUpgrades,
                           boolean storeCharge) {
    }

    public record Explosions(boolean preventDestruction, boolean dropItem) {

    }

    public record Notifications(BreakingNotifications breaking, PlacementNotifications placement) {
    }

    public record BreakingNotifications(boolean failedOther, boolean permissionDenied, boolean successful) {

    }

    public record PlacementNotifications(boolean permissionDenied, boolean successful) {

    }

    public record Item(String name, List<String> lore) {

        public Item(
                String name,
                List<String> lore
        ) {
            this.name = name;
            this.lore = Collections.unmodifiableList(lore);
        }
    }

    public record Inventory(boolean useCustomName, String defaultName, List<String> lore) {

        public Inventory(
                boolean useCustomName,
                String defaultName,
                List<String> lore
        ) {
            this.useCustomName = useCustomName;
            this.defaultName = defaultName;
            this.lore = Collections.unmodifiableList(lore);
        }
    }

    public record LinksInventory(boolean useCustomName, List<String> lore) {

        public LinksInventory(
                boolean useCustomName,
                List<String> lore
        ) {
            this.useCustomName = useCustomName;
            this.lore = Collections.unmodifiableList(lore);
        }
    }

    public record Menus(Inventory inventory, LinksInventory linksInventory, Menu shop, Menu filters) {
    }

    public record Menu(List<String> itemLore) {
        public Menu(List<String> itemLore) {
            this.itemLore = Collections.unmodifiableList(itemLore);
        }
    }

    public record Sounds(String place, String breakSound, UpgradeSounds upgrade) {
    }

    public record UpgradeSounds(String success, String failure) {
    }

    public record Worlds(List<String> disabled) {

        public Worlds(List<String> disabled) {
            this.disabled = Collections.unmodifiableList(disabled);
        }

        public boolean isDisabled(String worldName) {
            return disabled.contains(worldName);
        }
    }
}