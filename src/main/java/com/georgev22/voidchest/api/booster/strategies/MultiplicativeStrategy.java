package com.georgev22.voidchest.api.booster.strategies;

import com.georgev22.voidchest.api.booster.BoosterStackingStrategy;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class MultiplicativeStrategy implements BoosterStackingStrategy {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "multiplicative");

    @Override
    public BigDecimal calculate(@NonNull List<BigDecimal> multipliers) {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal result = ONE;
        for (BigDecimal m : multipliers) {
            result = result.multiply(m, MC);
        }
        return result;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return KEY;
    }
}
