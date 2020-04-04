package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents the user this application is running for.
 */
public class CurrentUser {

    private String id;
    private String username;
    private String discriminator;
    private String avatar;
    private boolean bot;

    @JsonProperty("mfa_enabled")
    private boolean twoFAEnabled;

    private boolean verified;
    private String email;
    private String locale;
    private int flags;

    @JsonCreator
    public CurrentUser(@JsonProperty("id") String id,
                       @JsonProperty("username") String username,
                       @JsonProperty("discriminator") String discriminator,
                       @JsonProperty("avatar") String avatar,
                       @JsonProperty("bot") boolean bot,
                       @JsonProperty("mfa_enabled") boolean twoFAEnabled,
                       @JsonProperty("verified") boolean verified,
                       @JsonProperty("email") String email,
                       @JsonProperty("locale") String locale,
                       @JsonProperty("flags") int flags) {
        this.id = id;
        this.username = username;
        this.discriminator = discriminator;
        this.avatar = avatar;
        this.bot = bot;
        this.twoFAEnabled = twoFAEnabled;
        this.verified = verified;
        this.email = email;
        this.locale = locale;
        this.flags = flags;
    }

    /**
     * Returns the user's id. This should be used as a unique identifier of the user, as other things may change.
     *
     * @return user id
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     *
     * @return username
     */
    @Nonnull
    public String getUsername() {
        return username;
    }

    /**
     * Returns the discriminator of the user.
     * <p>
     * A discriminator is a 4 digit number, appended after the user's name, divided by a "#" from the user name.
     *
     * @return discriminator
     */
    @Nonnull
    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * Returns the user's avatar icon hash
     *
     * @return avatar icon hash
     */
    @Nullable
    public String getAvatar() {
        return avatar;
    }

    /**
     * Returns whenever the current user is a bot.
     *
     * @return <code>true</code> if bot, <code>false</code> otherwise
     */
    public boolean isBot() {
        return bot;
    }

    /**
     * Returns whenever the current user has 2FA enabled.
     *
     * @return <code>true</code> if 2fa enabled, <code>false</code> otherwise
     */
    public boolean isTwoFAEnabled() {
        return twoFAEnabled;
    }

    /**
     * Returns if the user has verified his email or not.
     *
     * @return <code>true</code> if verified, <code>false</code> otherwise
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Returns the email of the user.
     *
     * @return email
     */
    @Nonnull
    public String getEmail() {
        return email;
    }

    /**
     * Returns the locale, set on the discord client by the user.
     *
     * @return locale
     */
    @Nonnull
    public String getLocale() {
        return locale;
    }

    // todo: better documentation on this
    /**
     * Returns flags.
     *
     * @return flags
     */
    public int getFlags() {
        return flags;
    }
}
