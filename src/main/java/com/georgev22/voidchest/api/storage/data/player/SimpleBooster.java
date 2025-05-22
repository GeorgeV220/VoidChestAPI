package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.utilities.Utils;

import java.time.Instant;

public class SimpleBooster implements Booster {

    private final String pluginIdentifier;
    private final ConcurrentObjectMap<String, Object> customData;
    private final String noActiveBooster;
    private final String[] timeFormatKeys;

    public SimpleBooster(String pluginName, String noActiveBooster, String... timeFormatKeys) {
        this.pluginIdentifier = pluginName;
        this.customData = new ConcurrentObjectMap<>();
        this.noActiveBooster = noActiveBooster;
        this.timeFormatKeys = timeFormatKeys;
        this.addCustomDataIfNotExists("booster", 1d);
        this.addCustomDataIfNotExists("boostTime", 0L);
    }

    @Override
    public double booster() {
        long time = Instant.now().toEpochMilli();
        long boostTime = this.boostTime();
        if (boostTime != -1 && boostTime <= time) {
            this.boostTime(0L);
            this.booster(1d);
        }
        return this.getCustomData("booster");
    }

    @Override
    public void booster(double booster) {
        this.addCustomData("booster", booster);
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
        long boostTime = this.getCustomData("boostTime");
        long time = Instant.now().toEpochMilli();
        if (boostTime != -1 && boostTime <= time) {
            this.boostTime(0L);
            this.booster(1d);
        }
        return this.getCustomData("boostTime");
    }

    @Override
    public void boostTime(long boostTime) {
        this.addCustomData("boostTime", boostTime);
    }

    @Override
    public boolean isBoosterActive() {
        return this.booster() > 1d;
    }

    @Override
    public String pluginIdentifier() {
        return this.pluginIdentifier;
    }

    @Override
    public ConcurrentObjectMap<String, Object> getCustomData() {
        return this.customData;
    }
}