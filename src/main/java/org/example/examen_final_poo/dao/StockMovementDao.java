package org.example.examen_final_poo.dao;

import org.example.examen_final_poo.model.MovementType;
import org.example.examen_final_poo.model.StockMovement;

import java.sql.SQLException;
import java.util.List;


public interface StockMovementDao {

    List<StockMovement> findAll(MovementType type) throws SQLException;


    List<StockMovement> findByProductId(String productId) throws SQLException;


    StockMovement save(StockMovement movement) throws SQLException;


    int sumQuantityByProductIdAndType(String productId, MovementType type) throws SQLException;
}
