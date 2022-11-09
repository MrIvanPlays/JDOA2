package com.mrivanplays.jdoa2;

/**
 * Represents a exception, thrown whenever we hit the rate limit of discord's api.
 */
public class RateLimitedException extends RuntimeException {

    public RateLimitedException(String message) {
        super(message);
    }
}
