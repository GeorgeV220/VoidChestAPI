package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.voidchest.api.utilities.CustomData;
import com.georgev22.voidchest.api.utilities.Utils;

import java.time.Instant;

public class SimpleBooster implements Booster {

    private final String pluginIdentifier;
    private final CustomData customData = new CustomData();
    private final String noActiveBooster;
    private final String[] timeFormatKeys;
    private double booster;
    private long boostTime;

    public SimpleBooster(String pluginName, String noActiveBooster, String... timeFormatKeys) {
        this.pluginIdentifier = pluginName;
        this.noActiveBooster = noActiveBooster;
        this.timeFormatKeys = timeFormatKeys;
        this.booster = 1d;
        this.boostTime = 0L;
    }

    @Override
    public double booster() {
        long time = Instant.now().toEpochMilli();
        long boostTime = this.boostTime();
        if (boostTime != -1 && boostTime <= time) {
            this.boostTime(0L);
            this.booster(1d);
        }
        return this.booster;
    }

    @Override
    public void booster(double booster) {
        this.booster = booster;
    }

    @Override
    public String boosterTimeLeft() {
        double booster = this.booster();
        if (booster <= 1d) {
            return noActiveBooster;
        }
        return Utils.convertSeconds(((this.boostTime() - Instant.now().toEpochMilli()) / 1000) + 1, timeFormatKeys);
    }

    @Override
    public long boostTime() {
        long boostTime = this.boostTime;
        long time = Instant.now().toEpochMilli();
        if (boostTime != -1 && boostTime <= time) {
            this.boostTime(0L);
            this.booster(1d);
        }
        return this.boostTime;
    }

    @Override
    public void boostTime(long boostTime) {
        this.boostTime = boostTime;
    }

    @Override
    public boolean isBoosterActive() {
        return this.booster() > 1d;
    }

    @Override
    public String pluginIdentifier() {
        return this.pluginIdentifier;
    }

    public CustomData customData() {
        return this.customData;
    }
}