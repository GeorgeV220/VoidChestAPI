package com.georgev22.voidchest.api.registry.economy;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.config.VoidChestOptionsUtil;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculatorSelector;
import com.georgev22.voidchest.api.registry.AbstractRegistry;
import com.georgev22.voidchest.api.registry.Registries;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Registry responsible for creating and storing {@link ProfitCalculatorSelector} instances
 * per VoidChest type, based on configuration-defined {@link ProfitCalculator} entries.
 * <p>
 * Unlike most registries, this class does <strong>not allow direct registration</strong> of
 * {@link ProfitCalculatorSelector} objects because selectors are dynamically constructed from
 * registered {@link ProfitCalculator}s and configuration weight rules.
 * <p>
 * To register new calculators, use {@link Registries#PROFIT_CALCULATOR#register(NamespacedKey, ProfitCalculator)}.
 * After that, call {@link #reloadSelectors()} to rebuild selector instances per chest type.
 */
public final class ProfitCalculatorSelectorRegistry
        extends AbstractRegistry<String, ProfitCalculatorSelector> {

    private static final ProfitCalculatorSelectorRegistry INSTANCE
            = new ProfitCalculatorSelectorRegistry();

    private final VoidChestAPI voidChestAPI = VoidChestAPI.getInstance();
    private final Logger logger = voidChestAPI.plugin().getLogger();

    /**
     * Constructs a singleton instance.
     * Private to enforce global access through {@link #getInstance()}.
     */
    private ProfitCalculatorSelectorRegistry() {
    }

    /**
     * Gets the global instance of this registry.
     *
     * @return the singleton instance
     */
    public static ProfitCalculatorSelectorRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Unsupported — Selectors must be registered with an explicit chest-type key.
     * <p>
     * Use:
     * <ul>
     *   <li>{@link #register(String, ProfitCalculatorSelector)}</li>
     *   <li>{@link #replaceOrRegister(String, ProfitCalculatorSelector)}</li>
     * </ul>
     * instead and after that call {@link #reloadSelectors()} to rebuild selector instances per chest type.
     *
     * @throws IllegalArgumentException always thrown to prevent keyless registration
     */
    @Override
    public void register(@NotNull ProfitCalculatorSelector value) throws IllegalArgumentException {
        throw new IllegalArgumentException(
                "ProfitCalculatorSelectorRegistry requires a chest-type key when registering selectors. " +
                        "Use register(String, ProfitCalculatorSelector) instead.");
    }

    /**
     * Unsupported — Selectors must be registered with an explicit chest-type key.
     * <p>
     * Use:
     * <ul>
     *   <li>{@link #register(String, ProfitCalculatorSelector)}</li>
     *   <li>{@link #replaceOrRegister(String, ProfitCalculatorSelector)}</li>
     * </ul>
     * instead and after that call {@link #reloadSelectors()} to rebuild selector instances per chest type.
     *
     * @throws IllegalArgumentException always thrown to prevent keyless registration
     */
    @Override
    public boolean replaceOrRegister(@NotNull ProfitCalculatorSelector value) {
        throw new IllegalArgumentException(
                "ProfitCalculatorSelectorRegistry requires a chest-type key when registering selectors. " +
                        "Use replaceOrRegister(String, ProfitCalculatorSelector) instead.");
    }

    /**
     * Reloads and rebuilds all {@link ProfitCalculatorSelector} instances based on the
     * current configuration for each registered VoidChest type.
     * <p>
     * Configuration entries must follow:
     * <pre>{@code namespace:key[:weight]}</pre>
     *
     * <ul>
     *   <li>namespace:key → resolves to a {@link ProfitCalculator} from the ProfitCalculatorRegistry</li>
     *   <li>weight → optional priority weight (default = 1000)</li>
     * </ul>
     * <p>
     * Results are stored using chest-type identifiers as keys via
     * {@link Registries#PROFIT_CALCULATOR#replaceOrRegister(Object, Object)}.
     *
     * <p>Example configuration entries:</p>
     * <pre>
     *   voidchest:shopguiplus:100
     *   custom_plugin:custom_calculator:101
     *   custom_plugin:custom_calculator2
     * </pre>
     * <p>
     * After loading, selectors can be retrieved using {@link #get(String)}.
     */
    public void reloadSelectors() {

        this.clear();

        this.voidChestAPI.voidChestConfigurationFileCache()
                .getCachedCFGs()
                .forEach((chestType, voidChestConfigurationFile) -> {

                    Map<ProfitCalculator, Integer> result = new HashMap<>();

                    if (voidChestConfigurationFile == null) {
                        this.logger.warning("Missing config for chest type: " + chestType);
                        return;
                    }

                    FileConfiguration cfg = voidChestConfigurationFile.getFileConfiguration();
                    List<String> entries = VoidChestOptionsUtil.OPTIONS_PROFIT_CALCULATOR.getStringList(cfg);

                    for (String entry : entries) {
                        if (entry == null || entry.isBlank()) continue;

                        String[] split = entry.split(":");

                        if (split.length < 2) {
                            this.logger.warning("Invalid calculator entry (expected 'namespace:key[:weight]'): " + entry);
                            continue;
                        }

                        String namespace = split[0];
                        String keyName = split[1];

                        NamespacedKey key;
                        try {
                            key = NamespacedKey.fromString(namespace + ":" + keyName);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            this.logger.warning("Invalid calculator entry (expected 'namespace:key[:weight]'): " + entry);
                            continue;
                        }

                        int weight = 1000;
                        if (split.length >= 3) {
                            try {
                                weight = Integer.parseInt(split[2]);
                            } catch (NumberFormatException e) {
                                this.logger.warning("Invalid weight in entry: " + entry + " — using default 1000");
                            }
                        }

                        int finalWeight = weight;
                        Registries.PROFIT_CALCULATOR.get(key).ifPresentOrElse(
                                calc -> result.put(calc, finalWeight),
                                () -> {
                                    this.logger.warning("Profit calculator not registered: " + key);
                                    this.logger.info("If this is a custom calculator from another plugin, "
                                            + "ensure that plugin registers it using Registries.PROFIT_CALCULATOR "
                                            + "and then reload VoidChest selectors (/voidchestadmin reload) or Registries.PROFIT_CALCULATOR_SELECTOR.reloadSelectors().");
                                }
                        );
                    }

                    this.replaceOrRegister(chestType, new ProfitCalculatorSelector(result));
                });
    }
}
