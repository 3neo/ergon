package com.jewel.ergon.jobs.exceptions;

public class IncompatibleSourceAndTargetFieldsTypesException extends  Exception {

    /**
     * thrown if source and target classes haves two fields with incompatibles types
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public IncompatibleSourceAndTargetFieldsTypesException(String message) {
        super(message);
    }
}
