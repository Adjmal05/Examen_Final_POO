package org.example.examen_final_poo.exception;

/**
 * Exception runtime technique levée lorsqu'une opération JDBC échoue
 * (SQLException interceptée dans la couche service).
 *
 * Permet aux Controllers de ne pas gérer une checked exception SQL :
 * la couche DAO déclare "throws SQLException" (transparence sur l'origine
 * technique de l'échec), mais le Service catche systématiquement et
 * re-lève cette exception runtime dédiée, que GlobalExceptionHandler
 * pourra mapper vers un code HTTP 500.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}