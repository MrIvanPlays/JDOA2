package com.mrivanplays.jdoa2.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrivanplays.jdoa2.ApplicationInfo;
import com.mrivanplays.jdoa2.JDOA2;
import com.mrivanplays.jdoa2.SessionManager;
import com.mrivanplays.jdoa2.SessionManagerBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;

import okhttp3.OkHttpClient;

public class SessionManagerImpl implements SessionManager {

    private Map<String, JDOA2> sessions;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ApplicationInfo.Builder cachedInfoBuilder;

    public SessionManagerImpl(SessionManagerBuilder builder) {
        sessions = new HashMap<>();
        cachedInfoBuilder = ApplicationInfo.builder()
                .clientId(builder.clientId())
                .clientSecret(builder.clientSecret())
                .redirectUri(builder.redirectUri());
        httpClient = builder.httpClient();
        objectMapper = builder.jsonMapper();
    }

    @Override
    @Nonnull
    public Map<String, JDOA2> getSessions() {
        return Collections.unmodifiableMap(sessions);
    }

    @Override
    @Nonnull
    public JDOA2 newSession(@Nonnull String authCode) {
        Objects.requireNonNull(authCode, "authCode");
        if (sessions.containsKey(authCode)) {
            throw new IllegalArgumentException("Session with auth code '" + authCode + "' already exists");
        }
        JDOA2 jdoa2 = JDOA2.builder()
                .applicationInfo(cachedInfoBuilder.authCode(authCode).build())
                .jsonMapper(objectMapper)
                .httpClient(httpClient)
                .build();
        sessions.put(authCode, jdoa2);
        return jdoa2;
    }

    @Override
    public Optional<JDOA2> getSession(@Nonnull String authCode) {
        return Optional.ofNullable(sessions.get(authCode));
    }

    @Nonnull
    @Override
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Nonnull
    @Override
    public ObjectMapper getJsonMapper() {
        return objectMapper;
    }
}
