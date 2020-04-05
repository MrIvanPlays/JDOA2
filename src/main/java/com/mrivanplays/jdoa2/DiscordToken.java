package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

/**
 * Represents a token, retrieved from discord API. Holds access token, which at some point, expires, and a refresh token
 * to refresh the access token.
 */
public class DiscordToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonCreator
    public DiscordToken(@JsonProperty("access_token") String accessToken,
                        @JsonProperty("expires_in") long expiresIn,
                        @JsonProperty("refresh_token") String refreshToken,
                        @JsonProperty("scope") String scope,
                        @JsonProperty("token_type") String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    /**
     * Returns the access token.
     *
     * @return access token
     */
    @Nonnull
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Returns the type of the discord token. This may be null if discord api didn't specify it.
     *
     * @return token type
     */
    @Nonnull
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Returns whenever the access token expires. This may be 0 if discord api didn't specify it.
     *
     * @return expires in
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Returns the refresh token, used to refresh the access token.
     *
     * @return refresh token
     */
    @Nonnull
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Returns the scopes we're allowed to get information for.
     *
     * @return scope
     */
    @Nonnull
    public String getScope() {
        return scope;
    }

    /**
     * Parses the scopes to a json array, for easily iterating thru them.
     *
     * @return scope array
     */
    @Nonnull
    @JsonIgnore
    public String[] parseScopes() {
        return scope.split(" ");
    }
}
