package dev.migwel.sts.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.Application;
import dev.migwel.sts.domain.model.FromRequest;

import dev.migwel.sts.domain.model.ToRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(Application.class)
class ToServiceFactoryTest {

    private final ToServiceFactory serviceFactory;

    @Autowired
    ToServiceFactoryTest(ToServiceFactory serviceFactory) {
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
