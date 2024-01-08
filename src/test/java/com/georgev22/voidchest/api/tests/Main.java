package com.georgev22.voidchest.api.tests;

import com.georgev22.library.utilities.LoggerWrapper;
import com.georgev22.voidchest.api.event.EventManager;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.tests.listeners.SimpleObjectListeners;
import com.georgev22.voidchest.api.tests.objects.SimpleObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.UUID;

public class Main {

    private final EventManager eventManager;

    private static final Logger log4JLogger = LogManager.getLogger(Main.class);

    private static final java.util.logging.Logger logger = new LoggerWrapper(log4JLogger);

    private static Main instance;

    public static void main(String[] args) {
        Main main = new Main();
        instance = main;
        main.start();
    }

    public Main() {
        this.eventManager = new EventManager(logger, Main.class);
        this.eventManager.register(new SimpleObjectListeners());
    }

    public void start() {
        logger.info("Task took " + Timings.measureTime(this::createSimpleObject) + " milliseconds");
        logger.info("Handler lists: " + HandlerList.getHandlerLists().stream()
                .map(handlerList ->
                        Arrays.stream(handlerList.getListenerWrappers())
                                .map(listenerWrapper -> listenerWrapper.eventExecutor().getClass().getName())
                                .toList()
                ).toList()
        );
        System.exit(0);
    }

    public void createSimpleObject() {
        for (int i = 0; i < 100; i++) {
            SimpleObject simpleObject = new SimpleObject(UUID.randomUUID());
            for (int j = 0; j < 100; j++) {
                simpleObject.addCustomData("key" + j, "value" + j);
            }
        }
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public static Main getInstance() {
        return instance;
    }

    public static Logger getLog4JLogger() {
        return log4JLogger;
    }

    public static java.util.logging.Logger getLogger() {
        return logger;
    }
}
