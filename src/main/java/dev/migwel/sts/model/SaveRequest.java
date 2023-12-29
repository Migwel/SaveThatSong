package dev.migwel.sts.model;

import javax.annotation.Nonnull;

public sealed interface SaveRequest permits PersistSaveRequest {}
