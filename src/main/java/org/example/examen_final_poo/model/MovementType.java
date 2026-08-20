package org.example.examen_final_poo.model;

/**
 * Enumeration UML "MovementType".
 * Représente le type d'un mouvement de stock : entrée (IN) ou sortie (OUT).
 * Mappée en base PostgreSQL sur la colonne stock_movement.movement_type
 * (VARCHAR(3) + CHECK), pas de type ENUM natif Postgres (mapping JDBC simplifié).
 */
public enum MovementType {
    IN,
    OUT
}