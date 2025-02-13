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
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        // Insert some test data
        Product p1 = new Product(1, "Laptop", 10, 999.99, 101);
        Product p2 = new Product(2, "Mouse", 50, 19.99, 102);
        productRepository.save(p1);
        productRepository.save(p2);
    }

    @Test
    public void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }

    @Test
    public void testUpdateProduct() {
        Product product = productRepository.findAll().get(0);
        product.setQuantity(5);
        productRepository.update(product);

        Product updated = productRepository.findById(product.getId());
        assertEquals(5, updated.getQuantity());
    }

    @Test
    public void createTestProduct() {
        Product product1 = new Product(1, "Laptop", 10, 999.99, 101);
        Product product2 = new Product(2, "Mouse", 50, 19.99, 102);

        System.out.println("Product 1: " + product1);
        System.out.println("Product 2: " + product2);
    }

}

