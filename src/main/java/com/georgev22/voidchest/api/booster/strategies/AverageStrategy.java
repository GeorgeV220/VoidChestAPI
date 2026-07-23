package com.georgev22.voidchest.api.booster.strategies;

import com.georgev22.voidchest.api.booster.BoosterStackingStrategy;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class AverageStrategy implements BoosterStackingStrategy {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "average");

    @Override
    public BigDecimal calculate(@NonNull List<BigDecimal> multipliers) {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal m : multipliers) {
            sum = sum.add(m, MC);
        }
        return sum.divide(BigDecimal.valueOf(multipliers.size()), MC);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return KEY;
    }
}
