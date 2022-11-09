package com.mrivanplays.jdoa2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents the user this application is running for.
 */
public class CurrentUser implements ISnowflake {

    private String id;
    private String username;
    private String discriminator;
    private String avatar;
    private boolean twoFAEnabled;
    private boolean verified;
    private String email;
    private String locale;
    private int flags;

    public CurrentUser(String id, String username, String discriminator,
                       String avatar, boolean twoFAEnabled, boolean verified,
                       String email, String locale, int flags) {
        this.id = id;
        this.username = username;
        this.discriminator = discriminator;
        this.avatar = avatar;
        this.twoFAEnabled = twoFAEnabled;
        this.verified = verified;
        this.email = email;
        this.locale = locale;
        this.flags = flags;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
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
     * Returns the user's default avatar icon hash.
     *
     * @return default avatar icon hash
     */
    @Nonnull
    public String getDefaultAvatar() {
        String[] defAvatars = JDOA2Utils.DEFAULT_AVATARS;
        return defAvatars[Integer.parseInt(discriminator) % defAvatars.length];
    }

    /**
     * Returns the user's effective avatar icon hash, effective meaning currently applied.
     *
     * @return currently applied avatar icon hash
     */
    @Nonnull
    public String getEffectiveAvatar() {
        return avatar == null ? getDefaultAvatar() : avatar;
    }

    /**
     * Returns whenever the current user is a bot.
     *
     * @return <code>true</code> if bot, <code>false</code> otherwise
     * @deprecated Due to the nature of OAuth2 at this moment, bots are not allowed to use the various urls provided.
     */
    @Deprecated
    public boolean isBot() {
        return false;
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
     * Returns the email of the user, if so we have the scope provided.
     *
     * @return email
     */
    @Nullable
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

    /**
     * Returns bitwise calculated flags.
     * <p>
     * See: <a href="https://discordapp.com/developers/docs/resources/user#user-object-user-flags">User flags (discord
     * api documentation)</a>
     *
     * @return flags
     */
    public int getFlags() {
        return flags;
    }

    /**
     * Gets the user as a discord formatted mention.
     *
     * @return mention
     */
    @Nonnull
    public String getAsMention() {
        return "<@" + id + ">";
    }
}
