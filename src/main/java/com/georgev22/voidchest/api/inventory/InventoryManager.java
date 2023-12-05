package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface InventoryManager {

    @NotNull InventoryHandler inventory(IVoidStorage voidStorage, InventoryType type);

    @NotNull InventoryHandler inventory(UUID voidStorageUUID, InventoryType type);

    enum InventoryType {
        STORAGE,
        WHITELIST,
        BLACKLIST,
        MENU
    }

}
