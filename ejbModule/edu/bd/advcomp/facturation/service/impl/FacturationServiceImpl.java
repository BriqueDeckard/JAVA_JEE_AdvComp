// File FacturationServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class FacturationServiceImpl implements FacturationService {

    /**
     * Dao pour la persistance des entités {@link HistoriqueOperation}
     */
    @Inject
    HistoriqueOperationDao historiqueOperationDao;

    /**
     * Dao pour la persistance des entités {@link Facture}
     */
    @Inject
    FactureDao factureDao;

    /**
     * Constructor for FacturationServiceImpl
     *
     */
    public FacturationServiceImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationService#historiserOperation(edu.bd.advcomp.authentification.entity.Utilisateur,
     * java.lang.String)
     *
     * @param client
     * @param descriptionOperation
     * @throws AdvcompException
     */
    @Override
    public void historiserOperation(Utilisateur client, String descriptionOperation) throws AdvcompException {
	checkClient(client);
	System.out.println(this.getClass().getName() + " - CLIENT OK");
	checkDescription(descriptionOperation);
	System.out.println(this.getClass().getName() + " - DESCRIPTION OK");

	HistoriqueOperation historiqueOperation;

	try {
	    historiqueOperation = historiqueOperationDao.getNew();
	    historiqueOperation.setDate(new Date());
	    historiqueOperation.setUtilisateur(client);
	    historiqueOperation.setDescription(descriptionOperation);
	    System.out.println(this.getClass().getName() + " - Historique : " + historiqueOperation.toString());
	} catch (Exception e) {
	    System.out.println(this.getClass().getName() + " - GET NEW FAILED");
	    e.printStackTrace();
	    throw new AdvcompException(this.getClass().getName() + "Echec creation HistoriqueOperation");
	}

	try {
	    historiqueOperationDao.create(historiqueOperation);
	} catch (Exception e) {
	    System.out.println(this.getClass().getName() + " - CREATE FAILED");
	    e.printStackTrace();
	    throw new AdvcompException(this.getClass().getName() + "Echec persistance HistoriqueOperation");
	}
	System.out.println(this.getClass().getName() + " - HistoriqueOperation CREATION SUCCEEDED");

	System.out.println(
		this.getClass().getName() + " - Historisation " + client.getNom() + " ==> " + descriptionOperation);

    }

    /**
     * checkDescription
     *
     * TODO : Fill method utility
     * 
     * @param descriptionOperation
     * @throws AdvcompException
     */
    private void checkDescription(String descriptionOperation) throws AdvcompException {
	if (descriptionOperation == null || descriptionOperation.isEmpty()) {
	    throw new AdvcompException(this.getClass().getName() + "Description nulle.");
	}
    }

    /**
     * checkClient
     *
     * TODO : Fill method utility
     * 
     * @param client
     * @throws AdvcompException
     */
    private void checkClient(Utilisateur client) throws AdvcompException {
	if (client == null) {
	    throw new AdvcompException(this.getClass().getName() + " Client nul !");
	}
    }

    private final Double PRICE = 1.20d;

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationService#facturer(java.util.Date,
     * java.util.Date)
     *
     * @param dateDebut
     * @param datefin
     * @throws AdvcompException
     */
    @Override
    public void facturer(Date dateDebut, Date datefin) throws AdvcompException {

	// Liste qui accueillera les factures
	List<Facture> factures = new ArrayList<Facture>();
	// Liste qui accueuillera es historiques mis à jours
	List<HistoriqueOperation> updatedHistoriques = new ArrayList<HistoriqueOperation>();

	// Lister les operations en cours
	List<HistoriqueOperation> historiques = new ArrayList<HistoriqueOperation>();
	historiques = historiqueOperationDao.getOperationAFacturer(dateDebut, datefin);

	Iterator<HistoriqueOperation> iterateurHistoriques = historiques.iterator();
	HistoriqueOperation historiqueEnCours = null;

	// Rentrer dans la boucle
	if (iterateurHistoriques.hasNext()) {
	    historiqueEnCours = iterateurHistoriques.next();
	    System.out.println("Hist en cours 1 : " + historiqueEnCours);
	}

	// compter les iterations
	int iteration_1 = 0;

	while (iterateurHistoriques.hasNext()) {
	    System.out.println("===_===_===_===_===_===");
	    iteration_1 += 1;
	    System.out.println("Iteration premier niveau : " + iteration_1);
	    // recuperer l'utilisateur
	    Utilisateur user = historiqueEnCours.getUtilisateur();
	    System.out.println("Utilisateur : " + user.toString());

	    // Obtenir une nouvelle facture
	    Facture facture;
	    try {
		facture = factureDao.getNew();
	    } catch (Exception e) {
		throw new AdvcompException(e);
	    }

	    if (facture == null) {
		throw new AdvcompException("Facture nulle.");
	    }
	    // Setup des propriétés de la facture
	    facture.setUtilisateur(user);
	    facture.setSoldee(false);
	    facture.setDate(new Date());
	    facture.setMontant(PRICE);
	    System.out.println("Facture 1 " + facture.toString());

	    int iteration_2 = 0;

	    while (iterateurHistoriques.hasNext() && historiqueEnCours.getUtilisateur().equals(user)) {
		System.out.println("-------------------------------------------");
		;
		iteration_2 += 1;
		System.out.println("Iteration second niveau : " + iteration_2);
		facture.setMontant(facture.getMontant() + PRICE);
		System.out.println("Facture 2 " + facture.toString());
		// Set de la facture sur l'historique
		historiqueEnCours.setFacture(facture);
		System.out.println("Historique en cours 1 : " + historiqueEnCours.toString());
		try {
		    updatedHistoriques.add(historiqueEnCours);
		    historiqueOperationDao.update(historiqueEnCours);
		} catch (Exception e) {
		    throw new AdvcompException(e);
		}
		// Passage à l'iteration suivante
		historiqueEnCours = iterateurHistoriques.next();
		// Affichage pour verification
		System.out.println("Historique en cours 2 : " + historiqueEnCours.toString());
	    }

	    try {
		factures.add(facture);
		factureDao.create(facture);
	    } catch (Exception e) {
		throw new AdvcompException(e);
	    }
	    System.out.println("+++++++++++++++++++++++++++++++++++++++++");
	    System.out.println("\tFacture 2 : " + facture);
	    System.out.println("===_===_===_===_===_===");
	}
	System.out.println("****************************************");
	System.out.println("FACTURES : ");
	for (Facture tempFacture : factures) {
	    System.out.println("Facture : " + tempFacture.toString());
	}
	System.out.println("****************************************");
	System.out.println("HISTORIQUES : ");
	for (HistoriqueOperation historiqueOperation : historiques) {
	    System.out.println("Historique : " + historiqueOperation.toString());
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationService#reglerFacture(java.lang.String,
     * java.lang.String)
     *
     * @param numeroFacture
     * @param rib
     * @throws AdvcompException
     */
    @Override
    public void reglerFacture(String numeroFacture, String rib) throws AdvcompException {
	Facture factureARegler = null;
	try {
	    factureARegler = factureDao.retrieve(numeroFacture);
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}
	if (factureARegler == null) {
	    throw new AdvcompException("Id incorrect");
	}
	System.out.println("************************************************");
	System.out.println("REGLEMENT FACTURE : " + factureARegler.toString());
	System.out.println("RIB : " + rib);
	factureARegler.setSoldee(true);
	try {
	    factureDao.update(factureARegler);
	}catch (Exception e) {
	    throw new AdvcompException(e);
	}
	System.out.println("FACTURE REGLEE.");

    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationService#obtenirFacture(java.lang.String)
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
