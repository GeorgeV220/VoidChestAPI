package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.utilities.DeepCloner;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static com.georgev22.voidchest.api.config.voidchests.VoidChestConfigPaths.*;

public final class VoidChestTypeConfigLoader {

    private static YamlConfiguration yamlConfiguration;

    private VoidChestTypeConfigLoader() {
    }

    public static @NonNull Map<String, VoidChestTypeConfig> load(File file) {
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection defaultsSection = yamlConfiguration.getConfigurationSection("defaults");
        if (defaultsSection == null) {
            throw new IllegalStateException(
                    "Missing required 'defaults' section in " + file.getName()
            );
        }

        Map<String, Object> defaults = toMap(defaultsSection);

        ConfigurationSection typesSection = yamlConfiguration.getConfigurationSection("types");
        if (typesSection == null) {
            throw new IllegalStateException(
                    "Missing required 'types' section in " + file.getName()
            );
        }

        Map<String, VoidChestTypeConfig> result = new HashMap<>();

        for (String typeName : typesSection.getKeys(false)) {
            ConfigurationSection typeSection =
                    typesSection.getConfigurationSection(typeName);

            if (typeSection == null) {
                continue;
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> resolved = (Map<String, Object>) DeepCloner.cloneValue(defaults);

            ConfigurationSection overridesSection =
                    typeSection.getConfigurationSection("overrides");

            if (overridesSection != null) {
                deepMerge(resolved, toMap(overridesSection));
            }

            result.put(
                    normalizeTypeName(typeName),
                    parse(resolved)
            );
        }

        return result;
    }

    public static YamlConfiguration getYamlConfiguration() throws IllegalAccessException {
        if (yamlConfiguration == null) {
            throw new IllegalAccessException("VoidChest types have not been loaded");
        }
        return yamlConfiguration;
    }

    @Contract("_ -> new")
    private static @NonNull VoidChestTypeConfig parse(Map<String, Object> root) {
        Section section = new Section(root);

        // AutoSell

        VoidChestTypeConfig.AutoSell autoSell =
                new VoidChestTypeConfig.AutoSell(
                        section.get(AUTOSELL_ENABLED),
                        section.get(AUTOSELL_START_VALUE),
                        section.get(AUTOSELL_INTERVAL),
                        section.get(AUTOSELL_BOOSTER),
                        section.get(AUTOSELL_PROFIT_CALCULATORS),
                        section.get(AUTOSELL_SELECTOR_TYPE)
                );

        // Purge

        VoidChestTypeConfig.ToggleFeature purge =
                new VoidChestTypeConfig.ToggleFeature(
                        section.get(PURGE_ENABLED),
                        section.get(PURGE_START_VALUE)
                );

        // Bank

        VoidChestTypeConfig.ToggleFeature bank =
                new VoidChestTypeConfig.ToggleFeature(
                        section.get(BANK_ENABLED),
                        section.get(BANK_START_VALUE)
                );

        // Chunk collector

        VoidChestTypeConfig.ToggleFeature transferNonSellables =
                new VoidChestTypeConfig.ToggleFeature(
                        section.get(
                                CHUNK_COLLECTOR_TRANSFER_NON_SELLABLES_ENABLED
                        ),
                        section.get(
                                CHUNK_COLLECTOR_TRANSFER_NON_SELLABLES_START_VALUE
                        )
                );

        VoidChestTypeConfig.Feature filters =
                new VoidChestTypeConfig.Feature(
                        section.get(CHUNK_COLLECTOR_FILTERS_ENABLED)
                );

        VoidChestTypeConfig.ChunkCollector chunkCollector =
                new VoidChestTypeConfig.ChunkCollector(
                        section.get(CHUNK_COLLECTOR_ENABLED),
                        section.get(CHUNK_COLLECTOR_START_VALUE),
                        section.get(CHUNK_COLLECTOR_MODE),
                        transferNonSellables,
                        filters
                );

        // Links

        VoidChestTypeConfig.Links links =
                new VoidChestTypeConfig.Links(
                        section.get(LINKS_ENABLED),
                        section.get(LINKS_LIMIT)
                );

        // Charge

        VoidChestTypeConfig.ChargePlaceholders chargePlaceholders =
                new VoidChestTypeConfig.ChargePlaceholders(
                        section.get(
                                CHARGE_PLACEHOLDERS_HOLOGRAM_NO_FUEL
                        ),
                        section.get(
                                CHARGE_PLACEHOLDERS_ITEM_NO_FUEL
                        )
                );

        VoidChestTypeConfig.Charge charge =
                new VoidChestTypeConfig.Charge(
                        section.get(CHARGE_ENABLED),
                        section.get(CHARGE_START_VALUE),
                        section.get(CHARGE_MAX_TIME),
                        section.get(CHARGE_RENEWAL_TIME),
                        section.get(CHARGE_PRICE),
                        chargePlaceholders
                );

        // Hologram

        VoidChestTypeConfig.Hologram hologram =
                new VoidChestTypeConfig.Hologram(
                        section.get(HOLOGRAM_ENABLED),
                        section.get(HOLOGRAM_START_VALUE),
                        section.get(HOLOGRAM_HEIGHT),
                        section.get(HOLOGRAM_TEXT)
                );

        // Block

        Material material = section.get(BLOCK_MATERIAL);

        VoidChestTypeConfig.Block block =
                new VoidChestTypeConfig.Block(material);

        // Interaction

        VoidChestTypeConfig.Interaction interaction =
                new VoidChestTypeConfig.Interaction(
                        section.get(INTERACTION_RIGHT_CLICK),
                        section.get(INTERACTION_SHIFT_RIGHT_CLICK),
                        section.get(INTERACTION_OWNER_OFFLINE),
                        section.get(INTERACTION_PREVENT_CREATIVE)
                );

        // Placement

        VoidChestTypeConfig.PlacementPrevention prevention =
                new VoidChestTypeConfig.PlacementPrevention(
                        section.get(PLACEMENT_PREVENT_CHESTS_NEAR_VOIDCHESTS),
                        section.get(PLACEMENT_PREVENT_VOIDCHESTS_NEAR_CHESTS),
                        section.get(PLACEMENT_PREVENT_VOIDCHESTS_IN_SAME_CHUNK),
                        section.get(PLACEMENT_PREVENT_VOIDCHESTS_NEAR_VOIDCHESTS)
                );

        VoidChestTypeConfig.Placement placement =
                new VoidChestTypeConfig.Placement(
                        section.get(PLACEMENT_LIMIT),
                        section.get(PLACEMENT_DELAY_CHECK),
                        prevention
                );

        // Breaking

        VoidChestTypeConfig.Breaking breaking =
                new VoidChestTypeConfig.Breaking(
                        section.get(BREAKING_DROP_TO_INVENTORY),
                        section.get(BREAKING_STORE_STATISTICS),
                        section.get(BREAKING_STORE_UPGRADES),
                        section.get(BREAKING_STORE_CHARGE)
                );

        // Explosions

        VoidChestTypeConfig.Explosions explosions =
                new VoidChestTypeConfig.Explosions(
                        section.get(EXPLOSIONS_PREVENT_DESTRUCTION),
                        section.get(EXPLOSIONS_DROP_ITEM)
                );

        // Notifications

        VoidChestTypeConfig.BreakingNotifications breakingNotifications =
                new VoidChestTypeConfig.BreakingNotifications(
                        section.get(NOTIFICATIONS_BREAKING_FAILED_OTHER),
                        section.get(NOTIFICATIONS_BREAKING_PERMISSION_DENIED),
                        section.get(NOTIFICATIONS_BREAKING_SUCCESSFUL)
                );

        VoidChestTypeConfig.PlacementNotifications placementNotifications =
                new VoidChestTypeConfig.PlacementNotifications(
                        section.get(NOTIFICATIONS_PLACEMENT_PERMISSION_DENIED),
                        section.get(NOTIFICATIONS_PLACEMENT_SUCCESSFUL)
                );

        VoidChestTypeConfig.Notifications notifications =
                new VoidChestTypeConfig.Notifications(
                        breakingNotifications,
                        placementNotifications
                );

        // Item

        VoidChestTypeConfig.Item item =
                new VoidChestTypeConfig.Item(
                        section.get(ITEM_NAME),
                        section.get(ITEM_LORE)
                );

        // Menus

        VoidChestTypeConfig.Inventory inventory =
                new VoidChestTypeConfig.Inventory(
                        section.get(MENUS_VOIDCHEST_USE_CUSTOM_NAME),
                        section.get(MENUS_VOIDCHEST_DEFAULT_NAME),
                        section.get(MENUS_VOIDCHEST_ITEM_LORE)
                );

        VoidChestTypeConfig.LinksInventory linksInventory =
                new VoidChestTypeConfig.LinksInventory(
                        section.get(MENUS_LINKS_USE_CUSTOM_NAME),
                        section.get(MENUS_LINKS_ITEM_LORE)
                );
        VoidChestTypeConfig.Menu shopMenu =
                new VoidChestTypeConfig.Menu(
                        section.get(MENUS_SHOP_ITEM_LORE)
                );

        VoidChestTypeConfig.Menu filterMenu =
                new VoidChestTypeConfig.Menu(
                        section.get(MENUS_FILTERS_ITEM_LORE)
                );

        VoidChestTypeConfig.Menus menus =
                new VoidChestTypeConfig.Menus(
                        inventory,
                        linksInventory,
                        shopMenu,
                        filterMenu
                );

        // Sounds

        VoidChestTypeConfig.UpgradeSounds upgradeSounds =
                new VoidChestTypeConfig.UpgradeSounds(
                        section.get(SOUNDS_UPGRADE_SUCCESS),
                        section.get(SOUNDS_UPGRADE_FAILURE)
                );

        VoidChestTypeConfig.Sounds sounds =
                new VoidChestTypeConfig.Sounds(
                        section.get(SOUNDS_PLACE),
                        section.get(SOUNDS_BREAK),
                        upgradeSounds
                );

        // Worlds

        VoidChestTypeConfig.Worlds worlds =
                new VoidChestTypeConfig.Worlds(
                        section.get(WORLDS_DISABLED)
                );

        return new VoidChestTypeConfig(
                autoSell,
                purge,
                bank,
                chunkCollector,
                links,
                charge,
                hologram,
                block,
                interaction,
                placement,
                breaking,
                explosions,
                notifications,
                item,
                menus,
                sounds,
                worlds
        );
    }

    @SuppressWarnings("unchecked")
    private static void deepMerge(
            Map<String, Object> target,
            @NonNull Map<String, Object> overrides
    ) {
        for (Map.Entry<String, Object> entry : overrides.entrySet()) {
            String key = entry.getKey();
            Object overrideValue = entry.getValue();

            Object existingValue = target.get(key);

            if (existingValue instanceof Map
                    && overrideValue instanceof Map) {

                deepMerge(
                        (Map<String, Object>) existingValue,
                        (Map<String, Object>) overrideValue
                );

                continue;
            }

            target.put(
                    key,
                    DeepCloner.cloneValue(overrideValue)
            );
        }
    }

    private static @NonNull Map<String, Object> toMap(
            @NonNull ConfigurationSection section
    ) {
        Map<String, Object> result = new LinkedHashMap<>();

        for (String key : section.getKeys(false)) {
            Object value = section.get(key);

            if (value instanceof ConfigurationSection) {
                result.put(
                        key,
                        toMap((ConfigurationSection) value)
                );
            } else {
                result.put(
                        key,
                        DeepCloner.cloneValue(value)
                );
            }
        }

        return result;
    }

    @Contract(pure = true)
    private static @NonNull String normalizeTypeName(@NonNull String name) {
        return name.toLowerCase(Locale.ROOT);
    }

    private record Section(Map<String, Object> root) {

        private <T> T get(
                @NonNull VoidChestConfigPath<T> path
        ) {
            String[] parts = path.getPath().split("\\.");

            Object current = root;

            for (String part : parts) {
                if (!(current instanceof Map)) {
                    throw missing(path);
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> map =
                        (Map<String, Object>) current;

                if (!map.containsKey(part)) {
                    throw missing(path);
                }

                current = map.get(part);
            }

            return path.deserialize(current);
        }

        private @NonNull IllegalArgumentException missing(
                @NonNull VoidChestConfigPath<?> path
        ) {
            return new IllegalArgumentException(
                    "Missing configuration value: "
                            + path.getPath()
            );
        }
    }
}