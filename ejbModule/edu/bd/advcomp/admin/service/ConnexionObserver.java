// File ConnexionObserver.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.service;

import javax.ejb.Local;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.admin.event.ConnexionEvent;

/**
 * Contracts for ConnexionObserver
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface ConnexionObserver {

    /**
     * observer Connexion
     *
     * @param connexionEvent
     * @throws AdvcompException
     */
    public void observerConnexion(ConnexionEvent connexionEvent) throws AdvcompException;
}
