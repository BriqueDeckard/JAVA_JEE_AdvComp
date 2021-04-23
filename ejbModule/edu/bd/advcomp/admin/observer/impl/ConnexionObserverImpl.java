// File ConnexionObserverImpl.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.observer.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.admin.dao.ConnexionAttemptDao;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.admin.event.ConnexionEvent;
import edu.bd.advcomp.admin.observer.ConnexionObserver;

/**
 * Impl pour l'observation des connexions
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class ConnexionObserverImpl implements ConnexionObserver {

    /**
     * DAO for connexion
     */
    @Inject
    ConnexionAttemptDao connexionDao;

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
     * @throws AdvcompException
     */
    @Override
    public void observerConnexion(@Observes ConnexionEvent connexionEvent) throws AdvcompException {
	System.out.println(connexionEvent.toString());
	try {
	    ConnexionAttempt connexion = connexionDao.getNew();
	    connexion.setLogin(connexionEvent.getLogin());
	    connexion.setStatus(connexionEvent.getConnexionStatus());
	    connexionDao.create(connexion);
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}

    }
}
