package com.mrivanplays.jdoa2;

/**
 * Represents an exception, thrown when there was error while trying to authenticate with discord api.
 * <p>
 * If the error wasn't a java exception, the message's format is as such: "http error:message"
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
