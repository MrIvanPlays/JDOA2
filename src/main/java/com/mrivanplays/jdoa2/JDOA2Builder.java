package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrivanplays.jdoa2.internal.JDOA2Impl;

import java.util.Objects;

import javax.annotation.Nonnull;

import okhttp3.OkHttpClient;

/**
 * Represents a {@link JDOA2} builder.
 */
public class JDOA2Builder {

    private ApplicationInfo applicationInfo;
    private ObjectMapper jsonMapper;
    private OkHttpClient okHttpClient;

    public JDOA2Builder() {
        this(null);
    }

    public JDOA2Builder(@Nonnull ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
        this.jsonMapper = new ObjectMapper();
        this.okHttpClient = new OkHttpClient();
    }

    /**
     * Returns the {@link ApplicationInfo} set.
     *
     * @return application info
     */
    public ApplicationInfo applicationInfo() {
        return applicationInfo;
    }

    /**
     * Sets a new application info.
     *
     * @param applicationInfo application info
     * @return this instance for chaining
     */
    public JDOA2Builder applicationInfo(@Nonnull ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
        return this;
    }

    /**
     * Returns the {@link ObjectMapper} set.
     *
     * @return json mapper
     */
    public ObjectMapper jsonMapper() {
        return jsonMapper;
    }

    /**
     * Sets a new json mapper.
     *
     * @param jsonMapper json mapper
     * @return this instance for chaining
     */
    public JDOA2Builder jsonMapper(@Nonnull ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        return this;
    }

    /**
     * Returns the {@link OkHttpClient} set.
     *
     * @return http client
     */
    public OkHttpClient httpClient() {
        return okHttpClient;
    }

    /**
     * Sets a new http client
     *
     * @param httpClient http client
     * @return this instance for chaining
     */
    public JDOA2Builder httpClient(@Nonnull OkHttpClient httpClient) {
        this.okHttpClient = httpClient;
        return this;
    }

    /**
     * Builds this builder's parameters into a {@link JDOA2} instance.
     *
     * @return jdoa2 instance
     */
    public JDOA2 build() {
        Objects.requireNonNull(applicationInfo, "applicationInfo");
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        if (jsonMapper == null) {
            jsonMapper = new ObjectMapper();
        }
        return new JDOA2Impl(this);
    }
}
