package dev.migwel.sts.domain.service;

import dev.migwel.sts.domain.model.FromRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Component
@ParametersAreNonnullByDefault
public class FromServiceFactory {

    private final List<FromService<?>> fromServices;

    @Autowired
    public FromServiceFactory(List<FromService<?>> fromServices) {
        this.fromServices = List.copyOf(fromServices);
    }

    @Nonnull
    public <T extends FromRequest> FromService<T> getFromService(
            Class<? extends FromRequest> requestClass) {
        for (FromService<?> fromService : fromServices) {
            if (fromService.isRelevant(requestClass)) {
                return (FromService<T>) fromService;
            }
        }
        throw new IllegalArgumentException("No From service available for this search request");
    }
}
