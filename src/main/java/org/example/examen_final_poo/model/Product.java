package org.example.examen_final_poo.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * POJO correspondant à la classe UML "Product".
 * Mappé sur la table PostgreSQL "product".
 *
 * Attributs strictement issus du diagramme UML :
 * - id            -> product.id            (VARCHAR(36))
 * - name          -> product.name          (VARCHAR(150))
 * - description   -> product.description   (TEXT)
 * - unitPrice     -> product.unit_price    (NUMERIC(10,2))
 */
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal unitPrice;

    public Product() {
    }

    public Product(String id, String name, String description, BigDecimal unitPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}