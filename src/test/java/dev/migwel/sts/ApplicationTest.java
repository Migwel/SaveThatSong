package dev.migwel.sts;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.domain.service.FromService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTest {

    private final List<FromService<?>> fromServices;

    @Autowired
    public ApplicationTest(List<FromService<?>> fromServices) {
        this.fromServices = fromServices;
    }

    @Test
    void loadContexts() {
        assertNotNull(fromServices);
        assertFalse(fromServices.isEmpty());
    }
}
