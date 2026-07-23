package com.georgev22.voidchest.api.booster.strategies;

import com.georgev22.voidchest.api.booster.BoosterStackingStrategy;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class HighestStrategy implements BoosterStackingStrategy {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "highest");

    @Override
    public BigDecimal calculate(@NonNull List<BigDecimal> multipliers) {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal max = multipliers.getFirst();
        for (int i = 1; i < multipliers.size(); i++) {
            BigDecimal m = multipliers.get(i);
            if (m.compareTo(max) > 0) {
                max = m;
            }
        }
        return max;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return KEY;
    }
}
