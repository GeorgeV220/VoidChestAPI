package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.integrations.economy.profit.ProfitCalculatorSelector;
import org.bukkit.Material;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.georgev22.voidchest.api.config.voidchests.VoidChestConfigPath.of;

public final class VoidChestConfigPaths {

    private VoidChestConfigPaths() {
    }

    // AutoSell

    public static final VoidChestConfigPath<Boolean> AUTOSELL_ENABLED =
            booleanPath("autosell.enabled");

    public static final VoidChestConfigPath<Boolean> AUTOSELL_START_VALUE =
            booleanPath("autosell.start-value");

    public static final VoidChestConfigPath<Long> AUTOSELL_INTERVAL =
            longPath("autosell.interval");

    public static final VoidChestConfigPath<Double> AUTOSELL_BOOSTER =
            doublePath("autosell.booster");

    public static final VoidChestConfigPath<List<String>> AUTOSELL_PROFIT_CALCULATORS =
            stringListPath("autosell.profit-calculators");

    public static final VoidChestConfigPath<ProfitCalculatorSelector.SelectorType> AUTOSELL_SELECTOR_TYPE =
            enumPath(
                    "autosell.selector-type",
                    ProfitCalculatorSelector.SelectorType.class
            );


    // Purge

    public static final VoidChestConfigPath<Boolean> PURGE_ENABLED =
            booleanPath("purge.enabled");

    public static final VoidChestConfigPath<Boolean> PURGE_START_VALUE =
            booleanPath("purge.start-value");


    // Bank

    public static final VoidChestConfigPath<Boolean> BANK_ENABLED =
            booleanPath("bank.enabled");

    public static final VoidChestConfigPath<Boolean> BANK_START_VALUE =
            booleanPath("bank.start-value");


    // Chunk Collector

    public static final VoidChestConfigPath<Boolean> CHUNK_COLLECTOR_ENABLED =
            booleanPath("chunk-collector.enabled");

    public static final VoidChestConfigPath<Boolean> CHUNK_COLLECTOR_START_VALUE =
            booleanPath("chunk-collector.start-value");

    public static final VoidChestConfigPath<VoidChestTypeConfig.CollectorMode> CHUNK_COLLECTOR_MODE =
            enumPath(
                    "chunk-collector.mode",
                    VoidChestTypeConfig.CollectorMode.class
            );

    public static final VoidChestConfigPath<Boolean> CHUNK_COLLECTOR_TRANSFER_NON_SELLABLES_ENABLED =
            booleanPath(
                    "chunk-collector.transfer-non-sellables.enabled"
            );

    public static final VoidChestConfigPath<Boolean> CHUNK_COLLECTOR_TRANSFER_NON_SELLABLES_START_VALUE =
            booleanPath(
                    "chunk-collector.transfer-non-sellables.start-value"
            );

    public static final VoidChestConfigPath<Boolean> CHUNK_COLLECTOR_FILTERS_ENABLED =
            booleanPath(
                    "chunk-collector.filters.enabled"
            );


    // Links

    public static final VoidChestConfigPath<Boolean> LINKS_ENABLED =
            booleanPath("links.enabled");

    public static final VoidChestConfigPath<Integer> LINKS_LIMIT =
            integerPath("links.limit");


    // Charge

    public static final VoidChestConfigPath<Boolean> CHARGE_ENABLED =
            booleanPath("charge.enabled");

    public static final VoidChestConfigPath<Boolean> CHARGE_START_VALUE =
            booleanPath("charge.start-value");

    public static final VoidChestConfigPath<Long> CHARGE_MAX_TIME =
            longPath("charge.max-time");

    public static final VoidChestConfigPath<Long> CHARGE_RENEWAL_TIME =
            longPath("charge.renewal-time");

    public static final VoidChestConfigPath<BigDecimal> CHARGE_PRICE =
            bigDecimalPath("charge.price");

    public static final VoidChestConfigPath<String> CHARGE_PLACEHOLDERS_HOLOGRAM_NO_FUEL =
            stringPath("charge.placeholders.hologram.no-fuel");

    public static final VoidChestConfigPath<String> CHARGE_PLACEHOLDERS_ITEM_NO_FUEL =
            stringPath("charge.placeholders.item.no-fuel");


    // Hologram

    public static final VoidChestConfigPath<Boolean> HOLOGRAM_ENABLED =
            booleanPath("hologram.enabled");

    public static final VoidChestConfigPath<Boolean> HOLOGRAM_START_VALUE =
            booleanPath("hologram.start-value");

    public static final VoidChestConfigPath<Double> HOLOGRAM_HEIGHT =
            doublePath("hologram.height");

    public static final VoidChestConfigPath<List<String>> HOLOGRAM_TEXT =
            stringListPath("hologram.text");


    // Block

