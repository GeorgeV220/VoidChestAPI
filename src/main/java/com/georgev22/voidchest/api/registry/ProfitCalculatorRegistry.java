package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton registry for managing ProfitCalculator instances by VoidChest name with priority.
 */
@ApiStatus.Experimental
public class ProfitCalculatorRegistry {

    private static final ObjectMap<String, List<CalculatorEntry>> calculatorMap = new HashObjectMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private ProfitCalculatorRegistry() {
    }

    /**
     * Registers a single ProfitCalculator instance with priority to a VoidChest.
     * If the priority already exists, assigns the next available priority.
     *
     * @param voidChestName The name of the VoidChest.
     * @param plugin        The plugin that is registering the calculator.
     * @param calculator    The ProfitCalculator instance to register.
     * @param priority      The priority of the ProfitCalculator instance.
     * @throws IllegalArgumentException if the calculator is already registered to the VoidChest.
     */
    public static void registerCalculator(String voidChestName, Plugin plugin, ProfitCalculator calculator, int priority) {
        validateCalculator(voidChestName, calculator);
        List<CalculatorEntry> calculatorEntries = getOrCreateCalculatorList(voidChestName);
        if (priorityExists(calculatorEntries, priority)) {
            priority = getNextAvailablePriority(calculatorEntries);
        }
        calculatorEntries.add(new CalculatorEntry(plugin, calculator, priority));
    }

    /**
     * Registers a list of ProfitCalculator instances with priorities.
     * If the priority already exists for an entry, it will shift the existing priorities by 1.
     *
     * @param voidChestName The name of the VoidChest.
     * @param calculators   The list of ProfitCalculator instances to register with their respective priorities.
     */
    public static void registerCalculators(String voidChestName, @NotNull List<CalculatorEntry> calculators) {
        List<CalculatorEntry> calculatorEntries = getOrCreateCalculatorList(voidChestName);
        for (CalculatorEntry calculatorEntry : calculators) {
            validateCalculator(voidChestName, calculatorEntry.calculator);
            if (priorityExists(calculatorEntries, calculatorEntry.priority)) {
                shiftPriorities(calculatorEntries, calculatorEntry.priority);
            }
            calculatorEntries.add(calculatorEntry);
        }
    }

    /**
     * Unregisters a single ProfitCalculator instance from a VoidChest.
     *
     * @param voidChestName The name of the VoidChest.
     * @param calculator    The ProfitCalculator instance to unregister.
     * @throws IllegalArgumentException if the calculator is not registered to the VoidChest.
     */
    public static void unregisterCalculator(String voidChestName, ProfitCalculator calculator) {
        List<CalculatorEntry> calculators = calculatorMap.get(voidChestName);
        if (calculators == null || calculators.stream().noneMatch(entry -> entry.calculator.equals(calculator))) {
            throw new IllegalArgumentException("Calculator " + calculator.getClass().getSimpleName() + " is not registered to " + voidChestName + " VoidChest.");
        }
        calculators.removeIf(entry -> entry.calculator.equals(calculator));
    }

    /**
     * Checks if a calculator is already registered to a specific VoidChest.
     *
     * @param voidChestName The name of the VoidChest.
     * @param calculator    The ProfitCalculator instance to check.
     * @return true if the calculator is registered, false otherwise.
     */
    public static boolean isCalculatorRegistered(String voidChestName, ProfitCalculator calculator) {
        List<CalculatorEntry> calculators = calculatorMap.get(voidChestName);
        return calculators != null && calculators.stream().anyMatch(entry -> entry.calculator.equals(calculator));
    }

    /**
     * Retrieves all registered ProfitCalculator instances sorted by priority.
     *
     * @return A list of all registered ProfitCalculator instances, sorted by priority.
     */
    public static List<ProfitCalculator> getAllCalculators() {
        return calculatorMap.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .map(entry -> entry.calculator)
                .toList();
    }

    /**
     * Retrieves all registered ProfitCalculator instances for a specific VoidChest, sorted by priority.
     *
     * @param voidChestName The name of the VoidChest.
     * @return A list of ProfitCalculator instances registered to the VoidChest, sorted by priority.
     */
    public static List<ProfitCalculator> getCalculators(String voidChestName) {
        return getOrCreateCalculatorList(voidChestName).stream()
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .map(entry -> entry.calculator)
                .toList();
    }

