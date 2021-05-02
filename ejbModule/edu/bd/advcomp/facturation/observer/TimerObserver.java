// File OperationObserver.java - No copyright - 23 avr. 2021
package edu.bd.advcomp.facturation.observer;

import javax.ejb.Local;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.facturation.event.FacturationSignalEvent;

/**
 * Observer pour le timer.
 * @author Brique DECKARD
 *
 */
@Local
public interface TimerObserver {
    
    /**
     * observes Operation from advCompService
     *
     * @param event
     * @throws AdvCompException 
     */
    public void observesTimer(FacturationSignalEvent event) throws AdvCompException;
}
