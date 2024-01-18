package dev.migwel.sts.domain.model;

import javax.annotation.CheckForNull;

public record FromSonosRequest(@CheckForNull String householdId, @CheckForNull String groupId)
        implements FromRequest {}
