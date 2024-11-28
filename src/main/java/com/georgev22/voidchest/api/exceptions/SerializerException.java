package com.georgev22.voidchest.api.exceptions;

/**
 * Exception thrown when an issue occurs during serialization or deserialization.
 *
 * <p>The {@code SerializerException} class is a runtime exception that extends {@link RuntimeException}.
 * It is used to indicate errors in the serialization or deserialization process within the VoidChest plugin.
 * </p>
 *
 * <p>The constructor takes a {@code message} parameter, allowing for a custom error message to be provided
 * when an instance of this exception is thrown.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * try {
 *     // Serialization or deserialization code
 * } catch (SerializerException e) {
 *     // Handle the exception, log the error, or take appropriate action
 *     e.printStackTrace();
 * }
 * }</pre>
 * </p>
 *
 * <p>It is recommended to handle this exception appropriately in your code to ensure proper error reporting
 * and user feedback when issues occur during serialization or deserialization.
 * </p>
 */
public class SerializerException extends RuntimeException {

    /**
     * Constructs a new SerializerException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public SerializerException(String message) {
        super(message);
    }
}
