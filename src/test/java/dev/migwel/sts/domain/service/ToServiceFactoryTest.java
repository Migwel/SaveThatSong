package dev.migwel.sts.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.TestSpringConfiguration;
import dev.migwel.sts.domain.model.ToRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@AutoConfigureTestDatabase
class ToServiceFactoryTest {

    private final ToServiceFactory serviceFactory;

    @Autowired
    public ToServiceFactoryTest(ToServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Test
    void getFromService() {
        Class<?>[] permittedSearchRequests = ToRequest.class.getPermittedSubclasses();
        for (Class<?> permittedSearchRequest : permittedSearchRequests) {
            try {
                serviceFactory.getToService((Class<? extends ToRequest>) permittedSearchRequest);
            } catch (IllegalArgumentException e) {
                fail(
                        "Exception occurred while trying to get to service for request: "
                                + permittedSearchRequest
                                + ". Reason: "
                                + e.getMessage());
            }
        }
    }
}
