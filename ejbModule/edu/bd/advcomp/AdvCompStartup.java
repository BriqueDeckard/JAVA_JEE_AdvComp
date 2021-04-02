// File AdvCompStartup.java - No copyright - 31 mars 2021
package edu.bd.advcomp;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.advcomp.facturation.entity.impl.FactureImpl;
import edu.bd.advcomp.facturation.entity.impl.HistoriqueOperationImpl;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD TODO : @Startup veut dire que ce bean va être créé a
 *         l'utilisation TODO : @Singleton
 */
@Startup
@Singleton
public class AdvCompStartup {

    /**
     * Entity Manager
     */ // persistence.xml --> <persistence-unit name="advcomp"
    @PersistenceContext(unitName = "advcomp")
    EntityManager em;

    /**
     * init TODO : init avec PostConstruct pour fair ele bootstrap. Bootstrap de
     * l'application AdvComp
     * 
     * @throws Exception
     */
    @PostConstruct
    public void init() {
	System.out.println("*** BOOTSTRAP EXECUTE ***");

	try {
	    // [ USER ]
	    // ***************************************************************************************************
	    String idForUser = "toto";

	    // Get the DAO for User
	    String utilisateurDaoClass = UtilisateurDao.class.getName();
	    UtilisateurDao userDao = (UtilisateurDao) InitialContext
		    .doLookup("java:global/AdvCompEjb/UtilisateurDaoSql!" + utilisateurDaoClass);

	    // Create an entity
	    UtilisateurImpl utilisateur = new UtilisateurImpl(idForUser, "secret", "toto", "clermont", Role.CLIENT);

	    // Persists entity
	    userDao.create(utilisateur);

	    // Find the entity
	    Utilisateur utilisateurObtenu = userDao.retrieve(idForUser);

	    // Display
	    System.out.println("INFO : Persisté " + utilisateurObtenu.toString());

	    // Modifies entity
	    utilisateurObtenu.setNom("tata");
	    utilisateurObtenu.setPassword("password");
	    utilisateurObtenu.setAdresse("nevers");
	    utilisateurObtenu.setRole(Role.ADMINISTRATEUR);

	    // Update entity
	    userDao.update(utilisateurObtenu);
	    // Find the entity
	    Utilisateur utilisateurMisAJour = userDao.retrieve(utilisateurObtenu.getLogin());
	    // Display
	    System.out.println("INFO : Mis à jour : " + utilisateurMisAJour.toString());

	    // [ HISTORIQUE ]
	    // ***************************************************************************************************
	    // Get the DAO for HistoriqueOperation
	    String historiqueOperationClass = HistoriqueOperationDao.class.getName();
	    HistoriqueOperationDao historiqueOperationDao = (HistoriqueOperationDao) InitialContext
		    .doLookup("java:global/AdvCompEjb/HistoriqueOperationDaoSql!" + historiqueOperationClass);

	    // Create an entity
	    HistoriqueOperation historique = new HistoriqueOperationImpl(001l, new Date(), "la description",
		    utilisateurObtenu);

	    // Persists entity
	    historiqueOperationDao.create(historique);

	    // Find entity
	    HistoriqueOperation historiqueObtenu = historiqueOperationDao.retrieve(historique.getId());

	    // Display
	    System.out.println("INFO : Persisté " + historiqueObtenu.toString());

	    // Modifies entity
	    historiqueObtenu.setDate(new Date());
	    historiqueObtenu.setDescription("Lorem ipsum");

	    // update entity
	    historiqueOperationDao.update(historiqueObtenu);

	    // Finds the entity
	    HistoriqueOperation historiqueMisAJour = historiqueOperationDao.retrieve(historiqueObtenu.getId());
	    // Display
	    System.out.println("INFO : Mis à jour : " + historiqueMisAJour.toString());

	    // [ FACTURE ]
	    // ***************************************************************************************************
	    // Get the DAO for Facture
	    String factureClass = FactureDao.class.getName();
	    FactureDao factureDao = (FactureDao) InitialContext
		    .doLookup("java:global/AdvCompEjb/FactureDaoSql!" + factureClass);
	    // Create an entity
	    Facture facture = new FactureImpl(utilisateurMisAJour, "001", new Date(), 10d, false);

	    // Persists entity
	    factureDao.create(facture);

	    // Find entity
	    Facture factureObtenue = factureDao.retrieve(facture.getNumero());

	    // Displays
	    System.out.println("INFO : Persisté " + factureObtenue.toString());

	    // Modifies entity
	    factureObtenue.setDate(new Date());
	    factureObtenue.setMontant(45d);
	    factureObtenue.setSoldee(true);

	    // Updates entity
	    factureDao.update(factureObtenue);

	    // Finds the entity
	    Facture factureMiseAJour = factureDao.retrieve(factureObtenue.getNumero());
	    // Display
	    System.out.println("INFO : Mis à jour : " + factureMiseAJour.toString());

	    // [DELETION ]
	    // Delete entity
	    userDao.delete(utilisateurMisAJour);
	    Utilisateur utilisateurNul = userDao.retrieve(utilisateurMisAJour.getLogin());
	    if (utilisateurNul != null) {
		throw new AdvcompException("Utilisateur non supprimé !");
	    }

	    System.out.println("USER Supprimé");
	    // Delete entity
	    historiqueOperationDao.delete(historiqueMisAJour);
	    HistoriqueOperation historiqueNul = historiqueOperationDao.retrieve(historiqueMisAJour.getId());
	    if (historiqueNul != null) {
		throw new AdvcompException("Historique non supprimé !");
	    }
	    System.out.println("HISTORIQUE supprimé");
	    
	    //Delete entity
	    factureDao.delete(factureMiseAJour);
	    Facture factureNulle = factureDao.retrieve(factureMiseAJour.getNumero());
	    if(factureNulle != null) {
		throw new AdvcompException("Facture non supprimée ! ");
	    }
	    System.out.println("FACTURE supprimée.");

	} catch (Exception e) {
	    e.printStackTrace();
	}

	// demo();

    }

