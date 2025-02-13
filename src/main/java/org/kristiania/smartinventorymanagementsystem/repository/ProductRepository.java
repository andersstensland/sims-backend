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

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("quantity"),
            rs.getDouble("price"),
            rs.getLong("supplier_id"),
            rs.getString("description"),
            rs.getString("category")
    );

    public Product findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public void save(Product product) {
        String sql = "INSERT INTO product (name, quantity, price, supplier_id, description, category) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getSupplierId(),
                    product.getDescription(),
                    product.getCategory()
            );
        } catch (Exception e) {
            throw new DatabaseException("Error saving product", e);
        }
    }

    public void update(Product product) {
        String sql = "UPDATE product SET name=?, quantity=?, price=?, supplier_id=?,  description=?, category=? WHERE id=?";
        try {
            int rows = jdbcTemplate.update(sql,
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getSupplierId(),
                    product.getId(),
                    product.getDescription(),
                    product.getCategory());
            if (rows == 0) {
                throw new ProductNotFoundException("No product found with id " + product.getId());
            }
        } catch (Exception e) {
            throw new DatabaseException("Error updating product", e);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
