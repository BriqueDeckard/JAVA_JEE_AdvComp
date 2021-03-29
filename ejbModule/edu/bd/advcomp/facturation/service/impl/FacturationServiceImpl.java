// File FacturationServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.service.impl;

import java.util.Date;

import javax.ejb.Stateless;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * @author Brique DECKARD
 *
 */
@Stateless
public class FacturationServiceImpl implements FacturationService {    
    
    /**
     * Constructor for FacturationServiceImpl
     *
     */
    public FacturationServiceImpl() {
    }


    /**
     * See @see edu.bd.advcomp.facturation.service.FacturationService#historiserOperation(edu.bd.advcomp.authentification.entity.Utilisateur, java.lang.String)
     *
     * @param client
     * @param descriptionOperation
     * @throws AdvcompException
     */
    @Override
    public void historiserOperation(Utilisateur client, String descriptionOperation) throws AdvcompException {
	if(client == null) {
	    throw new AdvcompException(this.getClass().getName() + " Client nul !");
	}
	if(descriptionOperation == null) {
	    throw new AdvcompException("Description nule.");
	}
	System.out.println("Historisation " + client.getNom() + " ==> " + descriptionOperation);
	
    }

    /**
     * See @see edu.bd.advcomp.facturation.service.FacturationService#facturer(java.util.Date, java.util.Date)
     *
     * @param dateDebut
     * @param datefin
     * @throws AdvcompException
     */
    @Override
    public void facturer(Date dateDebut, Date datefin) throws AdvcompException {
	// TODO Fill method utility.
	
    }

    /**
     * See @see edu.bd.advcomp.facturation.service.FacturationService#reglerFacture(java.lang.String, java.lang.String)
     *
     * @param numeroFacture
     * @param rib
     * @throws AdvcompException
     */
    @Override
    public void reglerFacture(String numeroFacture, String rib) throws AdvcompException {
	// TODO Fill method utility.
	
    }

    /**
     * See @see edu.bd.advcomp.facturation.service.FacturationService#obtenirFacture(java.lang.String)
     *
     * @param numeroFacture
     * @return
     */
    @Override
    public Facture obtenirFacture(String numeroFacture) {
	// TODO Fill method utility.
	return null;
    }
}