    public static final VoidChestConfigPath<Material> BLOCK_MATERIAL =
            of(
                    "block.material",
                    value -> {
                        Material material = Material.matchMaterial(
                                String.valueOf(value)
                        );

                        if (material == null) {
                            throw new IllegalArgumentException(
                                    "Invalid material: " + value
                            );
                        }

                        return material;
                    },
                    Material::name
            );


    // Interaction

    public static final VoidChestConfigPath<VoidChestTypeConfig.OpenMode> INTERACTION_RIGHT_CLICK =
            enumPath(
                    "interaction.right-click",
                    VoidChestTypeConfig.OpenMode.class
            );

    public static final VoidChestConfigPath<VoidChestTypeConfig.OpenMode> INTERACTION_SHIFT_RIGHT_CLICK =
            enumPath(
                    "interaction.shift-right-click",
                    VoidChestTypeConfig.OpenMode.class
            );

    public static final VoidChestConfigPath<Boolean> INTERACTION_OWNER_OFFLINE =
            booleanPath("interaction.owner-offline");

    public static final VoidChestConfigPath<Boolean> INTERACTION_PREVENT_CREATIVE =
            booleanPath("interaction.prevent-creative");


    // Placement

    public static final VoidChestConfigPath<Integer> PLACEMENT_LIMIT =
            integerPath("placement.limit");

    public static final VoidChestConfigPath<Boolean> PLACEMENT_DELAY_CHECK =
            booleanPath("placement.delay-check");

    public static final VoidChestConfigPath<Boolean> PLACEMENT_PREVENT_CHESTS_NEAR_VOIDCHESTS =
            booleanPath(
                    "placement.prevent.chests-near-voidchests"
            );

    public static final VoidChestConfigPath<Boolean> PLACEMENT_PREVENT_VOIDCHESTS_NEAR_CHESTS =
            booleanPath(
                    "placement.prevent.voidchests-near-chests"
            );

    public static final VoidChestConfigPath<Boolean> PLACEMENT_PREVENT_VOIDCHESTS_IN_SAME_CHUNK =
            booleanPath(
                    "placement.prevent.voidchests-in-same-chunk"
            );

    public static final VoidChestConfigPath<Boolean> PLACEMENT_PREVENT_VOIDCHESTS_NEAR_VOIDCHESTS =
            booleanPath(
                    "placement.prevent.voidchests-near-voidchests"
            );


    // Breaking

    public static final VoidChestConfigPath<Boolean> BREAKING_DROP_TO_INVENTORY =
            booleanPath("breaking.drop-to-inventory");

    public static final VoidChestConfigPath<Boolean> BREAKING_STORE_STATISTICS =
            booleanPath("breaking.store-statistics");

    public static final VoidChestConfigPath<Boolean> BREAKING_STORE_UPGRADES =
            booleanPath("breaking.store-upgrades");

    public static final VoidChestConfigPath<Boolean> BREAKING_STORE_CHARGE =
            booleanPath("breaking.store-charge");


    // Explosions

    public static final VoidChestConfigPath<Boolean> EXPLOSIONS_PREVENT_DESTRUCTION =
            booleanPath("explosions.prevent-destruction");

    public static final VoidChestConfigPath<Boolean> EXPLOSIONS_DROP_ITEM =
            booleanPath("explosions.drop-item");


    // Notifications

    public static final VoidChestConfigPath<Boolean> NOTIFICATIONS_BREAKING_FAILED_OTHER =
            booleanPath(
                    "notifications.breaking.failed-other"
            );

    public static final VoidChestConfigPath<Boolean> NOTIFICATIONS_BREAKING_PERMISSION_DENIED =
            booleanPath(
                    "notifications.breaking.permission-denied"
            );

    public static final VoidChestConfigPath<Boolean> NOTIFICATIONS_BREAKING_SUCCESSFUL =
            booleanPath(
                    "notifications.breaking.successful"
            );

    public static final VoidChestConfigPath<Boolean> NOTIFICATIONS_PLACEMENT_PERMISSION_DENIED =
            booleanPath(
                    "notifications.placement.permission-denied"
            );

    public static final VoidChestConfigPath<Boolean> NOTIFICATIONS_PLACEMENT_SUCCESSFUL =
            booleanPath(
                    "notifications.placement.successful"
            );


    // Item

    public static final VoidChestConfigPath<String> ITEM_NAME =
            stringPath("item.name");

    public static final VoidChestConfigPath<List<String>> ITEM_LORE =
            stringListPath("item.lore");

    // Menus


    public static final VoidChestConfigPath<Boolean> MENUS_VOIDCHEST_USE_CUSTOM_NAME =
            booleanPath(
                    "menus.voidchest.use-custom-name"
            );

    public static final VoidChestConfigPath<String> MENUS_VOIDCHEST_DEFAULT_NAME =
            stringPath(
                    "menus.voidchest.default-name"
            );

