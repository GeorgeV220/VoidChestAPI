package com.georgev22.voidchest.api.exceptions;

/**
 * The UserException class is an exception that can be thrown for user-related errors.
 * It extends the RuntimeException class.
 */
public class UserException extends RuntimeException {

    /**
     * Constructs a new UserException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public UserException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public UserException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserException with the specified detail message, cause, enableSuppression, and writableStackTrace.
     *
     * @param message            the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause              the cause (which is saved for later retrieval by the Throwable.getCause() method).
     * @param enableSuppression  whether suppression is enabled or disabled.
     * @param writableStackTrace whether the stack trace should be writable.
     */
    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

