package com.georgev22.voidchest.api.handler;

import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface InventoryHandler {

    @NotNull VoidInventory inventory(IVoidStorage voidStorage, InventoryType type);

    @NotNull VoidInventory inventory(UUID voidStorageUUID, InventoryType type);

    enum InventoryType {
        STORAGE,
        WHITELIST,
        BLACKLIST,
        MENU
    }

}
