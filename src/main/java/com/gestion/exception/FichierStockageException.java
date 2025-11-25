package com.gestion.exception;

public class FichierStockageException extends RuntimeException {

    public FichierStockageException (String message) {
        super(message);
    }

    public FichierStockageException (String message, Throwable cause) {
        super(message, cause);
    }
}
