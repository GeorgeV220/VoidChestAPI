package com.georgev22.voidchest.api.booster.strategies;

import com.georgev22.voidchest.api.booster.BoosterStackingStrategy;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class LowestStrategy implements BoosterStackingStrategy {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "lowest");

    @Override
    public BigDecimal calculate(@NonNull List<BigDecimal> multipliers) {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal min = multipliers.getFirst();
        for (int i = 1; i < multipliers.size(); i++) {
            BigDecimal m = multipliers.get(i);
            if (m.compareTo(min) < 0) {
                min = m;
            }
        }
        return min;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return KEY;
    }
}
