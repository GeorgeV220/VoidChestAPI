package com.georgev22.voidchest.api.booster;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BoosterProvider {

    private final ObjectMap<UUID, List<Booster>> boosters = new ConcurrentObjectMap<>();
    private final JavaPlugin plugin;

    public BoosterProvider(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public List<Booster> getBoosters(@NotNull OfflinePlayer player) {
        List<Booster> boosterList = boosters.get(player.getUniqueId());
        if (boosterList == null) {
            return Collections.emptyList();
        }

        synchronized (boosterList) {
            boosterList.removeIf(booster -> booster.timeLeft() <= 0);
            return new ArrayList<>(boosterList);
        }
    }

    public void addBooster(@NotNull OfflinePlayer player, Booster booster) {
        boosters.computeIfAbsent(player.getUniqueId(), k -> Collections.synchronizedList(new ArrayList<>()))
                .add(booster);
    }

    public void removeBooster(@NotNull OfflinePlayer player, Booster booster) {
        boosters.computeIfAbsent(player.getUniqueId(), k -> Collections.synchronizedList(new ArrayList<>()))
                .remove(booster);
    }

    public Plugin plugin() {
        return plugin;
    }
}
