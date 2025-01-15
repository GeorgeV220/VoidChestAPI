package com.georgev22.voidchest.api.event;

/**
 * Represents the priority level of an event listener.
 * Listeners with lower priority will be called before those with higher priority.
 * The execution order of listeners with the same priority is undefined.
 * <p>
 * It is not recommended to make data changes in listeners with priority {@link #LOWEST} and {@link #LOW}.
 * Data changes should be made in listeners with priority {@link #NORMAL} or {@link #HIGHEST}.
 * <p>
 * The recommended priority levels for most listeners are {@link #NORMAL} and {@link #HIGHEST}.
 * <p>
 * To monitor events, use {@link #MONITOR}.
 */
public enum EventPriority {
    /**
     * Lowest priority.
     * Executed first.
     * Not recommended for data changes.
     */
    LOWEST(-1000),

    /**
     * Low priority.
     */
    LOW(-500),

    /**
     * Normal priority.
     * Recommended for most listeners.
     */
    NORMAL(0),

    /**
     * High priority.
     * Recommended for data changes.
     */
    HIGH(500),

    /**
     * Highest priority.
     * Recommended for data changes.
     */
    HIGHEST(1000),

    /**
     * Monitor priority.
     * Executed last.
     * Not recommended for data changes but only for monitoring purposes.
     */
    MONITOR(2000),
    ;

    private final int value;

    /**
     * Constructs an {@code EventPriority} with the given value.
     *
     * @param value the priority value
     */
    EventPriority(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value of this priority level.
     *
     * @return the integer value of this priority level
     */
    public int getValue() {
        return value;
    }
}
