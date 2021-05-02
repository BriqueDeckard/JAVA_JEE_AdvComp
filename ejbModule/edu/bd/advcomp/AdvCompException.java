// File AdvCompException.java - No copyright - 23 mars 2021
package edu.bd.advcomp;

/**
 * <br/>
 * Exception levée lors d'une erreur dans l'application. <br/>
 * 
 * @author Brique DECKARD
 *
 */
public class AdvCompException extends Exception {

    /**
     * Constructor for AdvCompException
     */
    public AdvCompException() {
    }

    /**
     * Constructor for AdvCompException
     * 
     * @param message
     */
    public AdvCompException(String message) {
	super(message);
    }

    /**
     * Constructor for AdvCompException
     * 
     * @param cause
     */
    public AdvCompException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructor for AdvCompException
     * 
     * @param message
     * @param cause
     */
    public AdvCompException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructor for AdvCompException
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public AdvCompException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
