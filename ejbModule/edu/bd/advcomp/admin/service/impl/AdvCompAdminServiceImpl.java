// File AdvCompAdminServiceImpl.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.admin.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.admin.dao.ConnexionAttemptDao;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * Service d'admin pour AdvComp
 * 
 * @author Brique DECKARD
 *
 */
@Stateful
public class AdvCompAdminServiceImpl implements AdvCompAdminService {

    /**
     * Service utilisateurs
     */
    @Inject
    UtilisateurService userService;

    /**
     * Service de facturation
     */
    @Inject
    FacturationService facturationService;

    /**
     * Dao des factures
     */
    @Inject
    FactureDao factureDao;

    /**
     * Dao des tentatives de connexion
     */
    @Inject
    ConnexionAttemptDao connexionAttemptDao;

    /**
     * is Authenticated
     *
     * @return
     */
    private boolean isAuthenticated() {
	return this.admin != null;
    }

    /**
     * test Authentication
     *
     * @throws AdvcompException
     */
    private void testAuthentication() throws AdvcompException {
	if (!isAuthenticated()) {
	    throw new AdvcompException("Non authentifié");
	}
    }

    /**
     * Constructor for AdvCompAdminServiceImpl
     *
     */
    public AdvCompAdminServiceImpl() {
    }

    /**
     * Administrateur
     */
    Utilisateur admin = null;

    /**
     * See @see edu.bd.advcomp.admin.service.AdvCompAdminService#setAdmin()
     *
     */
    @Override
    public void setAdmin(Utilisateur admin) {
	this.admin = admin;

    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#validerCompteUtilisateur(java.lang.String)
     *
     * @param utilisateurLogin
     */
    @Override
    public void validerCompteUtilisateur(String utilisateurLogin) throws AdvcompException {
	try {
	    System.out.println("Validation utilisateur : " + utilisateurLogin);
	    Utilisateur utilisateurAValider = userService.obtenirUtilisateur(utilisateurLogin);
	    utilisateurAValider.setIsActive(true);
	    userService.modifierUtilisateur(utilisateurAValider);

	} catch (AdvcompException e) {
	    throw new AdvcompException(e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#supprimerCompteUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)
     *
     * @param utilisateur
     * @throws AdvcompException
     */
    @Override
    public void supprimerCompteUtilisateur(Utilisateur utilisateur) throws AdvcompException {
	try {
	    userService.supprimerUtilisateur(utilisateur);
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}

    }

    /**
     * See @see edu.bd.advcomp.admin.service.AdvCompAdminService#genererFacture()
     *
     */
    @Override
    public void genererFacture(Date dateDebut, Date dateFin) throws AdvcompException {
	this.facturationService.facturer(dateDebut, dateFin);
    }

    /**
     * See @see edu.bd.advcomp.admin.service.AdvCompAdminService#consulterFacture()
     *
     */
    @Override
    public String consulterFacture(String numero) {
	Facture facture = this.facturationService.obtenirFacture(numero);
	return facture.toString();

    }

    /**
     * See @see edu.bd.advcomp.admin.service.AdvCompAdminService#seDeconnecter()
     *
     */
    @Override
    public void seDeconnecter() {
	admin = null;

    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#findUser(java.lang.String)
     *
     * @param login
     * @return
     * @throws AdvcompException
     */
    @Override
    public Utilisateur findUser(String login) throws AdvcompException {
	Utilisateur user = userService.obtenirUtilisateur(login);
	System.out.println("Utilisateur : " + user);
	return user;
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#desactiverCompteUtilisateur(java.lang.String)
     *
     * @param utilisateurLogin
     * @throws AdvcompException
     */
    @Override
    public void desactiverCompteUtilisateur(String utilisateurLogin) throws AdvcompException {
	try {
	    System.out.println("Validation utilisateur : " + utilisateurLogin);
	    Utilisateur utilisateurAValider = userService.obtenirUtilisateur(utilisateurLogin);
	    utilisateurAValider.setIsActive(false);
	    userService.modifierUtilisateur(utilisateurAValider);

	} catch (AdvcompException e) {
	    throw new AdvcompException(e);
	}

    }

    /**
     * See @see edu.bd.advcomp.admin.service.AdvCompAdminService#getAllTheFacture()
     *
     * @return
     * @throws Exception
     * @throws AdvcompException
     */
    @Override
    public List<Facture> getAllTheFacture() throws AdvcompException, Exception {
	return factureDao.retrieveAll();
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#retrouverUtilisateursInactifs()
     *
     * @return
     * @throws AdvcompException
     */
    @Override
    public List<Utilisateur> retrouverUtilisateursInactifs() throws AdvcompException {
	return this.userService.retrouverUtilisateurInactifs();
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#getAllTheConnexionAttempt()
     *
     * @return
     * @throws AdvcompException
     */
    @Override
    public List<ConnexionAttempt> getAllTheConnexionAttempt() throws AdvcompException {
	try {
	    return this.connexionAttemptDao.retrieveAll();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.service.AdvCompAdminService#getNumberOfConnexionFor(java.util.Date)
     *
     * @param date
     * @return
     */
    @Override
    public Integer getNumberOfConnexionFor(Date bDate, Date eDate) throws AdvcompException {
	{
	    // TODO Fill method utility.
	    try {
		return this.connexionAttemptDao.retrieveAllBetween(bDate, eDate).size();
	    } catch (Exception e) {
		throw new AdvcompException(e);
	    }
	}
    }

}
