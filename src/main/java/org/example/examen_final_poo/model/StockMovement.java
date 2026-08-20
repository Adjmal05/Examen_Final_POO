package org.example.examen_final_poo.model;

import java.time.Instant;
import java.util.Objects;

/**
 * POJO correspondant à la classe UML "StockMovement".
 * Mappé sur la table PostgreSQL "stock_movement".
 *
 * Attributs strictement issus du diagramme UML :
 * - id             -> stock_movement.id             (VARCHAR(36))
 * - createdAt      -> stock_movement.created_at      (TIMESTAMPTZ)
 * - movementType   -> stock_movement.movement_type   (VARCHAR(3))
 * - quantity       -> stock_movement.quantity        (INTEGER)
 *
 * Relation UML "concern" (StockMovement 0..* -> Product 1..1) :
 * navigabilité StockMovement -> Product, traduite ici par l'identifiant
 * du produit (Option 2 - décision révisée), reflétant directement
 * la colonne FK stock_movement.product_id en base.
 */
public class StockMovement {

    private String id;
    private Instant createdAt;
    private MovementType movementType;
    private int quantity;
    private String productId;

    public StockMovement() {
    }

    public StockMovement(String id, Instant createdAt, MovementType movementType,
                         int quantity, String productId) {
        this.id = id;
        this.createdAt = createdAt;
        this.movementType = movementType;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockMovement)) return false;
        StockMovement that = (StockMovement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", movementType=" + movementType +
                ", quantity=" + quantity +
                ", productId='" + productId + '\'' +
                '}';
    }
}