    public static final VoidChestConfigPath<List<String>> MENUS_VOIDCHEST_ITEM_LORE =
            stringListPath(
                    "menus.voidchest.item-lore"
            );

    public static final VoidChestConfigPath<Boolean> MENUS_LINKS_USE_CUSTOM_NAME =
            booleanPath(
                    "menus.links.use-custom-name"
            );

    public static final VoidChestConfigPath<List<String>> MENUS_LINKS_ITEM_LORE =
            stringListPath(
                    "menus.links.item-lore"
            );

    public static final VoidChestConfigPath<List<String>> MENUS_SHOP_ITEM_LORE =
            stringListPath(
                    "menus.shop.item-lore"
            );

    public static final VoidChestConfigPath<List<String>> MENUS_FILTERS_ITEM_LORE =
            stringListPath(
                    "menus.filters.item-lore"
            );


    // Sounds

    public static final VoidChestConfigPath<String> SOUNDS_PLACE =
            stringPath("sounds.place");

    public static final VoidChestConfigPath<String> SOUNDS_BREAK =
            stringPath("sounds.break");

    public static final VoidChestConfigPath<String> SOUNDS_UPGRADE_SUCCESS =
            stringPath("sounds.upgrade.success");

    public static final VoidChestConfigPath<String> SOUNDS_UPGRADE_FAILURE =
            stringPath("sounds.upgrade.failure");


    // Worlds

    public static final VoidChestConfigPath<List<String>> WORLDS_DISABLED =
            stringListPath("worlds.disabled");


    /*
     * Path factories
     */

    private static @NonNull VoidChestConfigPath<String> stringPath(
            @NonNull String path
    ) {
        return of(
                path,
                String::valueOf,
                value -> value
        );
    }

    private static @NonNull VoidChestConfigPath<Boolean> booleanPath(
            @NonNull String path
    ) {
        return of(
                path,
                value -> {
                    if (value instanceof Boolean booleanValue) {
                        return booleanValue;
                    }

                    return Boolean.parseBoolean(
                            String.valueOf(value)
                    );
                },
                value -> value
        );
    }

    private static @NonNull VoidChestConfigPath<Integer> integerPath(
            @NonNull String path
    ) {
        return of(
                path,
                value -> {
                    if (value instanceof Number number) {
                        return number.intValue();
                    }

                    return Integer.parseInt(
                            String.valueOf(value)
                    );
                },
                value -> value
        );
    }

    private static @NonNull VoidChestConfigPath<Long> longPath(
            @NonNull String path
    ) {
        return of(
                path,
                value -> {
                    if (value instanceof Number number) {
                        return number.longValue();
                    }

                    return Long.parseLong(
                            String.valueOf(value)
                    );
                },
                value -> value
        );
    }

    private static @NonNull VoidChestConfigPath<Double> doublePath(
            @NonNull String path
    ) {
        return of(
                path,
                value -> {
                    if (value instanceof Number number) {
                        return number.doubleValue();
                    }

                    return Double.parseDouble(
                            String.valueOf(value)
                    );
                },
                value -> value
        );
    }

    private static @NonNull VoidChestConfigPath<BigDecimal> bigDecimalPath(
            @NonNull String path
    ) {
        return of(
                path,
                value -> {
                    if (value instanceof BigDecimal decimal) {
                        return decimal;
                    }

                    return new BigDecimal(
                            String.valueOf(value)
                    );
                },
                BigDecimal::toPlainString
        );
    }

    private static @NonNull VoidChestConfigPath<List<String>> stringListPath(
            @NonNull String path
    ) {
        return of(
                path,
                value -> {
                    if (!(value instanceof List<?> list)) {
                        throw new IllegalArgumentException(
                                "Expected list at '" + path + "', got: "
                                        + (value == null
                                        ? "null"
                                        : value.getClass().getSimpleName())
                        );
                    }

                    List<String> result =
                            new ArrayList<>(list.size());

                    for (Object element : list) {
                        result.add(String.valueOf(element));
                    }

                    return result;
                },
                ArrayList::new
        );
    }

    private static <E extends Enum<E>>
    @NonNull VoidChestConfigPath<E> enumPath(
            @NonNull String path,
            @NonNull Class<E> enumType
    ) {
        return of(
                path,
                value -> {
                    String name = normalizeEnum(
                            String.valueOf(value)
                    );

                    try {
                        return Enum.valueOf(
                                enumType,
                                name
                        );
                    } catch (IllegalArgumentException exception) {
                        throw new IllegalArgumentException(
                                "Invalid "
                                        + enumType.getSimpleName()
                                        + " value at '"
                                        + path
                                        + "': "
                                        + value,
                                exception
                        );
                    }
                },
                Enum::name
        );
    }

    private static @NonNull String normalizeEnum(
            @NonNull String value
    ) {
        return value
                .toUpperCase(Locale.ROOT)
                .replace('-', '_')
                .replace(' ', '_');
    }
}