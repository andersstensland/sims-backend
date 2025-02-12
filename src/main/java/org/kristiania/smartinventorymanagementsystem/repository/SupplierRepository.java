package org.kristiania.smartinventorymanagementsystem.repository;

import org.kristiania.smartinventorymanagementsystem.exceptions.DatabaseException;
import org.kristiania.smartinventorymanagementsystem.model.Supplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SupplierRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Supplier> supplierRowMapper = (rs, rowNum) -> new Supplier(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("contact_details")
    );

    public Supplier findById(Long id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, supplierRowMapper, id);
    }

    public List<Supplier> findAll() {
        String sql = "SELECT * FROM suppliers";
        return jdbcTemplate.query(sql, supplierRowMapper);
    }

    public void save(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, contact_details) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, supplier.getName(), supplier.getContactDetails());
        } catch (Exception e) {
            throw new DatabaseException("Error saving supplier", e);
        }
    }

    // etc. (update, delete)
}
