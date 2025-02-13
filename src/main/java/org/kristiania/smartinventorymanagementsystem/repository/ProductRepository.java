package org.kristiania.smartinventorymanagementsystem.repository;

import org.kristiania.smartinventorymanagementsystem.exceptions.DatabaseException;
import org.kristiania.smartinventorymanagementsystem.exceptions.ProductNotFoundException;
import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * New instance of rowMapper class. This class is a functional interface, which means that is has one abstract method.
     * When this new instance is created it specifies that it uses the Product type.
    *This method figures out what is at each row of the database. ResultSet points to a row of a table.
    * Uses the resultSet value and the row number to create a new instance of Product at each of the different rows
    * and gets the value of each column of one row. These values are then saved in that rows' instance of Product
    **/
    private RowMapper<Product> productRowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("quantity"),
            resultSet.getDouble("price"),
            resultSet.getInt("supplier_id")
    );

    /**
     * @param id of product
     * @return list containing new query, one row of productRowMapper and id of product
     */
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
    }

    /**
     *
     * @return list containing products on every row
     */
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * Update is used instead of create because it works the same, but easier
     * @param product you want to save in database
     */
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

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @param product you want to update
     */
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

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @param id of product to be deleted
     */
    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
