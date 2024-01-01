package dev.migwel.sts.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.Application;
import dev.migwel.sts.domain.model.FromRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(Application.class)
class FromServiceFactoryTest {

    private final FromServiceFactory serviceFactory;

    @Autowired
    FromServiceFactoryTest(FromServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Test
    void getFromService() {
        Class<?>[] permittedSearchRequests = FromRequest.class.getPermittedSubclasses();
        for (Class<?> permittedSearchRequest : permittedSearchRequests) {
            try {
                serviceFactory.getFromService(
                        (Class<? extends FromRequest>) permittedSearchRequest);
            } catch (IllegalArgumentException e) {
                fail(
                        "Exception occurred while trying to get from service for request: "
                                + permittedSearchRequest
                                + ". Reason: "
                                + e.getMessage());
            }
        }
    }
}
