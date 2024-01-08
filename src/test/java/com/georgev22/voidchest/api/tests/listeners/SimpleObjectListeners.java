package com.georgev22.voidchest.api.tests.listeners;

import com.georgev22.voidchest.api.event.annotations.EventHandler;
import com.georgev22.voidchest.api.event.interfaces.EventListener;
import com.georgev22.voidchest.api.tests.Main;
import com.georgev22.voidchest.api.tests.events.SimpleObjectCreationEvent;
import com.georgev22.voidchest.api.tests.events.SimpleObjectModifyEvent;
import com.georgev22.voidchest.api.tests.objects.SimpleObject;

public class SimpleObjectListeners implements EventListener {

    @EventHandler
    public void creationEvent(SimpleObjectCreationEvent event) {
        SimpleObject simpleObject = event.getSimpleObject();
        Main.getLogger().info("Event " + event.getEventName() + ": " + simpleObject.getCustomData().toString());
        Main.getLogger().info("Event " + event.getEventName() + ": " + event.isAsynchronous());
    }

    @EventHandler
    public void modificationEvent(SimpleObjectModifyEvent event) {
        SimpleObject simpleObject = event.getSimpleObject();
        Main.getLogger().info("Event " + event.getEventName() + ": " + simpleObject.getCustomData().toString());
        Main.getLogger().info("Event " + event.getEventName() + ": " + event.isAsynchronous());
    }

}
