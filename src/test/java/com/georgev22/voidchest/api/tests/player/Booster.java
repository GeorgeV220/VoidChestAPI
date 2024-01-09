package com.georgev22.voidchest.api.tests.player;

import com.sun.source.util.Plugin;

import java.time.Instant;

public class Booster {

    private double booster = 1d;
    private long boosterTime = 0;

    private final String pluginName;

    public Booster(String pluginName) {
        this.pluginName = pluginName;
    }

    public Booster(Plugin plugin) {
        this.pluginName = plugin.getName();
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setBooster(double booster) {
        this.booster = booster;
    }

    public void setBoosterTime(long boosterTime) {
        this.boosterTime = boosterTime;
    }

    public double getBooster() {
        long time = Instant.now().getEpochSecond();
        if (this.getBoosterTime() <= time) {
            this.setBoosterTime(0L);
            this.setBooster(1d);
        }
        return this.booster;
    }

    public long getBoosterTime() {
        return this.boosterTime;
    }

}
