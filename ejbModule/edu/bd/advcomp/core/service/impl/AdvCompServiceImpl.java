// File AdvCompServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.calcul.CalculException;
import edu.bd.advcomp.calcul.service.CalculateurService;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.event.FacturationEvent;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateful
public class AdvCompServiceImpl implements AdvCompService {

    private Utilisateur client = null;

    private Double resultatTemporaire;

    private Double resultatFinal;

    @Inject
    private FacturationService facturationService;

    @Inject
    private CalculateurService calculateurService;

    /**
     * Evènements pour la facturation
     */
    @Inject
    Event<FacturationEvent> facturationEvents;

    public AdvCompServiceImpl(Utilisateur client) {
	this.client = client;
    }

    /**
     * Constructor for AdvCompServiceImpl
     *
     */
    public AdvCompServiceImpl() {
    }

    private boolean clientConnecte() {
	return this.client != null;
    }
    
    private void testConnexion() throws AdvcompException {
	if(!clientConnecte()) {
	    throw new AdvcompException("Non connecté");
	}
    }

    private boolean operationChaineeEnCours() {
	return resultatTemporaire != null;

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
	testConnexion();
	
	try {
	    String descriptionOperation = facteur1 + " " + operateur + " " + facteur2;

	    // CALCUL
	    switch (operateur) {

	    case "+":
		resultatFinal = calculateurService.additionner(facteur1, facteur2);
		break;
	    case "-":
		resultatFinal = calculateurService.soustraire(facteur1, facteur2);
		break;
	    case "*":
		resultatFinal = calculateurService.multiplier(facteur1, facteur2);
		break;
	    case "/":
		resultatFinal = calculateurService.diviser(facteur1, facteur2);
		break;
	    default:
		throw new AdvcompException("Opérateur " + operateur + " non géré.");

	    }

	    // Fire event for facturation
	    facturationEvents.fire(new FacturationEvent(this.client, descriptionOperation));
	    return resultatFinal;
	} catch (CalculException e) {
	    e.printStackTrace();
	    throw new AdvcompException("Echec calcul", e);
	} catch (AdvcompException e) {
	    throw new AdvcompException(e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompService#commencerOperationChainee(java.lang.Double,
     * java.lang.Double, java.lang.String)
     *
     * @param facteur1
     * @param facteur2
     * @param operateur
     * @throws AdvcompException
     */
    @Override
    public void commencerOperationChainee(Double facteur1, Double facteur2, String operateur) throws AdvcompException {
	testConnexion();
	resultatTemporaire = null;
	if (operationChaineeEnCours()) {
	    throw new AdvcompException("Operation chainee déjà en cours");
	}
	resultatTemporaire = faireOperationBasique(facteur1, facteur2, operateur);
    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompService#poursuivreOperationChainee(java.lang.Double,
     * java.lang.String)
     *
     * @param facteur
     * @param operateur
     * @throws AdvcompException
     */
    @Override
    public void poursuivreOperationChainee(Double facteur, String operateur) throws AdvcompException {
	testConnexion();
	if (!operationChaineeEnCours()) {
	    throw new AdvcompException("Operation chainee pas en cours");
	}
	resultatTemporaire = faireOperationBasique(resultatTemporaire, facteur, operateur);
    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#acheverOperationChainee()
     *
     * @return
     * @throws AdvcompException
     */
    @Override
    public Double acheverOperationChainee() throws AdvcompException {
	testConnexion();
	if (!operationChaineeEnCours()) {
	    throw new AdvcompException("Operation chainee pas en cours");
	}
	resultatFinal = resultatTemporaire;

	resultatTemporaire = null;
	return resultatFinal;
    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#seDeconnecter()
     *
     * @throws AdvcompException
     */
    @Override
    public void seDeconnecter() throws AdvcompException {
	testConnexion();
	System.out.println("DECONNEXION");
	this.client = null;

    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#afficherResultatFinal()
     *
     * @return
     * @throws AdvcompException
     */
    @Override
    public String afficherResultatFinal() throws AdvcompException {
	testConnexion();
	if (operationChaineeEnCours()) {
	    throw new AdvcompException("Opperation chaine en cours");
	}
	try {
	    return Double.toString(this.resultatFinal);
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompService#afficherResultatIntermediaire()
     *
     * @return
     * @throws AdvcompException
     */
    @Override
    public String afficherResultatIntermediaire() throws AdvcompException {
	testConnexion();
	if (!operationChaineeEnCours()) {
	    throw new AdvcompException("Opperation chaine pas en cours");
	}
	try {
	    return Double.toString(this.resultatTemporaire);
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}
    }

}
