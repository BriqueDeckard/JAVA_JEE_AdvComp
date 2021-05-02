// File ConnexionObserverImpl.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.observer.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.dao.ConnexionAttemptDao;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.admin.entity.impl.ConnexionAttemptImpl;
import edu.bd.advcomp.admin.event.ConnexionEvent;
import edu.bd.advcomp.admin.observer.ConnexionObserver;
import edu.bd.advcomp.admin.service.ConnexionAttemptService;

/**
 * Impl pour l'observation des connexions
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class ConnexionObserverImpl implements ConnexionObserver {

    /**
     * Service pour la maintenance des entités {@link ConnexionAttemptImpl}
     */
    @Inject
    ConnexionAttemptService connexionService;

    /**
     * Constructor for ConnexionObserverImpl
     *
     */
    public ConnexionObserverImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionObserver#observerConnexion(edu.bd.advcomp.admin.event.ConnexionEvent)
     *
     * @param connexionEvent
     * @throws Exception
     * @throws AdvCompException
     */
    @Override
    public void observeConnexion(@Observes ConnexionEvent connexionEvent) throws AdvCompException {
	System.out.println(connexionEvent.toString());
	try {
	    ConnexionAttempt connexion = connexionService.getNewConnexion();
	    connexion.setLogin(connexionEvent.getLogin());
	    connexion.setStatus(connexionEvent.getConnexionStatus());
	    connexionService.creerConnexion(connexion);
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}

    }
}
