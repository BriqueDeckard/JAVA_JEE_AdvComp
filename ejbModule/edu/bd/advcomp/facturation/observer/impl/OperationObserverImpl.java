// File OperationObserverImpl.java - No copyright - 23 avr. 2021
package edu.bd.advcomp.facturation.observer.impl;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.facturation.event.OperationEvent;
import edu.bd.advcomp.facturation.observer.OperationObserver;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * Impl for operation observer.
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class OperationObserverImpl implements OperationObserver {

    @Inject
    FacturationService facturationService;

    /**
     * Constructor for OperationObserverImpl
     *
     */
    public OperationObserverImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.OperationObserver#listenOperationEvents(edu.bd.advcomp.facturation.event.FacturationEvent)
     *
     * @param facturationEvent
     * @throws AdvCompException
     */
    @Override
    public void listenOperationEvents(@Observes OperationEvent facturationEvent) throws AdvCompException {
	System.out.println("************************************************");
	System.out.println("INFO : Facturation event : ".toUpperCase() + facturationEvent.toString());
	Utilisateur user = facturationEvent.getUser();
	String message = facturationEvent.getMessage();

	try {
	    this.facturationService.historiserOperation(user, message);

	} catch (Exception e) {
	    throw new AdvCompException(e);
	}

    }
}
