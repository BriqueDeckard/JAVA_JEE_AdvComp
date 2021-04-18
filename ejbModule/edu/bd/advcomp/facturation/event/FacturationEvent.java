// File FacturationEvent.java - No copyright - 10 avr. 2021
package edu.bd.advcomp.facturation.event;

import java.io.Serializable;

import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class FacturationEvent implements Serializable {

    private Utilisateur user;

    /**
     * TODO : Fill field utility
     */
    private static final long serialVersionUID = -9067343104845152288L;
    private String message;

    /**
     * Constructor for FacturationEvent
     *
     */
    public FacturationEvent(Utilisateur user, String message) {
	super();
	this.message = message;
	this.user = user;
    }

    /**
     * Gets user
     * 
     * @return the user
     */
    public Utilisateur getUser() {
	return this.user;
    }

    /**
     * Gets message
     * 
     * @return the message
     */
    public String getMessage() {
	return this.message;
    }

    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "FacturationEvent [user=" + this.user + ", message=" + this.message + "]";
    }
}
