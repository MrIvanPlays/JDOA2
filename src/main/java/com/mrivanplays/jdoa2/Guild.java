package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a guild object, holding information about the {@link CurrentUser CurrentUser's} state, and also holding
 * very basic information about the guild itself.
 */
public class Guild implements ISnowflake {

    private String id;
    private String name;
    private String icon;
    private boolean owner;
    private int permissions;

    @JsonCreator
    public Guild(@JsonProperty("id") String id,
                 @JsonProperty("name") String name,
                 @JsonProperty("icon") String icon,
                 @JsonProperty("owner") boolean owner,
                 @JsonProperty("permissions") int permissions) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.owner = owner;
        this.permissions = permissions;
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
     * Returns the guild's name.
     *
     * @return guild name
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Returns the guild's icon hash
     *
     * @return icon hash
     */
    @Nullable
    public String getIcon() {
        return icon;
    }

    /**
     * Returns whenever the {@link CurrentUser} is owner of this guild.
     * <p>
     * A owner represents the user, who have created the guild, and not a user, which has a role which gives him most of
     * the permissions.
     *
     * @return <code>true</code> if owner, <code>false</code> otherwise
     */
    public boolean isOwner() {
        return owner;
    }

    /**
     * Returns total permissions for the {@link CurrentUser} in this guild. You must have knowledge of binary and stuff
     * in order to calculate them.
     * <p>
     * See: <a href="https://discordapp.com/developers/docs/topics/permissions">Permissions (discord api documentation)</a>
     *
     * @return permission
     */
    public int getPermissions() {
        return permissions;
    }
}
