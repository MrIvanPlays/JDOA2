package com.mrivanplays.jdoa2;

import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Represents information about the application, driven by this discord api wrapper.
 */
public final class ApplicationInfo {

    private final String authCode;
    private final String redirectUri;
    private final String clientId;
    private final String clientSecret;

    public ApplicationInfo(String authCode, String redirectUri, String clientId, String clientSecret) {
        this(new Builder().authCode(authCode).redirectUri(redirectUri).clientId(clientId).clientSecret(clientSecret));
    }

    public ApplicationInfo(Builder builder) {
        this.authCode = builder.authCode();
        this.redirectUri = builder.redirectUri();
        this.clientId = builder.clientId();
        this.clientSecret = builder.clientSecret();
    }

    /**
     * Creates a new {@link ApplicationInfo.Builder}
     *
     * @return builder
     */
    public static ApplicationInfo.Builder builder() {
        return new ApplicationInfo.Builder();
    }

    /**
     * Returns the auth code. The auth code is a code, received on url callback, which you have to configure in your
     * discord application config.
     * <p>
     * Example of what you need to specify here:
     *     1. Open your discord application config
     *     2. Navigate to OAuth2
     *     3. Add redirect url and save
     *     4. Generate OAuth2 link with the supported scopes: identify, email, guilds
     * After doing all the steps, try using the URL you created. If everything was done correctly, after allowing the
     * application by discord your browser will auto redirect you to your callback url and will append
     * "?code=AUTHENTICATION CODE" to the url, the "authentication code" specified is your authentication code that you
     * need to specify on this value.
     *
     * @return auth code
     */
    @Nonnull
    public String getAuthCode() {
        return authCode;
    }

    /**
     * Returns the application's URL callback.
     *
     * @return redirect uri
     */
    @Nonnull
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Returns the client id of your application, found in the discord application configuration.
     *
     * @return client id
     */
    @Nonnull
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the client secret of your application, found in the discord application configuration.
     *
     * @return client secret
     */
    @Nonnull
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Represents a builder, for easily creating {@link ApplicationInfo} instance.
     */
    public static final class Builder {

        private String authCode;
        private String redirectUri;
        private String clientId;
        private String clientSecret;

        /**
         * Authentication code of the user you want to get information of.
         *
         * @return auth code
         * @see ApplicationInfo#getAuthCode()
         */
        public String authCode() {
            return authCode;
        }

        /**
         * Sets the authentication code of the user you want to get information of.
         *
         * @param authCode authentication code
         * @return this instance for chaining
         * @see ApplicationInfo#getAuthCode()
         */
        public Builder authCode(@Nonnull String authCode) {
            this.authCode = authCode;
            return this;
        }

        /**
         * Application's URL callback.
         *
         * @return redirect uri
         */
        public String redirectUri() {
            return redirectUri;
        }

        /**
         * Sets the application's URL callback.
         *
         * @param redirectUri redirect uri
         * @return this instance for chaining
         */
        public Builder redirectUri(@Nonnull String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        /**
         * Application's client id.
         *
         * @return client id
         * @see ApplicationInfo#getClientId()
         */
        public String clientId() {
            return clientId;
        }

        /**
         * Sets the application's client id.
         *
         * @param clientId client id
         * @return this instance for chaining
         * @see ApplicationInfo#getClientId()
         */
        public Builder clientId(@Nonnull String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * Application's client secret.
         *
         * @return client secret
         */
        public String clientSecret() {
            return clientSecret;
        }

        /**
         * Sets the application's client secret.
         *
         * @param clientSecret client secret
         * @return this instance for chaining
         */
        public Builder clientSecret(@Nonnull String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        /**
         * Builds the specified parameters of the builder into an {@link ApplicationInfo}
         *
         * @return application info
         */
        public ApplicationInfo build() {
            Objects.requireNonNull(authCode, "authCode");
            Objects.requireNonNull(redirectUri, "redirectUri");
            Objects.requireNonNull(clientId, "clientId");
            Objects.requireNonNull(clientSecret, "clientSecret");
            return new ApplicationInfo(this);
        }
    }
}
