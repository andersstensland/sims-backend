package org.kristiania.smartinventorymanagementsystem.repository;

import org.kristiania.smartinventorymanagementsystem.exceptions.DatabaseException;
import org.kristiania.smartinventorymanagementsystem.exceptions.ProductNotFoundException;
import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("quantity"),
            rs.getDouble("price"),
            rs.getLong("supplier_id")
    );

    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public void save(Product product) {
        String sql = "INSERT INTO products (name, quantity, price, supplier_id) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getSupplierId());
        } catch (Exception e) {
            throw new DatabaseException("Error saving product", e);
        }
    }

    public void update(Product product) {
        String sql = "UPDATE products SET name=?, quantity=?, price=?, supplier_id=? WHERE id=?";
        try {
            int rows = jdbcTemplate.update(sql,
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getSupplierId(),
                    product.getId());
            if (rows == 0) {
                throw new ProductNotFoundException("No product found with id " + product.getId());
            }
        } catch (Exception e) {
            throw new DatabaseException("Error updating product", e);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
