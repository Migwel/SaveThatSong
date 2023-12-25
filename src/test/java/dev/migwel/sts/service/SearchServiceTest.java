package dev.migwel.sts.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.Application;
import dev.migwel.sts.model.SearchRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(Application.class)
class SearchServiceTest {

    private final List<SearchService<?>> searchServices;

    @Autowired
    public SearchServiceTest(List<SearchService<?>> searchServices) {
        this.searchServices = searchServices;
    }

    @Test
    void isRelevant_onlyOneRelevantServicePerRequestType() {
        Class<?>[] permittedSearchRequests = SearchRequest.class.getPermittedSubclasses();
        for (Class<?> permittedSearchRequest : permittedSearchRequests) {
            int nbRelevantSearchServices = 0;
            for (SearchService<?> searchService : searchServices) {
                if (searchService.isRelevant(permittedSearchRequest)) {
                    nbRelevantSearchServices += 1;
                }
            }
            assertEquals(
                    1,
                    nbRelevantSearchServices,
                    "Didn't find exactly one search service for request "
                            + permittedSearchRequest.getName());
        }
    }
}
