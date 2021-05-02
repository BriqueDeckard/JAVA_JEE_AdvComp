// File ConnexionAttemptServiceImpl.java - No copyright - 30 avr. 2021
package edu.bd.advcomp.admin.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.dao.ConnexionAttemptDao;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.admin.entity.impl.ConnexionAttemptImpl;
import edu.bd.advcomp.admin.service.ConnexionAttemptService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class ConnexionAttemptServiceImpl implements ConnexionAttemptService {

    /**
     * Constructor for ConnexionAttemptServiceImpl
     *
     */
    public ConnexionAttemptServiceImpl() {
    }

    /**
     * Dao pour la maintenance des entités {@link ConnexionAttempt}
     */
    @Inject
    ConnexionAttemptDao connexionDao;

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#obtenirConnexionAttempt(java.lang.String)
     *
     * @param login
     * @return
     * @throws AdvCompException
     */
    @Override
    public ConnexionAttempt obtenirConnexionAttempt(String numero) throws AdvCompException {
	try {
	    ConnexionAttempt connexion = this.connexionDao.retrieve(numero);
	    System.out.println("CONNEXION : " + connexion.toString());
	    return connexion;
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#creerConnexion(edu.bd.advcomp.admin.entity.ConnexionAttempt)
     *
     * @param connexion
     * @return
     * @throws AdvCompException
     */
    @Override
    public ConnexionAttempt creerConnexion(ConnexionAttempt connexion) throws AdvCompException {
	try {
	    System.out.println("CONNEXION : " + connexion.toString());
	    return this.connexionDao.create(connexion);
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#modifierConnexion(edu.bd.advcomp.admin.entity.ConnexionAttempt)
     *
     * @param connexion
     * @return
     * @throws AdvCompException
     */
    @Override
    public ConnexionAttempt modifierConnexion(ConnexionAttempt connexion) throws AdvCompException {
	try {
	    return this.connexionDao.update(connexion);
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#supprimerConnexion(edu.bd.advcomp.admin.entity.ConnexionAttempt)
     *
     * @param connexion
     * @throws AdvCompException
     */
    @Override
    public void supprimerConnexion(ConnexionAttempt connexion) throws AdvCompException {
	try {
	    this.connexionDao.delete(connexion);
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#getNewConnexion()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public ConnexionAttempt getNewConnexion() throws AdvCompException {
	return new ConnexionAttemptImpl();
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#retrouverToutesLesConnexions()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public List<ConnexionAttempt> retrouverToutesLesConnexions() throws AdvCompException {
	try {
	    List<ConnexionAttempt> connexions = this.connexionDao.retrieveAll();
	    for (ConnexionAttempt connexionAttempt : connexions) {
		System.out.println("Con. : " + connexionAttempt);
	    }
	    return connexions;
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.ConnexionAttemptService#retrouverToutesLesConnexionsEntreDeuxDates(java.util.Date,
     * java.util.Date)
     *
     * @param debut
     * @param fin
     * @return
     * @throws AdvCompException
     */
    @Override
    public List<ConnexionAttempt> retrouverToutesLesConnexionsEntreDeuxDates(Date debut, Date fin)
	    throws AdvCompException {
	try {
	    List<ConnexionAttempt> connexions = this.connexionDao.retrieveAllBetween(debut, fin);
	    
	    for (ConnexionAttempt connexionAttempt : connexions) {
		System.out.println("Con. : " + connexionAttempt);
	    }
	    return connexions;
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }
}
