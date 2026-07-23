package com.georgev22.voidchest.api.booster.strategies;

import com.georgev22.voidchest.api.booster.BoosterStackingStrategy;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class AdditiveStrategy implements BoosterStackingStrategy {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "additive");

    @Override
    public BigDecimal calculate(@NonNull List<BigDecimal> multipliers) {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal bonusSum = BigDecimal.ZERO;
        for (BigDecimal m : multipliers) {
            bonusSum = bonusSum.add(m.subtract(ONE, MC), MC);
        }
        return ONE.add(bonusSum, MC);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return KEY;
    }
}
