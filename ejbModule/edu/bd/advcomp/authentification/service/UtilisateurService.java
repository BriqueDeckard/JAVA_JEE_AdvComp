// File UtilisateurService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.service;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * Service dédié à la gestion des utilisateurs
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation local ?
public interface UtilisateurService {
    /**
     * Retourne un utilisateur d'après son login et null si aucun utilisateur avec
     * le login n'existe
     * 
     * @param login
     * @return
     */
    public Utilisateur obtenirUtilisateur(String login) throws AdvcompException;

    /**
     * Crée un nouvel utilisateur
     * 
     * @param utilisateur
     * @return
     */
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws AdvcompException;

    /**
     * Met à jour l'utilisateur
     * 
     * @param utilisateur
     * @return
     */
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur) throws AdvcompException;

    /**
     * Supprime l'utilisateur
     * 
     * @param utilisateur
     */
    public void supprimerUtilisateur(Utilisateur utilisateur) throws AdvcompException;

    /**
     * retourne une nouvelle instance d'utilisateur
     * 
     * @return
     */
    public Utilisateur getNewUtilisateur() throws AdvcompException;
}
