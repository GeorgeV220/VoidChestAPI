package com.georgev22.voidchest.api.utilities.persistence;

import java.io.Serializable;

public interface PersistentHolder extends Serializable {

    PersistentHolderType getType();

    String getPersistentId();

    default String getStorageKey() {
        return getType().name() + ":" + getPersistentId();
    }
}