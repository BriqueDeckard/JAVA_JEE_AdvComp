// File AdvCompServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.calcul.CalculException;
import edu.bd.advcomp.calcul.service.CalculateurService;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.event.OperationEvent;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateful
//@Interceptors({ edu.bd.advcomp.admin.interceptors.ConnexionInterceptorImpl.class })
public class AdvCompServiceImpl implements AdvCompService {

    /**
     * Client courant
     */
    private Utilisateur client = null;

    /**
     * Resultat temporaire des calculs
     */
    private Double resultatTemporaire;

    /**
     * Resultat final du calcul
     */
    private Double resultatFinal;

    /**
     * Service de calcul
     */
    @Inject
    private CalculateurService calculateurService;

    /**
     * Evènements pour la facturation
     */
    @Inject
    Event<OperationEvent> facturationEvents;

    /**
     * Constructor for AdvCompServiceImpl
     *
     * @param client
     */
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
     * is Client Connecte
     *
     * @return
     */
    private boolean isClientConnecte() {
	return this.client != null;
    }

    /**
     * test Connexion
     *
     * @throws AdvCompException
     */
    private void testConnexion() throws AdvCompException {
	if (!isClientConnecte()) {
	    throw new AdvCompException("Non connecté");
	}
    }

    /**
     * is Operation Chainee En Cours
     *
     * @return
     */
    private boolean isOperationChaineeEnCours() {
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
     * @throws AdvCompException
     */
    @Override
    public Double faireOperationBasique(Double facteur1, Double facteur2, String operateur) throws AdvCompException {
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
		throw new AdvCompException("Opérateur " + operateur + " non géré.");

	    }

	    // Fire event for facturation
	    facturationEvents.fire(new OperationEvent(this.client, descriptionOperation));
	    return resultatFinal;
	} catch (CalculException e) {
	    e.printStackTrace();
	    throw new AdvCompException(e);
	} catch (AdvCompException e) {
	    throw new AdvCompException(e);
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
     * @throws AdvCompException
     */
    @Override
    public void commencerOperationChainee(Double facteur1, Double facteur2, String operateur) throws AdvCompException {
	testConnexion();
	resultatTemporaire = null;
	if (isOperationChaineeEnCours()) {
	    throw new AdvCompException("Operation chainee déjà en cours");
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
     * @throws AdvCompException
     */
    @Override
    public void poursuivreOperationChainee(Double facteur, String operateur) throws AdvCompException {
	testConnexion();
	if (!isOperationChaineeEnCours()) {
	    throw new AdvCompException("Operation chainee pas en cours");
	}
	resultatTemporaire = faireOperationBasique(resultatTemporaire, facteur, operateur);
    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#acheverOperationChainee()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public Double acheverOperationChainee() throws AdvCompException {
	testConnexion();
	if (!isOperationChaineeEnCours()) {
	    throw new AdvCompException("Operation chainee pas en cours");
	}
	resultatFinal = resultatTemporaire;

	resultatTemporaire = null;
	return resultatFinal;
    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#seDeconnecter()
     *
     * @throws AdvCompException
     */
    @Override
    public void seDeconnecter() throws AdvCompException {
	testConnexion();
	System.out.println("DECONNEXION");
	this.client = null;

    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#afficherResultatFinal()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public String afficherResultatFinal() throws AdvCompException {
	testConnexion();
	if (isOperationChaineeEnCours()) {
	    throw new AdvCompException("Opperation chaine en cours");
	}
	try {
	    return Double.toString(this.resultatFinal);
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompService#afficherResultatIntermediaire()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public String afficherResultatIntermediaire() throws AdvCompException {
	testConnexion();
	if (!isOperationChaineeEnCours()) {
	    throw new AdvCompException("Opperation chaine pas en cours");
	}
	try {
	    return Double.toString(this.resultatTemporaire);
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }

}
