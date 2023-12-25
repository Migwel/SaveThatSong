package dev.migwel.sts.model;

public record SonosSearchRequest(String householdId, String groupId) implements SearchRequest {}
