package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Registry for managing {@link ProfitCalculator} instances per VoidChest type and associated plugin.
 * <p>
 * Calculators are sorted by their weight, with lower weight having higher priority.
 */
public class ProfitCalculatorRegistry {

    private static final ObjectMap<String, List<ProfitCalculator>> calculatorMap = new HashObjectMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private ProfitCalculatorRegistry() {
    }

    /**
     * Registers a single {@link ProfitCalculator} for a given VoidChest type.
     * Duplicate calculators per chest are not allowed.
     *
     * @param voidchestType The type of the VoidChest.
     * @param calculator    The calculator to register.
     * @throws IllegalArgumentException if the calculator is already registered.
     */
    public static void registerCalculator(String voidchestType, ProfitCalculator calculator) {
        validateCalculator(voidchestType, calculator);
        getOrCreateCalculatorList(voidchestType).add(calculator);
    }

    /**
     * Registers a list of calculator entries to the given VoidChest type.
     * Duplicate calculators are not allowed.
     *
     * @param voidchestType The type of the VoidChest.
     * @param calculators   The list of calculator entries to register.
     * @throws IllegalArgumentException if any calculator is already registered.
     */
    public static void registerCalculators(String voidchestType, @NotNull List<ProfitCalculator> calculators) {
        List<ProfitCalculator> calculatorEntries = getOrCreateCalculatorList(voidchestType);
        for (ProfitCalculator calculatorEntry : calculators) {
            validateCalculator(voidchestType, calculatorEntry);
            calculatorEntries.add(calculatorEntry);
        }
    }

    /**
     * Unregisters a specific calculator from the given VoidChest type.
     *
     * @param voidchestType The type of the VoidChest.
     * @param calculator    The calculator to remove.
     * @throws IllegalArgumentException if the calculator is not registered.
     */
    public static void unregisterCalculator(String voidchestType, ProfitCalculator calculator) {
        List<ProfitCalculator> entries = calculatorMap.get(voidchestType);
        if (entries == null || entries.stream().noneMatch(e -> e.equals(calculator))) {
            throw new IllegalArgumentException("Calculator " + calculator.getClass().getSimpleName() + " is not registered to " + voidchestType);
        }
        entries.removeIf(e -> e.equals(calculator));
    }

    /**
     * Checks if a calculator is already registered to a specific VoidChest type.
     *
     * @param voidchestType The type of the VoidChest.
     * @param calculator    The calculator to check.
     * @return {@code true} if already registered, otherwise {@code false}.
     */
    public static boolean isCalculatorRegistered(String voidchestType, ProfitCalculator calculator) {
        List<ProfitCalculator> entries = calculatorMap.get(voidchestType);
        return entries != null && entries.stream().anyMatch(e -> e.equals(calculator));
    }

