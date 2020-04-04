package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natanbc.reliqua.request.PendingRequest;

import java.util.List;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import okhttp3.OkHttpClient;

/**
 * Represents the driver of the all components.
 * <p>
 * JDOA2 stands for Java Discord OAuth2, representing a discord api wrapper of OAuth2 applications for interacting with
 * user data.
 */
public interface JDOA2 {

    /**
     * Creates a new {@link JDOA2Builder}
     *
     * @return builder
     */
    static JDOA2Builder builder() {
        return new JDOA2Builder();
    }

    /**
     * Creates a new {@link JDOA2Builder}
     *
     * @param applicationInfo application info
     * @return builder
     * @see ApplicationInfo
     */
    static JDOA2Builder builder(ApplicationInfo applicationInfo) {
        return new JDOA2Builder(applicationInfo);
    }

    /**
     * Starts a OAuth2 token exchange process. You may want to call this right after you receive a instance of this
     * class in order for the application to interact with discord api to provide you information.
     * <p>
     * <b>WARNING: This is a thread blocking method, it may block the thread if the application is rate limited by
     * discord api. CONSIDER RUNNING ASYNCHRONOUSLY!!!!</b>
     *
     * @return token object
     * @see DiscordToken
     */
    @Nonnull
    DiscordToken doTokenExchange();

    /**
     * Refreshes an existing OAuth2 token, or if none found, calls {@link #doTokenExchange()}. You may want to call this
     * hand in hand with {@link #isCurrentTokenValid()} in order for the wrapper to have a working access token.
     * <p>
     * <b>WARNING: This is a thread blocking method, it may block the thread if the application is rate limited by
     * discord api. CONSIDER RUNNING ASYNCHRONOUSLY!!!!</b>
     *
     * @return token object
     * @see DiscordToken
     */
    @Nonnull
    DiscordToken doTokenExchangeUsingRefreshToken();

    /**
     * Returns whenever the token we have is valid or not. You may want to check this every time before using any of the
     * information receiver methods, and if the token isn't valid you may want to call {@link
     * #doTokenExchangeUsingRefreshToken()}.
     *
     * @return <code>true</code> if valid, <code>false</code> otherwise
     */
    boolean isCurrentTokenValid();

    /**
     * Retrieves {@link CurrentUser} information, returning {@link PendingRequest}.
     * <p>
     * <b>WARNING: This is a thread blocking method, it may block the thread if the pending request is executed
     * synchronously and the application is rate limited by discord api. CONSIDER COMPLETING ASYNCHRONOUSLY!!!!</b>
     *
     * @return pending request, containing current user information
     * @see PendingRequest
     * @see CurrentUser
     */
    @CheckReturnValue
    PendingRequest<CurrentUser> getCurrentUser();

    /**
     * Returns the icon url of the user specified. Note that {@link CurrentUser#getAvatar()} returns an icon hash, not
     * an url.
     *
     * @param user current user
     * @return icon url
     */
    @Nullable
    default String getUserAvatarUrl(CurrentUser user) {
        if (user.getAvatar() == null) {
            return null;
        }
        String iconId = user.getAvatar();
        return "https://cdn.discordapp.com/avatars/" + user.getId() + "/" + iconId + (iconId.startsWith("a_") ? ".gif" : ".png");
    }

    @Nullable
    default String getGuildIconUrl(Guild guild) {
        if (guild.getIcon() == null) {
            return null;
        }
        String iconId = guild.getIcon();
        return "https://cdn.discordapp.com/icons/" + guild.getId() + "/" + iconId + (iconId.startsWith("a_")? ".gif" : ".png");
    }

    /**
     * Retrieves a {@link List} of {@link Guild Guilds}, which the {@link CurrentUser} has joined in, returning {@link
     * PendingRequest}
     * <p>
     * <b>WARNING: This is a thread blocking method, it may block the thread if the pending request is executed
     * synchronously and the application is rate limited by discord api. CONSIDER COMPLETING ASYNCHRONOUSLY!!!!</b>
     *
     * @return pending request, containing guilds information
     * @see PendingRequest
     * @see Guild
     */
    @CheckReturnValue
    PendingRequest<List<Guild>> getCurrentUserGuilds();

    /**
     * Returns the {@link OkHttpClient}, used to handle requests.
     *
     * @return http client
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

    /**
     * Returns the {@link ApplicationInfo}
     *
     * @return application info
     * @see ApplicationInfo
     */
    @Nonnull
    ApplicationInfo getApplicationInfo();
}
