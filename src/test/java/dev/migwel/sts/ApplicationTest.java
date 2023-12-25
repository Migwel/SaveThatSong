package dev.migwel.sts;

import static org.junit.jupiter.api.Assertions.*;

import dev.migwel.sts.service.SearchService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTest {

    private final List<SearchService<?>> searchServices;

    @Autowired
    public ApplicationTest(List<SearchService<?>> searchServices) {
        this.searchServices = searchServices;
    }

    @Test
    void loadContexts() {
        assertNotNull(searchServices);
        assertFalse(searchServices.isEmpty());
    }
}
