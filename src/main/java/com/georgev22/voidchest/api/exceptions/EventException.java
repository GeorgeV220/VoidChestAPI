package com.georgev22.voidchest.api.exceptions;

/**
 * The EventException class is an exception that can be thrown during event handling.
 * It extends the RuntimeException class.
 */
public class EventException extends RuntimeException {

    /**
     * Constructs a new EventException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public EventException(String message) {
        super(message);
    }

    /**
     * Constructs a new EventException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EventException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public EventException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new EventException with the specified detail message, cause, enableSuppression, and writableStackTrace.
     *
     * @param message            the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause              the cause (which is saved for later retrieval by the Throwable.getCause() method).
     * @param enableSuppression  whether suppression is enabled or disabled.
     * @param writableStackTrace whether the stack trace should be writable.
     */
    public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}