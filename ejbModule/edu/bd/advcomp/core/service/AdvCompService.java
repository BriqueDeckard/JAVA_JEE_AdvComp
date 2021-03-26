// File AdvCompService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * Service pour les client utilisateur d'AdvComp
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation @Local ?
public interface AdvCompService {
    public void setClient(Utilisateur client);

    /**
     * Réalise un calcul basique en utilisant AdvComp
     * 
     * @param facteur1
     * @param facteur2
     * @param operateur
     * @return
     * @throws AdvcompException
     */
    public Double faireOperationBasique(Double facteur1, Double facteur2, String operateur) throws AdvcompException;
}
