package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.ToRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToServiceFactory {

    private final List<ToService<?>> toServices;

    @Autowired
    public ToServiceFactory(List<ToService<?>> toServices) {
        this.toServices = List.copyOf(toServices);
    }

    public <T extends ToRequest> ToService<T> getSaveService(T saveRequest) {
        for (ToService<?> toService : toServices) {
            if (toService.isRelevant(saveRequest.getClass())) {
                return (ToService<T>) toService;
            }
        }
        throw new IllegalArgumentException("No search service available for this search request");
    }
}
