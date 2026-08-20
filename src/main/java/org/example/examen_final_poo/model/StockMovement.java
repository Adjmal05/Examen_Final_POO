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
 * navigabilité StockMovement -> Product, traduite par une référence
 * objet complète (Option 1 validée), reconstruite par le DAO via
 * jointure/second SELECT lors du mapping ResultSet -> POJO.
 * La FK product_id (VARCHAR(36)) reste un détail d'implémentation SQL,
 * non exposé ici en tant qu'attribut métier.
 */
public class StockMovement {

    private String id;
    private Instant createdAt;
    private MovementType movementType;
    private int quantity;
    private Product product;

    public StockMovement() {
    }

    public StockMovement(String id, Instant createdAt, MovementType movementType,
                         int quantity, Product product) {
        this.id = id;
        this.createdAt = createdAt;
        this.movementType = movementType;
        this.quantity = quantity;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
                ", product=" + product +
                '}';
    }
}