// File CalculException.java - No copyright - 23 mars 2021
package edu.bd.advcomp.calcul;

/**
 * Exception levée lors d'une erreur dans un calcul
 * 
 * @author Brique DECKARD
 *
 */
public class CalculException extends Exception {

    private String codeErreur = "999";

    /**
     * Constructor for CalculException
     */
    public CalculException() {
	super();
    }

    /**
     * Constructor for CalculException
     * 
     * @param message
     */
    public CalculException(String message) {
	super(message);
    }

    /**
     * Constructor for CalculException
     * 
     * @param cause
     */
    public CalculException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructor for CalculException
     * 
     * @param message
     * @param cause
     */
    public CalculException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructor for CalculException
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CalculException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}
