package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nonnull;

/**
 * Represents a snowflake object, from discord's api
 */
public interface ISnowflake {

    /**
     * Returns the snowflake, this object is holding.
     *
     * @return id snowflake
     */
    @Nonnull
    String getId();

    /**
     * Returns the snowflake object as a <code>long</code>
     *
     * @return id snowflake as long
     */
    @JsonIgnore
    default long getIdLong() {
        return Long.parseLong(getId());
    }
}
