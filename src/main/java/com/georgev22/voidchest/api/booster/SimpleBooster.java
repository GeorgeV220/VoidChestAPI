package com.georgev22.voidchest.api.booster;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class SimpleBooster implements Booster {

    private final UUID boosterIdentifier;
    private final BigDecimal amount;
    private final long durationMS;
    private final long startTime = Instant.now().toEpochMilli();

    public SimpleBooster(UUID boosterIdentifier, BigDecimal amount, long durationMS) {
        this.boosterIdentifier = boosterIdentifier;
        this.amount = amount;
        this.durationMS = durationMS;
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
        long elapsed = Instant.now().toEpochMilli() - startTime;
        long timeLeft = durationMS - elapsed;
        return timeLeft > 0 ? timeLeft / 1000 : 0;
    }

    public long startTime() {
        return startTime;
    }

}
