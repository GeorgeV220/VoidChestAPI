package com.georgev22.voidchest.api.storage;


import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.model.Entity;

@FunctionalInterface
public interface EntityFactory<E extends Entity> {
    E create(ObjectMap<String, Object> data);
}
