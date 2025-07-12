package com.georgev22.voidchest.api.booster;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class SimpleBooster implements Booster {

    private final UUID boosterIdentifier;
    private final BigDecimal amount;
    private final long startTime = Instant.now().toEpochMilli();
    private final long timeLeftMS;

    public SimpleBooster(UUID boosterIdentifier, BigDecimal amount, long timeLeftMS) {
        this.boosterIdentifier = boosterIdentifier;
        this.amount = amount;
        this.timeLeftMS = timeLeftMS;
    }

    @Override
    public UUID boosterIdentifier() {
        return boosterIdentifier;
    }

    @Override
    public BigDecimal amount() {
        return amount;
    }

    @Override
    public long timeLeft() {
        return timeLeftMS - (Instant.now().toEpochMilli() - startTime) / 1000;
    }

    public long startTime() {
        return startTime;
    }

}
