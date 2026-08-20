package org.example.examen_final_poo.controller;

import org.example.examen_final_poo.model.MovementType;
import org.example.examen_final_poo.model.StockMovement;
import org.example.examen_final_poo.service.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST exposant strictement les 4 endpoints définis dans
 * le diagramme UML :
 *   GET  /stock-movements?type=in|out
 *   GET  /products/{id}/stock-movements
 *   POST /stock-movements
 *   GET  /products/{id}/stock
 *
 * Ne contient aucune logique métier ni SQL : délègue tout à
 * StockMovementService.
 */
@RestController
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    /**
     * GET /stock-movements?type=in|out
     * Le paramètre "type" est optionnel. Fourni en minuscules dans
     * l'énoncé (in|out), converti en majuscules avant mapping vers
     * l'enum MovementType (IN|OUT).
     */
    @GetMapping("/stock-movements")
    public ResponseEntity<List<StockMovement>> getAllMovements(
            @RequestParam(required = false) String type) {

        MovementType movementType = (type != null)
                ? MovementType.valueOf(type.toUpperCase())
                : null;

        List<StockMovement> movements = stockMovementService.getAllMovements(movementType);
        return ResponseEntity.ok(movements);
    }

    /**
     * GET /products/{id}/stock-movements
     */
    @GetMapping("/products/{id}/stock-movements")
    public ResponseEntity<List<StockMovement>> getMovementsByProduct(
            @PathVariable String id) {

        List<StockMovement> movements = stockMovementService.getMovementsByProduct(id);
        return ResponseEntity.ok(movements);
    }

    /**
     * POST /stock-movements
     * Reçoit un StockMovementCreateRequest (DTO technique, hors UML),
     * délègue au Service la construction et la persistance du
     * StockMovement complet.
     */
    @PostMapping("/stock-movements")
    public ResponseEntity<StockMovement> createMovement(
            @RequestBody StockMovementCreateRequest request) {

        StockMovement created = stockMovementService.createMovement(
                request.getProductId(),
                request.getMovementType(),
                request.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /products/{id}/stock
     * Retourne le stock actuel calculé (somme des IN - somme des OUT).
     */
    @GetMapping("/products/{id}/stock")
    public ResponseEntity<Integer> getCurrentStock(@PathVariable String id) {
        int stock = stockMovementService.getCurrentStock(id);
        return ResponseEntity.ok(stock);
    }
}