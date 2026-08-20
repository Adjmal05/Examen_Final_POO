package org.example.examen_final_poo.dao;

import org.example.examen_final_poo.model.Product;

import java.sql.SQLException;
import java.util.Optional;


public interface ProductDao {


    Optional<Product> findById(String id) throws SQLException;
}
