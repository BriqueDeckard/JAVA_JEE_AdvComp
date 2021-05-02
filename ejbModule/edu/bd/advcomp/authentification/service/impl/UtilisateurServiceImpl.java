// File UtilisateurServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.UtilisateurService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class UtilisateurServiceImpl implements UtilisateurService {

    /**
     * Constructor for UtilisateurServiceImpl
     */
    public UtilisateurServiceImpl() {
    }

    @Inject
    private UtilisateurDao utilisateurDao;

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.UtilisateurService#obtenirUtilisateur(java.lang.String)
     * 
     * @param login
     * @return
     * @throws AdvCompException
     */
    @Override
    public Utilisateur obtenirUtilisateur(String login) throws AdvCompException {
	try {
	    return this.utilisateurDao.retrieve(login);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.UtilisateurService#creerUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)
     * 
     * @param utilisateur
     * @return
     * @throws AdvCompException
     */
    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws AdvCompException {
	try {
	    return this.utilisateurDao.create(utilisateur);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return utilisateur;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.UtilisateurService#modifierUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)
     * 
     * @param utilisateur
     * @return
     * @throws AdvCompException
     */
    @Override
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur) throws AdvCompException {
	try {
	    return this.utilisateurDao.update(utilisateur);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return utilisateur;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.UtilisateurService#supprimerUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)
     * 
     * @param utilisateur
     * @throws AdvCompException
     */
    @Override
    public void supprimerUtilisateur(Utilisateur utilisateur) throws AdvCompException {
	try {
	    this.utilisateurDao.delete(utilisateur);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.UtilisateurService#getNewUtilisateur()
     * 
     * @return
     * @throws AdvCompException
     */
    @Override
    public Utilisateur getNewUtilisateur() throws AdvCompException {
	try {
	    return this.utilisateurDao.getNew();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.UtilisateurService#retrouverUtilisateurInactifs()
     * 
     * @return
     *
     * @throws AdvCompException
     */
    @Override
    public List<Utilisateur> retrouverUtilisateurInactifs() throws AdvCompException {
	try {
	    return this.utilisateurDao.retrieveAllInactiveUsers();
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}

    }
}
