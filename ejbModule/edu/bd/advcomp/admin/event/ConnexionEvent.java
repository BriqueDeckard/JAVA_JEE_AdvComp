// File ConnexionEvent.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.event;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class ConnexionEvent {

    /**
     * Gets login
     * 
     * @return the login
     */
    public String getLogin() {
	return this.login;
    }

    /**
     * Gets connexionStatus
     * 
     * @return the connexionStatus
     */
    public Boolean getConnexionStatus() {
	return this.connexionStatus;
    }

    /**
     * Constructor for ConnexionEvent
     *
     */
    public ConnexionEvent() {
    }

    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "ConnexionEvent [login=" + this.login + ", connexionStatus=" + this.connexionStatus + "]";
    }

    /**
     * Constructor for ConnexionEvent
     *
     * @param login
     * @param connexionStatus
     */
    public ConnexionEvent(String login, Boolean connexionStatus) {
	super();
	this.login = login;
	this.connexionStatus = connexionStatus;
    }

    private String login;

    private Boolean connexionStatus;
}
