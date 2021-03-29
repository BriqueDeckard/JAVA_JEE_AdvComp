// File AdvCompServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import javax.ejb.Stateful;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.calcul.CalculException;
import edu.bd.advcomp.calcul.service.CalculateurService;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateful
public class AdvCompServiceImpl implements AdvCompService {

    private Utilisateur client;

    @Inject
    private FacturationService facturationService;
    
    @Inject
    private CalculateurService calculateurService;    

    public AdvCompServiceImpl(Utilisateur client) {
	this.client = client;
    }

    /**
     * Constructor for AdvCompServiceImpl
     *
     */
    public AdvCompServiceImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompService#setClient(edu.bd.advcomp.authentification.entity.Utilisateur)
     *
     * @param client
     */
    @Override
    public void setClient(Utilisateur client) {
	this.client = client;

    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompService#faireOperationBasique(java.lang.Double,
     * java.lang.Double, java.lang.String)
     *
     * @param facteur1
     * @param facteur2
     * @param operateur
     * @return
     * @throws AdvcompException
     */
    @Override
    public Double faireOperationBasique(Double facteur1, Double facteur2, String operateur) throws AdvcompException {
	try {
	    Double resultat;
	    String descriptionOperation = facteur1 + " " + operateur + " " + facteur2;

	    // CALCUL
	    switch (operateur) {

	    case "+":
		resultat = calculateurService.additionner(facteur1, facteur2);
		break;
	    case "-":
		resultat = calculateurService.soustraire(facteur1, facteur2);
		break;
	    case "*":
		resultat = calculateurService.multiplier(facteur1, facteur2);
		break;
	    case "/":
		resultat = calculateurService.diviser(facteur1, facteur2);
		break;
	    default:
		throw new AdvcompException("Opérateur " + operateur + " non géré.");

	    }
	    
	    facturationService.historiserOperation(client, descriptionOperation);
	    return resultat;
	} catch (CalculException e) {
	    e.printStackTrace();
	    throw new AdvcompException("Echec calcul", e);
	} catch (AdvcompException e) {
	    throw e;
	    // TODO: handle exception
	}
    }
}
