// File OperationObserverImpl.java - No copyright - 23 avr. 2021
package edu.bd.advcomp.facturation.observer.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.facturation.event.FacturationSignalEvent;
import edu.bd.advcomp.facturation.observer.TimerObserver;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * Impl de l'observateur pour le timer.
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class TimerObserverImpl implements TimerObserver {

    /**
     * facturation service
     */
    @Inject
    FacturationService facturationService;

    /**
     * Constructor for OperationObserverImpl
     *
     */
    public TimerObserverImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.OperationObserver#observesOperation(edu.bd.advcomp.facturation.event.FacturationSignalEvent)
     *
     * @param event
     * @throws AdvcompException
     */
    @Override
    public void observesTimer(@Observes FacturationSignalEvent event) throws AdvcompException {
	Date dateDebut = event.getDateDebut();
	Date dateFin = event.getDateFin();

	try {
	    this.facturationService.facturer(dateDebut, dateFin);
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}

    }

}
