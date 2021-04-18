// File FacturationServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.advcomp.facturation.event.FacturationEvent;
import edu.bd.advcomp.facturation.event.FacturationSignalEvent;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class FacturationServiceImpl implements FacturationService {

    @Inject
    Event<FacturationSignalEvent> facturationSignalEvents;

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
     * edu.bd.advcomp.facturation.service.FacturationService#listenToTimerEvents(edu.bd.advcomp.facturation.event.FacturationSignalEvent)
     *
     * @param facturationSignalEvent
     * @throws AdvcompException
     */
    @Override
    public void listenToTimerEvents(@Observes FacturationSignalEvent facturationSignalEvent) throws AdvcompException {
	System.out.println("************************************************");
	System.out.println("INFO : Facturation timer : ".toUpperCase() + facturationSignalEvent.toString());

	Date dateDebut = facturationSignalEvent.getDateDebut();
	Date dateFin = facturationSignalEvent.getDateFin();

	try {
	    facturer(dateDebut, dateFin);
	    System.out.println("------------------------------------------------");
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationService#listenToAdvCompServiceEvents(edu.bd.advcomp.facturation.event.FacturationEvent)
     *
     * @param facturationEvent
     * @throws AdvcompException
     */
    @Override
    public void listenToAdvCompServiceEvents(@Observes FacturationEvent facturationEvent) throws AdvcompException {
	System.out.println("************************************************");
	System.out.println("INFO : Facturation event : ".toUpperCase() + facturationEvent.toString());

	Utilisateur user = facturationEvent.getUser();
	String message = facturationEvent.getMessage();

	try {
	    historiserOperation(user, message);
	    System.out.println("------------------------------------------------");
	} catch (AdvcompException e) {
	    throw new AdvcompException(e);
	}

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
	System.out.println("************************************************");
	System.out.println("INFO : HistoriserOperation ".toUpperCase());

	checkClient(client);
	checkDescription(descriptionOperation);

	HistoriqueOperation historiqueOperation;

	// Create a new HistoriqueOperation
	try {
	    historiqueOperation = historiqueOperationDao.getNew();
	    historiqueOperation.setDate(new Date());
	    historiqueOperation.setUtilisateur(client);
	    historiqueOperation.setDescription(descriptionOperation);
	    historiqueOperation.setFacture(null);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(this.getClass().getName() + "Echec creation HistoriqueOperation");
	}

	// Persistence
	try {
	    historiqueOperationDao.create(historiqueOperation);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(this.getClass().getName() + "Echec persistance HistoriqueOperation");
	}

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
	System.out.println("************************************************");
	System.out.println("INFO : FACTURATION BETWEEN " + dateDebut.toString() + " AND " + datefin.toString());

	// Lister les operations en cours
	List<HistoriqueOperation> historiques = historiqueOperationDao.getOperationAFacturer(dateDebut, datefin);
	System.out.println("INFO : Total historique : " + historiques.size());

	historiques = historiques.stream().filter(h -> h.getFacture() == null).collect(Collectors.toList());
	System.out.println("INFO : historiques to bill : " + historiques.size());

	Iterator<HistoriqueOperation> iterateurHistoriques = historiques.iterator();

	HistoriqueOperation historiqueEnCours = null;

	// Rentrer dans la boucle
	if (iterateurHistoriques.hasNext()) {
	    historiqueEnCours = iterateurHistoriques.next();
	}

	// compter les iterations
	int iter1 = 0;

	while (iterateurHistoriques.hasNext()) {
	    System.out.println("___________________________________________");

	    // recuperer l'utilisateur
	    Utilisateur user = historiqueEnCours.getUtilisateur();
	    System.out.println("INFO : Facturation for user : " + user.getNom());

	    iter1 += 1;
	    System.out.println("INFO : ITERATION 1 : " + iter1);

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

	    // fACTURE PERSISTENCE
	    try {
		factureDao.create(facture);
	    } catch (Exception e) {
		e.printStackTrace();
		throw new AdvcompException(e);
	    }
	    System.out.println("INFO : Created facture : " + facture.getMontant());

	    // compter les iterations
	    int iter2 = 0;
	    while (iterateurHistoriques.hasNext() && historiqueEnCours.getUtilisateur().equals(user)) {
		System.out.println("-------------------------------------------");
		iter2 += 1;
		System.out.println("INFO : ITERATION 2 : " + iter2);

		facture.setMontant(facture.getMontant() + PRICE);

		historiqueEnCours.setFacture(facture);
		try {
		    System.out.println("INFO : Historique en cours : " + historiqueEnCours.toString());
		    historiqueOperationDao.update(historiqueEnCours);
		    System.out.println("INFO : Update 1 facture : " + facture.getMontant());
		} catch (Exception e) {
		    throw new AdvcompException(e);
		}

		// Passage à l'iteration suivante
		historiqueEnCours = iterateurHistoriques.next();
	    }
	    try {
		historiqueOperationDao.update(historiqueEnCours);
		System.out.println("INFO : FACTURE PRICE : " + facture.getMontant());
		System.out.println("INFO : Update 2 facture : " + facture.getMontant());
	    } catch (Exception e1) {
		e1.printStackTrace();
	    }

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
	} catch (Exception e) {
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
	try {
	    return this.factureDao.retrieve(numeroFacture);
	} catch (AdvcompException e) {
	    // TODO Fill catch block.
	    e.printStackTrace();
	    return null;
	}
    }

}
