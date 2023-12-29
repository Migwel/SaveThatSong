package dev.migwel.sts.domain.model;

public record FromSonosRequest(String householdId, String groupId) implements FromRequest {}
