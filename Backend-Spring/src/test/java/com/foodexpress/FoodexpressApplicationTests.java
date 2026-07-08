package com.foodexpress;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: verifica che l'intero context Spring si avvii correttamente
 * (entità JPA, repository, service, controller, security, seed) su H2.
 */
@SpringBootTest
class FoodexpressApplicationTests {

    @Test
    void contextLoads() {
    }
}
