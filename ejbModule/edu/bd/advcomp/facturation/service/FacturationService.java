// File FacturationService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.event.OperationEvent;
import edu.bd.advcomp.facturation.event.FacturationSignalEvent;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface FacturationService {

    public static String JNDI = "java:global/AdvCompEjb/FacturationServiceImpl!edu.bd.advcomp.facturation.service.FacturationService";

    /**
     * Enregistre l'opération réaliser par le client
     * 
     * @param utilisateur
     * @param descriptionOperation
     */
    public void historiserOperation(Utilisateur client, String descriptionOperation) throws AdvCompException;

    /**
     * Génère les factures pour tous les clients ayant des opérations historisées
     * non facturées sur la période
     * 
     * @param dateDebut
     * @param datefin
     */
    public void facturer(Date dateDebut, Date datefin) throws AdvCompException;

    /**
     * Enregistrement du règlement d'une facture
     * 
     * @param numeroFacture
     * @param rib
     */
    public void reglerFacture(String numeroFacture, String rib) throws AdvCompException;

    /**
     * Retourne une facture d'après son numéro
     * 
     * @param numeroFacture
     * @return
     */
    public Facture obtenirFacture(String numeroFacture);

    /**
     * retrieveAll
     *
     * TODO : Fill method utility
     * @return
     * @throws AdvCompException 
     */
    public List<Facture> retrieveAll() throws AdvCompException;

}
