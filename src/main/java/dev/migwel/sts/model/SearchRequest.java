package dev.migwel.sts.model;

public sealed interface SearchRequest permits SonosSearchRequest, RadioSearchRequest {}
