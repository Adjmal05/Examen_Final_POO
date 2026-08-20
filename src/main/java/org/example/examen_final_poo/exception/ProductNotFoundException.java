package org.example.examen_final_poo.exception;


public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productId) {
        super("Produit introuvable avec l'id : " + productId);
    }
}
