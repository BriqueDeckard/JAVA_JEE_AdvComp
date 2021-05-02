// File ConnexionAttemptService.java - No copyright - 30 avr. 2021
package edu.bd.advcomp.admin.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface ConnexionAttemptService {
    String JNDI = "java:global/advCompEjb/ConnexionAttemptServiceImpl!edu.bd.advcomp.admin.service.ConnexionAttempService";

    /**
     * obtenir Connexion Attempt
     *
     * @param login
     * @return
     * @throws AdvCompException
     */
    public ConnexionAttempt obtenirConnexionAttempt(String numero) throws AdvCompException;

    /**
     * creer Connexion
     *
     * @param connexion
     * @return
     * @throws AdvCompException
     */
    public ConnexionAttempt creerConnexion(ConnexionAttempt connexion) throws AdvCompException;

    /**
     * modifier Connexion
     *
     * @param connexion
     * @return
     * @throws AdvCompException
     */
    public ConnexionAttempt modifierConnexion(ConnexionAttempt connexion) throws AdvCompException;

    /**
     * supprimer Connexion
     *
     * @param connexion
     * @throws AdvCompException
     */
    public void supprimerConnexion(ConnexionAttempt connexion) throws AdvCompException;

    /**
     * get New Connexion
     *
     * @return
     * @throws AdvCompException
     */
    public ConnexionAttempt getNewConnexion() throws AdvCompException;

    /**
     * retrouver Toutes Les Connexions
     *
     * @return
     * @throws AdvCompException
     */
    public List<ConnexionAttempt> retrouverToutesLesConnexions() throws AdvCompException;

    /**
     * retrouver Toutes Les Connexions Entre Deux Dates
     *
     * @param debut
     * @param fin
     * @return
     * @throws AdvCompException
     */
    public List<ConnexionAttempt> retrouverToutesLesConnexionsEntreDeuxDates(Date debut, Date fin)
	    throws AdvCompException;

}
