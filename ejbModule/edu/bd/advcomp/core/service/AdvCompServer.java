// File AdvCompServer.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import edu.bd.advcomp.AdvcompException;

/**
 * Serveur fournisseur de service {@link AdvCompService} aux clients
 * authentifiés.
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation @Remote ?
public interface AdvCompServer {
    /**
     * Traite la demande de connexion d'un client et lui retourne un
     * {@link AdvCompService} si l'authentification a abouti dans les autres cas une
     * {@link AdvcompException} est levée.
     * 
     * @param login
     * @param password
     * @return
     * @throws AdvcompException
     */
    public AdvCompService connexion(String login, String password) throws AdvcompException;
}
