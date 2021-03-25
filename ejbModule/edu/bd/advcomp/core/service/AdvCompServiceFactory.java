// File AdvCompServiceFactory.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * Factory pour la création de services
 * 
 * @author Brique DECKARD
 *
 */
@FunctionalInterface
public interface AdvCompServiceFactory {

    /**
     * Do createAdvCompService
     * Creation du service
     * @param client
     * @return
     */
    public AdvCompService createAdvCompService(Utilisateur client);
}
