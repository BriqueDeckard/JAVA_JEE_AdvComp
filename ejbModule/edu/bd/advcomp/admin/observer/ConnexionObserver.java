// File ConnexionObserver.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.observer;

import javax.ejb.Local;

import edu.bd.advcomp.AdvCompException;
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
     * @throws AdvCompException
     */
    public void observeConnexion(ConnexionEvent connexionEvent) throws AdvCompException;
}
