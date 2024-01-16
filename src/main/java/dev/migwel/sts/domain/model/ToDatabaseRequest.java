package dev.migwel.sts.domain.model;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class ToDatabaseRequest implements ToRequest {
    private final String username;

    /**
     * @param username The username of the authenticated user, for which the title should be
     *     persisted in the database
     */
    public ToDatabaseRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
