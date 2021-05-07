// File FacturationServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
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
     * @throws AdvCompException
     */
    @Override
    public void historiserOperation(Utilisateur client, String descriptionOperation) throws AdvCompException {
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
	    throw new AdvCompException(this.getClass().getName() + "Echec creation HistoriqueOperation");
	}

	// Persistence
	try {
	    historiqueOperationDao.create(historiqueOperation);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvCompException(this.getClass().getName() + "Echec persistance HistoriqueOperation");
	}

    }

    /**
     * checkDescription
     *
     * TODO : Fill method utility
     * 
     * @param descriptionOperation
     * @throws AdvCompException
     */
    private void checkDescription(String descriptionOperation) throws AdvCompException {
	if (descriptionOperation == null || descriptionOperation.isEmpty()) {
	    throw new AdvCompException(this.getClass().getName() + "Description nulle.");
	}
    }

    /**
     * checkClient
     *
     * TODO : Fill method utility
     * 
     * @param client
     * @throws AdvCompException
     */
    private void checkClient(Utilisateur client) throws AdvCompException {
	if (client == null) {
	    throw new AdvCompException(this.getClass().getName() + " Client nul !");
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
     * @throws AdvCompException
     */
    @Override
    public void facturer(Date dateDebut, Date datefin) throws AdvCompException {
	System.out.println("************************************************");
	System.out.println("INFO : FACTURATION BETWEEN " + dateDebut.toString() + " AND " + datefin.toString());

	// Lister les operations en cours
	List<HistoriqueOperation> historiques = historiqueOperationDao.getOperationAFacturer(dateDebut, datefin);

	historiques = historiques.stream().filter(h -> h.getFacture() == null).collect(Collectors.toList());
	System.out.println("INFO : historiques à facturer : " + historiques.size());

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
		throw new AdvCompException(e);
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
		throw new AdvCompException(e);
	    }
	    System.out.println("INFO : Created facture : " + facture.getMontant());

	    // compter les iterations
	    int iter2 = 0;
	    while (iterateurHistoriques.hasNext() && historiqueEnCours.getUtilisateur().equals(user)) {
		System.out.println("-------------------------------------------");
		System.out.println("HISTORIQUE : " + historiqueEnCours.toString());
		iter2 += 1;
		//System.out.println("INFO : ITERATION 2 : " + iter2);

		facture.setMontant(facture.getMontant() + PRICE);

		historiqueEnCours.setFacture(facture);
		try {
		    //System.out.println("INFO : Historique en cours : " + historiqueEnCours.toString());
		    historiqueOperationDao.update(historiqueEnCours);
		    //System.out.println("INFO : Update 1 facture : " + facture.getMontant());
		} catch (Exception e) {
		    throw new AdvCompException(e);
		}

		// Passage à l'iteration suivante
		historiqueEnCours = iterateurHistoriques.next();
	    }
	    try {
		historiqueOperationDao.update(historiqueEnCours);
		//System.out.println("INFO : FACTURE PRICE : " + facture.getMontant());
		//System.out.println("INFO : Update 2 facture : " + facture.getMontant());
	    } catch (Exception e1) {
		e1.printStackTrace();
	    }
	    
	    System.out.println("FACTURE : " + facture.toString());

	}

    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationService#reglerFacture(java.lang.String,
     * java.lang.String)
     *
     * @param numeroFacture
     * @param rib
     * @throws AdvCompException
     */
    @Override
    public void reglerFacture(String numeroFacture, String rib) throws AdvCompException {
	Facture factureARegler = null;
	try {
	    factureARegler = factureDao.retrieve(numeroFacture);
	    System.out.println("Facture a regler : " + factureARegler.toString());
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
	System.out.println("************************************************");
	System.out.println("REGLEMENT FACTURE : " + factureARegler.toString());
	System.out.println("RIB : " + rib);
	factureARegler.setSoldee(true);
	try {
	    factureDao.update(factureARegler);
	} catch (Exception e) {
	    throw new AdvCompException(e);
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
	try {
	    return this.factureDao.retrieve(numeroFacture);
	} catch (AdvCompException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * See @see edu.bd.advcomp.facturation.service.FacturationService#retrieveAll()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public List<Facture> retrieveAll() throws AdvCompException {
	try {
	    return this.factureDao.retrieveAll();
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}
    }

}