    /**
     * demo
     *
     * TODO : Fill method utility
     */
    private void demo() {
	// Creation de l'utilisateur et persistance
	// UtilisateurImpl(login, password, nom, adresse, role)
	Utilisateur utilisateur = new UtilisateurImpl("toto", "secret", "toto", "clermont", Role.CLIENT);
	try {
	    em.persist(utilisateur);
	} catch (Exception e) {
	    System.out.println("**********\nERROR ! ");
	    e.printStackTrace();
	}
	Utilisateur gotUser = em.find(UtilisateurImpl.class, "toto");
	System.out.println("GOT USER : " + gotUser.toString());

	// Creation de l'historique et persistance
	// HistoriqueOperationImpl(Long id, Date date, String description, Utilisateur
	// utilisateur)
	HistoriqueOperation historique = new HistoriqueOperationImpl(001l, new Date(), "la description", gotUser);
	try {
	    em.persist(historique);
	} catch (Exception e) {
	    System.out.println("**********\nERROR ! ");
	    e.printStackTrace();
	}
	HistoriqueOperation gotHist = em.find(HistoriqueOperationImpl.class, 001l);
	System.out.println("GOT HIST : " + gotHist.toString());

	// Creation d'une facture et persistance
	// FactureImpl(Utilisateur utilisateur, String numero, Date date, double
	// montant, boolean isSoldee)
	Facture facture = new FactureImpl(gotUser, "f001", new Date(), 42d, false);
	try {
	    em.persist(facture);
	} catch (Exception e) {
	    System.out.println("**********\nERROR ! ");
	    e.printStackTrace();
	}
	Facture gotFact = em.find(FactureImpl.class, "f001");
	System.out.println("GOT FACT : " + gotFact.toString());

	gotHist.setFacture(facture);
	em.merge(gotHist);
	HistoriqueOperation gotHist2 = em.find(HistoriqueOperationImpl.class, 001l);
	System.out.println("GOT HIST 2 : " + gotHist2.toString());
    }

}
