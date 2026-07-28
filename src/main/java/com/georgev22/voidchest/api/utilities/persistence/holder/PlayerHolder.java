package com.georgev22.voidchest.api.utilities.persistence.holder;

import com.georgev22.voidchest.api.utilities.persistence.PersistentHolder;
import com.georgev22.voidchest.api.utilities.persistence.PersistentHolderType;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record PlayerHolder(UUID uniqueId) implements PersistentHolder {

    public PlayerHolder(@NotNull OfflinePlayer player) {
        this(player.getUniqueId());
    }

    @Override
    public PersistentHolderType getType() {
        return PersistentHolderType.PLAYER;
    }

    @Override
    public String getPersistentId() {
        return uniqueId.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerHolder(UUID id))) {
            return false;
        }

        return uniqueId.equals(id);
    }

    @Override
    public @NotNull String toString() {
        return getStorageKey();
    }
}