package org.kristiania.smartinventorymanagementsystem.repository;

import org.kristiania.smartinventorymanagementsystem.exceptions.DatabaseException;
import org.kristiania.smartinventorymanagementsystem.model.Sale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleRepository {

    private final JdbcTemplate jdbcTemplate;

    public SaleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Sale> saleRowMapper = (rs, rowNum) -> new Sale(
            rs.getLong("id"),
            rs.getLong("product_id"),
            rs.getInt("quantity_sold"),
            rs.getDouble("total_price"),
            rs.getTimestamp("sale_date").toLocalDateTime(),
            rs.getString("customer"),
            rs.getDouble("discount")
    );

    public void save(Sale sale) {
        String sql = "INSERT INTO sale (product_id, quantity_sold, total_price, sale_date, customer, discount) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    sale.getProductId(),
                    sale.getQuantitySold(),
                    sale.getTotalPrice(),
                    sale.getSaleDate(),
                    sale.getCustomer(),
                    sale.getDiscount()
            );
        } catch (Exception e) {
            throw new DatabaseException("Error saving sale", e);
        }
    }

    public List<Sale> findAll() {
        String sql = "SELECT * FROM sale";
        return jdbcTemplate.query(sql, saleRowMapper);
    }

    // Additional queries for reports, e.g. findSalesByDateRange, etc.
}
