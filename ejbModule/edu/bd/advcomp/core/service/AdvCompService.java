// File AdvCompService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * Service pour les client utilisateur d'AdvComp
 * 
 * @author Brique DECKARD
 *
 */
@Remote
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

    /**
     * commencerOperationChainee
     *
     * Débute un calcul chainé
     * 
     * @param facteur1
     * @param facteur2
     * @param operateur
     * @throws AdvcompException
     */
    public void commencerOperationChainee(Double facteur1, Double facteur2, String operateur) throws AdvcompException;

    /**
     * poursuivreOperationChainee
     *
     * Operation intermediraire de l'operation chainee.
     * 
     * @param facteur
     * @param operateur
     * @throws AdvcompException
     */
    public void poursuivreOperationChainee(Double facteur, String operateur) throws AdvcompException;

    /**
     * acheverOperationChainee
     *
     * Ordre d'interuption de l'operation chaînée.
     * 
     * @return
     * @throws AdvcompException
     */
    public Double acheverOperationChainee() throws AdvcompException;

}
