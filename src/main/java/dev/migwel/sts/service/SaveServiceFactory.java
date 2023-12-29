package dev.migwel.sts.service;

import dev.migwel.sts.model.SaveRequest;
import dev.migwel.sts.model.SearchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaveServiceFactory {

    private final List<SaveService<?>> saveServices;

    @Autowired
    public SaveServiceFactory(List<SaveService<?>> saveServices) {
        this.saveServices = List.copyOf(saveServices);
    }

    public <T extends SaveRequest> SaveService<T> getSaveService(T saveRequest) {
        for (SaveService<?> saveService : saveServices) {
            if (saveService.isRelevant(saveRequest.getClass())) {
                return (SaveService<T>) saveService;
            }
        }
        throw new IllegalArgumentException("No search service available for this search request");
    }
}
