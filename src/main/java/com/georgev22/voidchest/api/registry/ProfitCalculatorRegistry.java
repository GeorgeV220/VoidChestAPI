package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public final class ProfitCalculatorRegistry
        extends AbstractRegistry<NamespacedKey, ProfitCalculator> {

    private static final ProfitCalculatorRegistry INSTANCE = new ProfitCalculatorRegistry();

    private ProfitCalculatorRegistry() {
    }

    public static ProfitCalculatorRegistry getInstance() {
        return INSTANCE;
    }

    public void register(@NotNull ProfitCalculator calculator) throws IllegalArgumentException {
        super.register(calculator.getKey(), calculator);
    }

    public boolean replaceOrRegister(@NotNull ProfitCalculator calculator) {
        return super.replaceOrRegister(calculator.getKey(), calculator);
    }
}
