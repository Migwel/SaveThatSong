package dev.migwel.sts.controller.dto;

import javax.annotation.CheckForNull;

public record ToResult(boolean success, @CheckForNull String errorMessage) {}
