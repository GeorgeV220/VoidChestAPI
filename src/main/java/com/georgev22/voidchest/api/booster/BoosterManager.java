package com.georgev22.voidchest.api.booster;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BoosterManager {

    private final Set<BoosterProvider> providers = ConcurrentHashMap.newKeySet();

    public void registerProvider(BoosterProvider provider) {
        if (provider == null) return;
        providers.add(provider);
    }

    public void unregisterProvider(BoosterProvider provider) {
        if (provider == null) return;
        if (!providers.contains(provider)) return;
        providers.remove(provider);
    }

    public Set<BoosterProvider> getProviders() {
        return providers;
    }

    public Optional<BoosterProvider> getProvider(JavaPlugin plugin) {
        for (BoosterProvider provider : providers) {
            if (provider.plugin() == plugin) return Optional.of(provider);
        }
        return Optional.empty();
    }

    /**
     * Calculates total booster by summing all boosters from all providers.
     */
    public BigDecimal getTotalBooster(OfflinePlayer player) {
        BigDecimal total = BigDecimal.ZERO;
        for (BoosterProvider provider : providers) {
            List<Booster> boosters = provider.getBoosters(player);
            if (boosters != null) {
                for (Booster booster : boosters) {
                    if (booster != null && booster.timeLeft() > 0) {
                        total = total.add(booster.amount());
                    }
                }
            }
        }
        return total;
    }

    /**
     * Returns the longest booster time left among all boosters.
     */
    public long getLongestBoosterTimeLeft(OfflinePlayer player) {
        long max = 0;
        for (BoosterProvider provider : providers) {
            List<Booster> boosters = provider.getBoosters(player);
            if (boosters != null) {
                for (Booster booster : boosters) {
                    if (booster != null && booster.timeLeft() > 0) {
                        max = Math.max(max, booster.timeLeft());
                    }
                }
            }
        }
        return max;
    }

    /**
     * Calculates total booster time left by summing all boosters from all providers.
     */
    public long getTotalBoosterTimeLeft(OfflinePlayer offlinePlayer) {
        long total = 0;
        for (BoosterProvider provider : providers) {
            List<Booster> boosters = provider.getBoosters(offlinePlayer);
            if (boosters != null) {
                for (Booster booster : boosters) {
                    if (booster != null && booster.timeLeft() > 0) {
                        total += booster.timeLeft();
                    }
                }
            }
        }
        return total;
    }
}
