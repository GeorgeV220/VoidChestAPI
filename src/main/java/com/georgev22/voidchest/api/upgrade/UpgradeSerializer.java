package com.georgev22.voidchest.api.upgrade;

@FunctionalInterface
public interface UpgradeSerializer<U> {

    String serialize(Upgrade<U> upgrade);

}
