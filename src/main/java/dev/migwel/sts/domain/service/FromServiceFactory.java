package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.FromRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FromServiceFactory {

    private final List<FromService<?>> fromServices;

    @Autowired
    public FromServiceFactory(List<FromService<?>> fromServices) {
        this.fromServices = List.copyOf(fromServices);
    }

    public <T extends FromRequest> FromService<T> getFromService(T fromRequest) {
        for (FromService<?> fromService : fromServices) {
            if (fromService.isRelevant(fromRequest.getClass())) {
                return (FromService<T>) fromService;
            }
        }
        throw new IllegalArgumentException("No search service available for this search request");
    }
}