    /**
     * Retrieves all registered {@link ProfitCalculator} instances sorted by weight across all VoidChest types.
     *
     * @return A sorted list of all calculators.
     */
    public static List<ProfitCalculator> getAllCalculators() {
        return calculatorMap.values().stream()
                .flatMap(List::stream)
                .map(e -> e)
                .sorted(Comparator.comparingInt(ProfitCalculator::getWeight))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all calculators registered to the specified VoidChest type, sorted by weight.
     *
     * @param voidchestType The type of the VoidChest.
     * @return A sorted list of calculators.
     */
    public static List<ProfitCalculator> getCalculators(String voidchestType) {
        return getOrCreateCalculatorList(voidchestType).stream()
                .map(e -> e)
                .sorted(Comparator.comparingInt(ProfitCalculator::getWeight))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all calculators registered by the specified plugin across all VoidChest types, sorted by weight.
     *
     * @param plugin The plugin to filter by.
     * @return A sorted list of calculators.
     */
    public static List<ProfitCalculator> getCalculatorsByPlugin(Plugin plugin) {
        return calculatorMap.values().stream()
                .flatMap(List::stream)
                .filter(e -> e.getPlugin().equals(plugin))
                .sorted(Comparator.comparingInt(ProfitCalculator::getWeight))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all calculators registered by a plugin for a specific VoidChest type, sorted by weight.
     *
     * @param voidchestType The type of the VoidChest.
     * @param plugin        The plugin to filter by.
     * @return A sorted list of calculators.
     */
    public static List<ProfitCalculator> getCalculatorsByPlugin(String voidchestType, Plugin plugin) {
        return getOrCreateCalculatorList(voidchestType).stream()
                .filter(e -> e.getPlugin().equals(plugin))
                .sorted(Comparator.comparingInt(ProfitCalculator::getWeight))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all calculator entries for a given plugin across all VoidChest types.
     *
     * @param plugin The plugin to filter by.
     * @return A list of calculator entries.
     */
    public static List<ProfitCalculator> getCalculatorEntriesByPlugin(Plugin plugin) {
        return calculatorMap.values().stream()
                .flatMap(List::stream)
                .filter(e -> e.getPlugin().equals(plugin))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all calculator entries for a specific VoidChest.
     *
     * @param voidchestType The type of the VoidChest.
     * @return A list of calculator entries.
     */
    public static List<ProfitCalculator> getCalculatorEntries(String voidchestType) {
        return new ArrayList<>(getOrCreateCalculatorList(voidchestType));
    }

    /**
     * Retrieves all calculator entries for a plugin and VoidChest type.
     *
     * @param voidchestType The type of the VoidChest.
     * @param plugin        The plugin to filter by.
     * @return A list of calculator entries.
     */
    public static List<ProfitCalculator> getCalculatorEntriesByPlugin(String voidchestType, Plugin plugin) {
        return getOrCreateCalculatorList(voidchestType).stream()
                .filter(e -> e.getPlugin().equals(plugin))
                .collect(Collectors.toList());
    }

    /**
     * Finds the best calculator for the given VoidChest based on weight and profit.
     * <p>
     * Lower weight is prioritized. If multiple calculators share the lowest weight,
     * the one that returns the highest profit will be used.
     *
     * @param voidChest     The VoidChest to calculate profit for.
     * @param input         The input item to calculate profit for.
     * @return The selected calculator, or {@code Optional.empty()} if none apply.
     */
    public static Optional<ProfitCalculator> selectBestCalculator(@NotNull IVoidChest voidChest, ItemStack input, BigInteger itemAmount) {
        List<ProfitCalculator> calculators = getCalculators(voidChest.type());
        if (calculators.isEmpty()) {
            return Optional.empty();
        }

        Map<Integer, List<ProfitCalculator>> grouped = calculators.stream()
                .collect(Collectors.groupingBy(ProfitCalculator::getWeight));

        int lowestWeight = grouped.keySet().stream()
                .min(Integer::compareTo)
                .orElse(0);

        List<ProfitCalculator> candidates = grouped.get(lowestWeight);
        if (candidates == null || candidates.isEmpty()) {
            return Optional.empty();
        }

        return candidates.stream()
                .map(calculator -> Map.entry(calculator, calculator.getProfit(voidChest, input, itemAmount)))
                .filter(entry -> entry.getValue() != null && entry.getValue().compareTo(BigDecimal.ZERO) > 0)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    /**
     * Calculates the best profit for the given VoidChest based on calculator weight and profit value.
     * <p>
     * Lower weight is prioritized. If multiple calculators share the lowest weight,
     * the one that returns the highest profit will be used.
     *
     * @param voidChest   The VoidChest to calculate profit for.
     * @param input       The input item to calculate profit for.
     * @param itemAmount  The number of items being evaluated.
     * @return The best profit, or {@code Optional.empty()} if no calculator applies or yields profit.
     */
    public static Optional<BigDecimal> selectBestProfit(@NotNull IVoidChest voidChest, ItemStack input, BigInteger itemAmount) {
        List<ProfitCalculator> calculators = getCalculators(voidChest.type());
        if (calculators.isEmpty()) {
            return Optional.empty();
        }

        Map<Integer, List<ProfitCalculator>> grouped = calculators.stream()
                .collect(Collectors.groupingBy(ProfitCalculator::getWeight));

        int lowestWeight = grouped.keySet().stream()
                .min(Integer::compareTo)
                .orElse(0);

        List<ProfitCalculator> candidates = grouped.get(lowestWeight);
        if (candidates == null || candidates.isEmpty()) {
            return Optional.empty();
        }

        return candidates.stream()
                .map(calculator -> calculator.getProfit(voidChest, input, itemAmount))
                .filter(profit -> profit != null && profit.compareTo(BigDecimal.ZERO) > 0)
                .max(BigDecimal::compareTo);
    }

    private static List<ProfitCalculator> getOrCreateCalculatorList(String voidchestType) {
        return calculatorMap.computeIfAbsent(voidchestType, k -> new ArrayList<>());
    }

    private static void validateCalculator(String voidchestType, ProfitCalculator calculator) {
        if (isCalculatorRegistered(voidchestType, calculator)) {
            throw new IllegalArgumentException("Calculator " + calculator.getClass().getSimpleName() + " is already registered to " + voidchestType);
        }
    }
}
