package org.example.examen_final_poo.dao;

import org.example.examen_final_poo.model.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Repository
public class ProductDaoImpl implements ProductDao {

    private static final String SQL_FIND_BY_ID =
            "SELECT id, name, description, unit_price " +
                    "FROM product " +
                    "WHERE id = ?";

    private final DataSource dataSource;

    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Product> findById(String id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToProduct(resultSet));
                }
                return Optional.empty();
            }
        }
    }


    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getString("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setUnitPrice(resultSet.getBigDecimal("unit_price"));
        return product;
    }
}