package dev.migwel.sts.service;

import dev.migwel.sts.model.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchServiceFactory {

    private final List<SearchService<?>> searchServices;

    @Autowired
    public SearchServiceFactory(List<SearchService<?>> searchServices) {
        this.searchServices = List.copyOf(searchServices);
    }

    public <T extends SearchRequest> SearchService<T> getSearchService(T searchRequest) {
        for (SearchService<?> searchService : searchServices) {
            if (searchService.isRelevant(searchRequest.getClass())) {
                return (SearchService<T>) searchService;
            }
        }
        throw new IllegalArgumentException("No search service available for this search request");
    }
}
