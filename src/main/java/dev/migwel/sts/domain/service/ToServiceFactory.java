package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.ToRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Component
@ParametersAreNonnullByDefault
public class ToServiceFactory {

    private final List<ToService<?>> toServices;

    @Autowired
    public ToServiceFactory(List<ToService<?>> toServices) {
        this.toServices = List.copyOf(toServices);
    }

    @Nonnull
    public <T extends ToRequest> ToService<T> getToService(
            Class<? extends ToRequest> requestClass) {
        for (ToService<?> toService : toServices) {
            if (toService.isRelevant(requestClass)) {
                return (ToService<T>) toService;
            }
        }
        throw new IllegalArgumentException("No To service available for this search request");
    }
}
