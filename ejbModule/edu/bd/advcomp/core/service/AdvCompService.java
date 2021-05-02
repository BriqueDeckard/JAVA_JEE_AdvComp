// File AdvCompService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import edu.bd.advcomp.AdvCompException;
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
     * @throws AdvCompException
     */
    public Double faireOperationBasique(Double facteur1, Double facteur2, String operateur) throws AdvCompException;

    /**
     * commencer Operation Chainee
     *
     * Débute un calcul chainé
     * 
     * @param facteur1
     * @param facteur2
     * @param operateur
     * @throws AdvCompException
     */
    public void commencerOperationChainee(Double facteur1, Double facteur2, String operateur) throws AdvCompException;

    /**
     * poursuivre Operation Chainee
     *
     * Operation intermediraire de l'operation chainee.
     * 
     * @param facteur
     * @param operateur
     * @throws AdvCompException
     */
    public void poursuivreOperationChainee(Double facteur, String operateur) throws AdvCompException;

    /**
     * achever Operation Chainee
     *
     * Ordre d'interuption de l'operation chaînée.
     * 
     * @return
     * @throws AdvCompException
     */
    public Double acheverOperationChainee() throws AdvCompException;

    /**
     * afficher resultat
     * 
     * @return
     * @throws AdvCompException
     */
    public String afficherResultatFinal() throws AdvCompException;

    /**
     * afficher Resultat Intermediaire
     *
     * @return
     * @throws AdvCompException
     */
    public String afficherResultatIntermediaire() throws AdvCompException;

    /**
     * se Deconnecter
     *
     * TODO : Fill method utility
     * 
     * @throws AdvCompException
     */
    public void seDeconnecter() throws AdvCompException;

}
