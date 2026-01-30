package com.georgev22.voidchest.api.storage.model.voidchest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents an extra booster applied to a specific VoidChest.
 * <p>
 * Extra boosters are additive and contribute to the total booster value
 * of the chest independently of base boosters and upgrades.
 *
 * @param boosterId the unique identifier of this booster
 * @param booster   the extra booster value applied to the chest
 * @param saveToDb  whether this booster should be persisted to storage.
 *                  If {@code false}, the booster is runtime-only and will
 *                  be lost after a restart
 */
public record VoidChestBooster(
        UUID boosterId,
        BigDecimal booster,
        boolean saveToDb
) {
}

