package org.kristiania.smartinventorymanagementsystem;

import org.junit.jupiter.api.Test;
import org.kristiania.smartinventorymanagementsystem.repositorytests.ProductRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartInventoryManagementSystemApplicationTests {
    @Test
    void contextLoads() {
    ProductRepositoryTest test = new ProductRepositoryTest();

    test.createTestProduct();
    }

}
