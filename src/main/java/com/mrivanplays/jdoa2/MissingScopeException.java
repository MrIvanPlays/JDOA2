package com.mrivanplays.jdoa2;

/**
 * Represents a {@link RuntimeException}, thrown if a data is requested which isn't accessible (doesn't have a specified
 * scope.)
 */
public class MissingScopeException extends RuntimeException {

    public MissingScopeException(String message) {
        super(message);
    }
}
