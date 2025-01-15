package com.georgev22.voidchest.api.exceptions;

/**
 * The VoidFileAlreadySetupException class is an exception that can be thrown when a VoidFile is already set up.
 * It extends the RuntimeException class.
 */
public class VoidFileAlreadySetupException extends RuntimeException {

    /**
     * Constructs a new VoidFileAlreadySetupException with the specified error message.
     *
     * @param errorMessage the error message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public VoidFileAlreadySetupException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Constructs a new VoidFileAlreadySetupException with the specified error messages.
     * The error messages are concatenated using a space separator.
     *
     * @param errorMessage the error messages to be joined and used as the error message.
     */
    public VoidFileAlreadySetupException(String... errorMessage) {
        super(String.join(" ", errorMessage));
    }

    /**
     * Constructs a new VoidFileAlreadySetupException with the specified error message and cause.
     *
     * @param errorMessage the error message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param err          the cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public VoidFileAlreadySetupException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
