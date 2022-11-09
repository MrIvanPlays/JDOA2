package com.mrivanplays.jdoa2.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RateLimitedResponse {

    private boolean global;
    private String message;

    @JsonProperty("retry_after")
    private int retryAfter;

    @JsonCreator
    public RateLimitedResponse(@JsonProperty("global") boolean global,
                               @JsonProperty("message") String message,
                               @JsonProperty("retry_after") int retryAfter) {
        this.global = global;
        this.message = message;
        this.retryAfter = retryAfter;
    }

    public boolean isGlobal() {
        return global;
    }

    public String getMessage() {
        return message;
    }

    public int getRetryAfter() {
        return retryAfter;
    }
}
