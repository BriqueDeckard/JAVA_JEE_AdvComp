// File UtilisateurService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.service;

import java.util.List;

import javax.ejb.Local;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * Service dédié à la gestion des utilisateurs
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface UtilisateurService {
    String JNDI = "java:global/AdvCompEjb/UtilisateurServiceImpl!edu.bd.advcomp.authentification.service.UtilisateurService";

    /**
     * Retourne un utilisateur d'après son login et null si aucun utilisateur avec
     * le login n'existe
     * 
     * @param login
     * @return
     */
    public Utilisateur obtenirUtilisateur(String login) throws AdvCompException;

    /**
     * Crée un nouvel utilisateur
     * 
     * @param utilisateur
     * @return
     */
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws AdvCompException;

    /**
     * Met à jour l'utilisateur
     * 
     * @param utilisateur
     * @return
     */
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur) throws AdvCompException;

    /**
     * Supprime l'utilisateur
     * 
     * @param utilisateur
     */
    public void supprimerUtilisateur(Utilisateur utilisateur) throws AdvCompException;

    /**
     * retourne une nouvelle instance d'utilisateur
     * 
     * @return
     */
    public Utilisateur getNewUtilisateur() throws AdvCompException;

    /**
     * retrouver Utilisateur Inactifs
     *
     * TODO : Fill method utility
     * @return
     * @throws AdvCompException
     */
    List<Utilisateur> retrouverUtilisateurInactifs() throws AdvCompException;
}
