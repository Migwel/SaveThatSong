package dev.migwel.sts.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.Application;
import dev.migwel.sts.model.SaveRequest;
import dev.migwel.sts.model.SearchRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(Application.class)
class SaveServiceTest {

    private final List<SaveService<?>> saveServices;

    @Autowired
    public SaveServiceTest(List<SaveService<?>> saveServices) {
        this.saveServices = saveServices;
    }

    @Test
    void isRelevant_onlyOneRelevantServicePerRequestType() {
        Class<?>[] permittedSaveRequests = SaveRequest.class.getPermittedSubclasses();
        for (Class<?> permittedSearchRequest : permittedSaveRequests) {
            int nbRelevantSearchServices = 0;
            for (SaveService<?> saveService : saveServices) {
                if (saveService.isRelevant(permittedSearchRequest)) {
                    nbRelevantSearchServices += 1;
                }
            }
            assertEquals(
                    1,
                    nbRelevantSearchServices,
                    "Didn't find exactly one save service for request "
                            + permittedSearchRequest.getName());
        }
    }
}
