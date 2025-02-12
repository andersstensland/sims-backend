package org.kristiania.smartinventorymanagementsystem.repositorytests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.kristiania.smartinventorymanagementsystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import({ProductRepository.class})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Insert some test data
        Product p1 = new Product(null, "Laptop", 10, 999.99, null);
        Product p2 = new Product(null, "Mouse", 50, 19.99, null);
        productRepository.save(p1);
        productRepository.save(p2);
    }

    @Test
    void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }

    @Test
    void testUpdateProduct() {
        Product product = productRepository.findAll().get(0);
        product.setQuantity(5);
        productRepository.update(product);

        Product updated = productRepository.findById(product.getId());
        assertEquals(5, updated.getQuantity());
    }
}

