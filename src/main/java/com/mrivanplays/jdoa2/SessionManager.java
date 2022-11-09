package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

import okhttp3.OkHttpClient;

/**
 * Represents a session manager, for managing {@link JDOA2} instances, used by your application.
 */
public interface SessionManager {

    /**
     * Creates a new {@link SessionManagerBuilder}
     *
     * @return builder
     */
    @Nonnull
    static SessionManagerBuilder builder() {
        return new SessionManagerBuilder();
    }

    /**
     * Returns unmodifiable {@link Map}, holding all managed {@link JDOA2} instances, key being the
     * <code>authCode</code> specified when creating the session.
     *
     * @return map with managed {@link JDOA2} instances
     */
    @Nonnull
    Map<String, JDOA2> getSessions();


    /**
     * Creates a new {@link JDOA2} session for the specified <code>authCode</code>. You can see {@link
     * ApplicationInfo#getAuthCode()} for information about that <code>authCode</code>.
     *
     * @param authCode auth code
     * @return fresh new jdoa2 session
     * @throws IllegalArgumentException if session with such code already exists.
     */
    @Nonnull
    JDOA2 newSession(@Nonnull String authCode);

    /**
     * Returns an {@link Optional}, which may hold a {@link JDOA2} session, identified by the <code>authCode</code>
     * specified, if any.
     *
     * @param authCode auth code of the session you want to retrieve
     * @return session if exists, optional empty otherwise
     */
    Optional<JDOA2> getSession(@Nonnull String authCode);

    /**
     * Returns a {@link JDOA2} session, fresh created if didn't exist, retrieved otherwise.
     *
     * @param authCode auth code of the session
     * @return new session or retrieved session
     */
    @Nonnull
    default JDOA2 getOrCreateSession(@Nonnull String authCode) {
        return getSession(authCode).orElse(newSession(authCode));
    }

    /**
     * Returns the {@link ObjectMapper}, used to read json from discord api responses.
     *
     * @return json mapper
     */
    @Nonnull
    OkHttpClient getHttpClient();

    /**
     * Returns the {@link ObjectMapper}, used to read json from discord api responses.
     *
     * @return json mapper
     */
    @Nonnull
    ObjectMapper getJsonMapper();
}