    /**
     * Retrieves all ProfitCalculator instances associated with a specific plugin, sorted by priority.
     *
     * @param plugin The plugin to filter calculators by.
     * @return A list of ProfitCalculator instances associated with the plugin, sorted by priority.
     */
    public static List<ProfitCalculator> getCalculatorsByPlugin(Plugin plugin) {
        return calculatorMap.values().stream()
                .flatMap(List::stream)
                .filter(entry -> entry.plugin.equals(plugin))
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .map(entry -> entry.calculator)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all ProfitCalculator instances for a specific VoidChest and associated with a specific plugin.
     *
     * @param voidChestName The name of the VoidChest.
     * @param plugin        The plugin to filter calculators by.
     * @return A list of ProfitCalculator instances registered to the VoidChest and associated with the plugin.
     */
    public static List<ProfitCalculator> getCalculatorsByPlugin(String voidChestName, Plugin plugin) {
        return getOrCreateCalculatorList(voidChestName).stream()
                .filter(entry -> entry.plugin.equals(plugin))
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .map(entry -> entry.calculator)
                .toList();
    }


    /**
     * Retrieves all CalculatorEntry instances associated with a specific plugin, sorted by priority.
     *
     * @param plugin The plugin to filter entries by.
     * @return A list of CalculatorEntry instances associated with the plugin, sorted by priority.
     */
    public static List<CalculatorEntry> getCalculatorEntriesByPlugin(Plugin plugin) {
        return calculatorMap.values().stream()
                .flatMap(List::stream)
                .filter(entry -> entry.plugin.equals(plugin))
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all CalculatorEntry instances for a specific VoidChest, sorted by priority.
     *
     * @param voidChestName The name of the VoidChest.
     * @return A list of CalculatorEntry instances registered to the VoidChest, sorted by priority.
     */
    public static List<CalculatorEntry> getCalculatorEntries(String voidChestName) {
        return getOrCreateCalculatorList(voidChestName).stream()
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all CalculatorEntry instances for a specific VoidChest and associated with a specific plugin.
     *
     * @param voidChestName The name of the VoidChest.
     * @param plugin        The plugin to filter entries by.
     * @return A list of CalculatorEntry instances registered to the VoidChest and associated with the plugin.
     */
    public static List<CalculatorEntry> getCalculatorEntriesByPlugin(String voidChestName, Plugin plugin) {
        return getOrCreateCalculatorList(voidChestName).stream()
                .filter(entry -> entry.plugin.equals(plugin))
                .sorted(Comparator.comparingInt(entry -> entry.priority))
                .collect(Collectors.toList());
    }

    private static List<CalculatorEntry> getOrCreateCalculatorList(String voidChestName) {
        return calculatorMap.computeIfAbsent(voidChestName, k -> new ArrayList<>());
    }

    private static void validateCalculator(String voidChestName, ProfitCalculator calculator) {
        if (isCalculatorRegistered(voidChestName, calculator)) {
            throw new IllegalArgumentException("Calculator " + calculator.getClass().getSimpleName() + " is already registered to " + voidChestName + " VoidChest.");
        }
    }

    private static boolean priorityExists(@NotNull List<CalculatorEntry> calculatorEntries, int priority) {
        return calculatorEntries.stream().anyMatch(entry -> entry.priority == priority);
    }

    private static int getNextAvailablePriority(@NotNull List<CalculatorEntry> calculatorEntries) {
        return calculatorEntries.stream().max(Comparator.comparingInt(entry -> entry.priority))
                .map(entry -> entry.priority + 1).orElse(0);
    }

    private static void shiftPriorities(@NotNull List<CalculatorEntry> calculatorEntries, int startingPriority) {
        calculatorEntries.stream()
                .filter(entry -> entry.priority >= startingPriority)
                .forEach(entry -> entry.priority += 1);
    }

    /**
     * A helper class to store calculator, its priority, and the associated plugin.
     */
    public static class CalculatorEntry {
        private final Plugin plugin;
        private final ProfitCalculator calculator;
        private int priority;

        /**
         * Constructs a new CalculatorEntry with the given calculator and priority.
         *
         * @param plugin     The plugin that registered the calculator.
         * @param calculator The ProfitCalculator instance associated with the current instance.
         * @param priority   The priority value of the current instance. The interpretation of this value is context-dependent.
         */
        public CalculatorEntry(Plugin plugin, ProfitCalculator calculator, int priority) {
            this.plugin = plugin;
            this.calculator = calculator;
            this.priority = priority;
        }

        public Plugin getPlugin() {
            return this.plugin;
        }

        /**
         * Gets the ProfitCalculator instance associated with the current instance.
         *
         * @return The ProfitCalculator instance associated with the current instance.
         */
        public ProfitCalculator getCalculator() {
            return this.calculator;
        }

        /**
         * Gets the priority of the current instance.
         *
         * @return The priority value of the current instance. The interpretation of this value is context-dependent.
         */
        public int getPriority() {
            return this.priority;
        }

        /**
         * Sets the priority of the current instance.
         * <p>
         * This method is intended for internal use and should not be called directly from external code.
         *
         * @param priority The priority value to be set. The interpretation of this value is context-dependent.
         */
        @ApiStatus.Internal
        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
}
