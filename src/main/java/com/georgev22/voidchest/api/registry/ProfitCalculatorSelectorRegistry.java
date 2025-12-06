package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.config.VoidChestOptionsUtil;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculatorSelector;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class ProfitCalculatorSelectorRegistry
        extends AbstractRegistry<String, ProfitCalculatorSelector> {

    private static final ProfitCalculatorSelectorRegistry INSTANCE
            = new ProfitCalculatorSelectorRegistry();

    private final VoidChestAPI voidChestAPI = VoidChestAPI.getInstance();
    private final Logger logger = voidChestAPI.plugin().getLogger();

    private ProfitCalculatorSelectorRegistry() {
    }

    public static ProfitCalculatorSelectorRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void register(@NotNull ProfitCalculatorSelector value) throws IllegalArgumentException {
        throw new IllegalArgumentException("ProfitCalculatorSelectorRegistry does not support register(ProfitCalculatorSelector)");
    }

    @Override
    public boolean replaceOrRegister(@NotNull ProfitCalculatorSelector value) {
        throw new IllegalArgumentException("ProfitCalculatorSelectorRegistry does not support replaceOrRegister(ProfitCalculatorSelector)");
    }

    public void reloadSelectors() {
        ProfitCalculatorRegistry calcRegistry = ProfitCalculatorRegistry.getInstance();

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

                    List<String> entries = VoidChestOptionsUtil.OPTIONS_PROFIT_CALCULATOR
                            .getStringList(cfg);

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
                            key = NamespacedKey.of(namespace, keyName);
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
                        calcRegistry.get(key).ifPresentOrElse(
                                calc -> result.put(calc, finalWeight),
                                () -> {
                                    this.logger.warning("Profit calculator not registered: " + key);
                                    this.logger.info("If this is a custom calculator from another plugin, "
                                            + "ensure that plugin registers it using ProfitCalculatorRegistry "
                                            + "and then reload VoidChest selectors (/voidchestadmin reload)/ProfitCalculatorRegistry#reloadSelectors().");
                                }
                        );
                    }

                    this.replaceOrRegister(chestType, new ProfitCalculatorSelector(result));
                });
    }
}
