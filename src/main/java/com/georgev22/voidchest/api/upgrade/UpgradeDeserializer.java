package com.georgev22.voidchest.api.upgrade;

@FunctionalInterface
public interface UpgradeDeserializer<U> {

    Upgrade<U> deserialize(String serializedUpgrade);

}
