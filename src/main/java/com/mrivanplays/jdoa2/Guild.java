package com.mrivanplays.jdoa2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a guild object, holding information about the {@link CurrentUser CurrentUser's} state, and also holding
 * very basic information about the guild itself.
 */
public class Guild {

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
     * Returns the id of the guild. This should be used as a unique identifier of the guild, as other parameters can
     * change.
     *
     * @return guild id
     */
    @Nonnull
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

    // todo: better documentation of this
    /**
     * Returns the permission integer.
     *
     * @return permission
     */
    public int getPermissions() {
        return permissions;
    }
}
