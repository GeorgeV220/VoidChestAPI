package com.georgev22.voidchest.api.stacker;

import org.jetbrains.annotations.NotNull;

/**
 * The IStackerManager interface provides methods for managing a Stacker.
 */
public interface IStackerManager {

    /**
     * Retrieves the Stacker instance.
     *
     * @return The Stacker instance.
     */
    @NotNull
    Stacker getStacker();

    /**
     * Hooks the Stacker manager.
     */
    void hook();

}
