package org.kristiania.smartinventorymanagementsystem.repository;

import org.kristiania.smartinventorymanagementsystem.exceptions.DatabaseException;
import org.kristiania.smartinventorymanagementsystem.model.Supplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * New instance of rowMapper class. This class is a functional interface, which means that is has one abstract method.
     * When this new instance is created it specifies that it uses the Supplier type.
     *This method figures out what is at each row of the database. ResultSet points to a row of a table.
     * Uses the resultSet value and the row number to create a new instance of Supplier at each of the different rows
     * and gets the value of each column of one row. These values are then saved in that rows' instance of Supplier
     **/
    private RowMapper<Supplier> supplierRowMapper = (resultSet, rowNum) -> new Supplier(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("contact_details")
    );

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @param id of supplier
     * @return list containing new query, one row of supplierRowMapper and id of supplier
     */
    public Supplier findById(int id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, supplierRowMapper, id);
    }

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @return list containing products on every row
     */
    public List<Supplier> findAll() {
        String sql = "SELECT * FROM suppliers";
        return jdbcTemplate.query(sql, supplierRowMapper);
    }

    /**
     * Inserts arguments into the correct VALUES in the pre-prepared sql statement
     * @param supplier to be saved in database
     */
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
