package com.georgev22.voidchest.api.exceptions;

/**
 * Exception thrown to indicate that an invalid inventory type has been encountered.
 * This exception is typically used when attempting to operate on a void chest inventory
 * with an unsupported or unexpected inventory type.
 */
public class InvalidInventoryTypeException extends RuntimeException {

    /**
     * Constructs a new InvalidInventoryTypeException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidInventoryTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidInventoryTypeException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidInventoryTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidInventoryTypeException with the specified cause and a detail message of
     * (cause==null ? null: cause.toString()) (which typically contains the class and detail message
     * of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidInventoryTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new InvalidInventoryTypeException with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause (which is saved for later retrieval by the getCause() method).
     * @param enableSuppression  whether suppression is enabled or disabled.
     * @param writableStackTrace whether the stack trace should be writable.
     */
    public InvalidInventoryTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
