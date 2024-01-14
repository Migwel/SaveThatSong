package dev.migwel.sts.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.TestSpringConfiguration;
import dev.migwel.sts.domain.model.ToRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(TestSpringConfiguration.class)
class ToServiceTest {

    private final List<ToService<?>> toServices;

    @Autowired
    public ToServiceTest(List<ToService<?>> toServices) {
        this.toServices = toServices;
    }

    @Test
    void isRelevant_onlyOneRelevantServicePerRequestType() {
        Class<?>[] permittedSaveRequests = ToRequest.class.getPermittedSubclasses();
        for (Class<?> permittedSearchRequest : permittedSaveRequests) {
            int nbRelevantSearchServices = 0;
            for (ToService<?> toService : toServices) {
                if (toService.isRelevant(permittedSearchRequest)) {
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
