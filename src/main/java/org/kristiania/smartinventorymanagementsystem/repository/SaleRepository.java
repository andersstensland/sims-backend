package org.kristiania.smartinventorymanagementsystem.repository;

import org.kristiania.smartinventorymanagementsystem.exceptions.DatabaseException;
import org.kristiania.smartinventorymanagementsystem.model.Sale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SaleRepository {

    private final JdbcTemplate jdbcTemplate;

    public SaleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * New instance of rowMapper class. This class is a functional interface, which means that is has one abstract method.
     * When this new instance is created it specifies that it uses the Sale type.
     *This method figures out what is at each row of the database. ResultSet points to a row of a table.
     * Uses the resultSet value and the row number to create a new instance of Sale at each of the different rows
     * and gets the value of each column of one row. These values are then saved in that rows' instance of Sale
     **/
    private RowMapper<Sale> saleRowMapper = (resultSet, rowNum) -> new Sale(
            resultSet.getInt("id"),
            resultSet.getInt("product_id"),
            resultSet.getInt("quantity_sold"),
            resultSet.getDouble("total_price"),
            resultSet.getTimestamp("sale_date").toLocalDateTime()
    );

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @param sale you want to save in database
     */
    public void save(Sale sale) {
        String sql = "INSERT INTO sales (product_id, quantity_sold, total_price, sale_date) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    sale.getProductId(),
                    sale.getQuantitySold(),
                    sale.getTotalPrice(),
                    sale.getSaleDate());
        } catch (Exception e) {
            throw new DatabaseException("Error saving sale", e);
        }
    }

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @return list of all sales on all rows
     */
    public List<Sale> findAll() {
        String sql = "SELECT * FROM sales";
        return jdbcTemplate.query(sql, saleRowMapper);
    }

    // Additional queries for reports, e.g. findSalesByDateRange, etc.
}
