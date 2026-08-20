package org.example.examen_final_poo.dao;

import org.example.examen_final_poo.model.MovementType;
import org.example.examen_final_poo.model.StockMovement;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation JDBC pure de StockMovementDao.
 * Connection, PreparedStatement, ResultSet manuels, requêtes paramétrées,
 * mapping ResultSet -> POJO explicite, try-with-resources systématique.
 *
 * NB : @Repository sert uniquement à l'enregistrement du bean Spring
 * (injection du DataSource) — n'introduit aucune abstraction sur le SQL.
 */
@Repository
public class StockMovementDaoImpl implements StockMovementDao {

    private static final String SQL_FIND_ALL =
            "SELECT id, created_at, movement_type, quantity, product_id " +
                    "FROM stock_movement " +
                    "ORDER BY created_at DESC";

    private static final String SQL_FIND_ALL_BY_TYPE =
            "SELECT id, created_at, movement_type, quantity, product_id " +
                    "FROM stock_movement " +
                    "WHERE movement_type = ? " +
                    "ORDER BY created_at DESC";

    private static final String SQL_FIND_BY_PRODUCT_ID =
            "SELECT id, created_at, movement_type, quantity, product_id " +
                    "FROM stock_movement " +
                    "WHERE product_id = ? " +
                    "ORDER BY created_at DESC";

    private static final String SQL_INSERT =
            "INSERT INTO stock_movement (id, created_at, movement_type, quantity, product_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SUM_QUANTITY_BY_PRODUCT_AND_TYPE =
            "SELECT COALESCE(SUM(quantity), 0) AS total " +
                    "FROM stock_movement " +
                    "WHERE product_id = ? AND movement_type = ?";

    private final DataSource dataSource;

    public StockMovementDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<StockMovement> findAll(MovementType type) throws SQLException {
        String sql = (type == null) ? SQL_FIND_ALL : SQL_FIND_ALL_BY_TYPE;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (type != null) {
                statement.setString(1, type.name());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                return mapResultSetToList(resultSet);
            }
        }
    }

    @Override
    public List<StockMovement> findByProductId(String productId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_PRODUCT_ID)) {

            statement.setString(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return mapResultSetToList(resultSet);
            }
        }
    }

    @Override
    public StockMovement save(StockMovement movement) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, movement.getId());
            statement.setTimestamp(2, Timestamp.from(movement.getCreatedAt()));
            statement.setString(3, movement.getMovementType().name());
            statement.setInt(4, movement.getQuantity());
            statement.setString(5, movement.getProductId());

            statement.executeUpdate();
            return movement;
        }
    }

    @Override
    public int sumQuantityByProductIdAndType(String productId, MovementType type) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SUM_QUANTITY_BY_PRODUCT_AND_TYPE)) {

            statement.setString(1, productId);
            statement.setString(2, type.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
                return 0;
            }
        }
    }

    /**
     * Mapping manuel ResultSet -> liste de StockMovement.
     */
    private List<StockMovement> mapResultSetToList(ResultSet resultSet) throws SQLException {
        List<StockMovement> movements = new ArrayList<>();
        while (resultSet.next()) {
            movements.add(mapResultSetToStockMovement(resultSet));
        }
        return movements;
    }


    private StockMovement mapResultSetToStockMovement(ResultSet resultSet) throws SQLException {
        StockMovement movement = new StockMovement();
        movement.setId(resultSet.getString("id"));
        movement.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());
        movement.setMovementType(MovementType.valueOf(resultSet.getString("movement_type")));
        movement.setQuantity(resultSet.getInt("quantity"));
        movement.setProductId(resultSet.getString("product_id"));
        return movement;
    }
}
