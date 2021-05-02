// File OperationObserver.java - No copyright - 23 avr. 2021
package edu.bd.advcomp.facturation.observer;

import javax.ejb.Local;
import javax.enterprise.event.Observes;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.facturation.event.OperationEvent;

/**
 * Contract for operation observer.
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface OperationObserver {

    /**
     * listen Operation Events
     *
     * TODO : Fill method utility
     * 
     * @param facturationEvent
     * @throws AdvCompException
     */
    public void listenOperationEvents(@Observes OperationEvent facturationEvent) throws AdvCompException;
}
