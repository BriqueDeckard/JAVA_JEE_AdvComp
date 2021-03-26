// File AuthentificationService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.service;

/**
 * Service d'authentification 
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation Local ?
public interface AuthentificationService {
    /**
     * Authentification d'un utilisateur.
     * 
     * @param login
     * @param password
     * @return retourne true si l'authentification a réussi et false sinon
     */
    public Boolean authentifier(String login, String password);
}
