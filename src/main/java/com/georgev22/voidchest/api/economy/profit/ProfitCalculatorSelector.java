package com.georgev22.voidchest.api.economy.profit;

import java.util.Map;
import java.util.Comparator;
import java.util.Optional;

public class ProfitCalculatorSelector {

    private final Map<ProfitCalculator, Integer> calculators;

    public ProfitCalculatorSelector(Map<ProfitCalculator, Integer> calculators) {
        this.calculators = calculators;
    }

    public Optional<ProfitCalculator> selectBest() {
        return calculators.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Map<ProfitCalculator, Integer> getEntries() {
        return Map.copyOf(calculators);
    }
}
