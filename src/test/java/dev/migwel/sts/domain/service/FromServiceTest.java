package dev.migwel.sts.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.TestSpringConfiguration;
import dev.migwel.sts.domain.model.FromRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(TestSpringConfiguration.class)
class FromServiceTest {

    private final List<FromService<?>> fromServices;

    @Autowired
    public FromServiceTest(List<FromService<?>> fromServices) {
        this.fromServices = fromServices;
    }

    @Test
    void isRelevant_onlyOneRelevantServicePerRequestType() {
        Class<?>[] permittedSearchRequests = FromRequest.class.getPermittedSubclasses();
        for (Class<?> permittedSearchRequest : permittedSearchRequests) {
            int nbRelevantSearchServices = 0;
            for (FromService<?> fromService : fromServices) {
                if (fromService.isRelevant(permittedSearchRequest)) {
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
