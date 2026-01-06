package com.georgev22.voidchest.api.registry;

import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages all internal and external registries used by the VoidChest plugin.
 * <p>
 * Provides lifecycle methods for initialization and reloading of registry
 * integrations, along with utilities for reporting registry status.
 */
public interface IRegistryManager {

    /**
     * Register all registry integrations.
     * <p>
     * This should be invoked once during plugin startup to initialize
     * all available registry providers.
     */
    void load();

    /**
     * Reload and re-register all integrations.
     * <p>
     * Intended for use during configuration reloads or when plugin
     * dependencies change dynamically at runtime.
     */
    void reload();

    /**
     * Retrieves a collection of registry metrics in a categorized form.
     * <p>
     * Each entry in the map represents a registry category and a list of
     * registered identifiers formatted as {@code NamespacedKey.toString()}.
     *
     * @return a map where the key is the registry category name and the value
     * is a list of registered entry names under that category.
     */
    Map<String, List<String>> getRegistryMetrics();

    /**
     * Formats registry metrics into a human-readable multi-line structure.
     * <p>
     * Example output format:
     * <pre>
     * Economies:
     *  - vault:economy
     *  - plugin:example
     * Special Items:
     *  - myplugin:item
     * </pre>
     *
     * @param metrics the metrics retrieved from {@link #getRegistryMetrics()}
     * @return a list of formatted lines representing categorized registry data
     */
    static @NonNull @Unmodifiable List<String> formatRegistryMetrics(@NonNull Map<String, List<String>> metrics) {
        return metrics.entrySet().stream()
                .map(entry -> {
                    String title = entry.getKey();
                    List<String> values = entry.getValue();

                    String joinedValues = values.stream()
                            .map(v -> " - " + v)
                            .collect(Collectors.joining("\n"));

                    return title + ":\n" + (values.isEmpty() ? " - None" : joinedValues);
                })
                .toList();
    }

    /**
     * Converts formatted registry metrics into a string array.
     * <p>
     * This is useful when working with APIs that require string arrays,
     * such as chat message broadcasting or UI list components.
     *
     * @param metrics the metrics retrieved from {@link #getRegistryMetrics()}
     * @return a formatted string array representing the registry data
     * @see #formatRegistryMetrics(Map)
     */
    static String[] formatRegistryMetricsArray(Map<String, List<String>> metrics) {
        return formatRegistryMetrics(metrics).toArray(String[]::new);
    }
}
