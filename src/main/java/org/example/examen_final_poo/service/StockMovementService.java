package org.example.examen_final_poo.service;

import org.example.examen_final_poo.dao.ProductDao;
import org.example.examen_final_poo.dao.StockMovementDao;
import org.example.examen_final_poo.exception.DataAccessException;
import org.example.examen_final_poo.exception.ProductNotFoundException;
import org.example.examen_final_poo.model.MovementType;
import org.example.examen_final_poo.model.StockMovement;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Service métier pour StockMovement.
 * Ne contient AUCUN SQL : toute la persistance passe par les DAO
 * (ProductDao, StockMovementDao), injectés via le constructeur.
 *
 * Toutes les SQLException levées par la couche dao/ sont interceptées
 * ici et transformées en DataAccessException (runtime), pour que
 * la couche controller/ n'ait pas à gérer une exception checked.
 */
@Service
public class StockMovementService {

    private final StockMovementDao stockMovementDao;
    private final ProductDao productDao;

    public StockMovementService(StockMovementDao stockMovementDao, ProductDao productDao) {
        this.stockMovementDao = stockMovementDao;
        this.productDao = productDao;
    }

    /**
     * GET /stock-movements?type=in|out
     * Si type est null, retourne tous les mouvements.
     */
    public List<StockMovement> getAllMovements(MovementType type) {
        try {
            return stockMovementDao.findAll(type);
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des mouvements de stock", e);
        }
    }

    /**
     * GET /products/{id}/stock-movements
     * Vérifie d'abord que le produit existe.
     */
    public List<StockMovement> getMovementsByProduct(String productId) {
        ensureProductExists(productId);
        try {
            return stockMovementDao.findByProductId(productId);
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des mouvements du produit " + productId, e);
        }
    }

    /**
     * POST /stock-movements
     * Vérifie l'existence du produit, construit un StockMovement complet
     * (id généré, date de création), puis le persiste.
     */
    public StockMovement createMovement(String productId, MovementType movementType, int quantity) {
        ensureProductExists(productId);

        StockMovement movement = new StockMovement(
                UUID.randomUUID().toString(),
                Instant.now(),
                movementType,
                quantity,
                productId
        );

        try {
            return stockMovementDao.save(movement);
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création du mouvement de stock", e);
        }
    }

    /**
     * GET /products/{id}/stock
     * Règle métier (hypothèse validée) : stock = somme des IN - somme des OUT.
     * Calcul délégué en SQL via StockMovementDao.sumQuantityByProductIdAndType.
     */
    public int getCurrentStock(String productId) {
        ensureProductExists(productId);
        try {
            int totalIn = stockMovementDao.sumQuantityByProductIdAndType(productId, MovementType.IN);
            int totalOut = stockMovementDao.sumQuantityByProductIdAndType(productId, MovementType.OUT);
            return totalIn - totalOut;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors du calcul du stock du produit " + productId, e);
        }
    }

    private void ensureProductExists(String productId) {
        try {
            productDao.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la vérification du produit " + productId, e);
        }
    }
}