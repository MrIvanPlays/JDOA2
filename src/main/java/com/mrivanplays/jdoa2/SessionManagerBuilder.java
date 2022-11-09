package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrivanplays.jdoa2.internal.SessionManagerImpl;

import java.util.Objects;

import okhttp3.OkHttpClient;

public class SessionManagerBuilder {

    private String clientId, clientSecret, redirectUri;
    private OkHttpClient httpClient;
    private ObjectMapper jsonMapper;

    public String clientId() {
        return clientId;
    }

    public SessionManagerBuilder clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String clientSecret() {
        return clientSecret;
    }

    public SessionManagerBuilder clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String redirectUri() {
        return redirectUri;
    }

    public SessionManagerBuilder redirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public OkHttpClient httpClient() {
        return httpClient;
    }

    public SessionManagerBuilder httpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public ObjectMapper jsonMapper() {
        return jsonMapper;
    }

    public SessionManagerBuilder jsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        return this;
    }

    public SessionManager build() {
        Objects.requireNonNull(clientId, "clientId");
        Objects.requireNonNull(clientSecret, "clientSecret");
        Objects.requireNonNull(redirectUri, "redirectUri");
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }
        if (jsonMapper == null) {
            jsonMapper = new ObjectMapper();
        }
        return new SessionManagerImpl(this);
    }
}
