package com.georgev22.voidchest.api.tests.objects;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.storage.data.Entity;
import com.georgev22.voidchest.api.tests.Main;
import com.georgev22.voidchest.api.tests.events.SimpleObjectCreationEvent;
import com.georgev22.voidchest.api.tests.events.SimpleObjectModifyEvent;

import java.util.UUID;

public class SimpleObject implements Entity {

    private final ConcurrentObjectMap<String, Object> data = new ConcurrentObjectMap<>();

    private final boolean async;

    public SimpleObject(UUID id, boolean async) {
        SimpleObjectCreationEvent event = new SimpleObjectCreationEvent(this, async);
        Main.getInstance().getEventManager().callEvent(event);
        this.data.append("id", id);
        this.data.append("async", async);
        this.async = async;
    }

    @Override
    public UUID getId() {
        return this.getCustomData("id");
    }

    public Entity addCustomData(String key, Object value) {
        SimpleObjectModifyEvent event = new SimpleObjectModifyEvent(this, this.data,
                this.data.append(key, value), async);
        Main.getInstance().getEventManager().callEvent(event);
        return event.getSimpleObject();
    }

    public Entity addCustomDataIfNotExists(String key, Object value) {
        SimpleObjectModifyEvent event = new SimpleObjectModifyEvent(this, this.data,
                this.data.append(key, value), async);
        Main.getInstance().getEventManager().callEvent(event);
        return event.getSimpleObject();
    }

    public ConcurrentObjectMap<String, Object> getCustomData() {
        return this.data;
    }

    public <T> T getCustomData(String key) {
        return (T) this.data.get(key);
    }
}
