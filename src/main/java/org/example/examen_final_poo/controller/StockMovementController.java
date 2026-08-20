package org.example.examen_final_poo.controller;

import org.example.examen_final_poo.model.MovementType;

/**
 * DTO technique (hors UML) utilisé uniquement pour désérialiser
 * le corps JSON de POST /stock-movements.
 *
 * Le client fournit l'ID du produit concerné (productId), pas l'objet
 * Product complet : le Service se chargera de charger le Product réel
 * via ProductDao avant de construire un StockMovement valide.
 */
public class StockMovementCreateRequest {

    private String productId;
    private MovementType movementType;
    private int quantity;

    public StockMovementCreateRequest() {
    }

    public StockMovementCreateRequest(String productId, MovementType movementType, int quantity) {
        this.productId = productId;
        this.movementType = movementType;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StockMovementCreateRequest{" +
                "productId='" + productId + '\'' +
                ", movementType=" + movementType +
                ", quantity=" + quantity +
                '}';
    }
}