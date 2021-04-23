// File AdvCompStartup.java - No copyright - 31 mars 2021
package edu.bd.advcomp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.impl.FactureImpl;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD TODO : @Startup veut dire que ce bean va être créé a
 *         l'utilisation TODO : @Singleton
 */
@Startup
@Singleton
public class AdvCompStartup {

    public static String JNDI = "java:global/AdvCompEjb/AdvCompStartup!edu.bd.advcomp.AdvCompStartup";

    public static Utilisateur testUserInactif = new UtilisateurImpl("CLIENT1", "SECRET", "CLIENT1", "ADRESSE1",
	    Role.CLIENT, false);
    public static Utilisateur testUserActif = new UtilisateurImpl("CLIENT2", "SECRET", "CLIENT2", "ADRESSE2",
	    Role.CLIENT, true);
    public static Utilisateur testUserASupprimer = new UtilisateurImpl("CLIENT3", "SECRET", "CLIENT3", "ADRESSE3",
	    Role.CLIENT, true);
    public static Utilisateur testAdmin = new UtilisateurImpl("ADMIN1", "SECRET", "ADMIN1", "ADRESSE4",
	    Role.ADMINISTRATEUR, true);

    public static Facture testFacture = new FactureImpl(testUserActif, "FACTURE1", new Date(), 10d, false);

    /**
     * Entity Manager
     */ // persistence.xml --> <persistence-unit name="advcomp"
    @PersistenceContext(unitName = "advcomp")
    EntityManager em;

    /**
     * dev initialization.
     *
     * TODO : Fill method utility
     */
    private void devInit() {
	System.out.println("####################################");
	System.out.println("# " + this.getClass().getSimpleName() + " - BOOTSTRAP DEV.");
	System.out.println("#");

	try {
	    // INSERTION ADMIN
	    UtilisateurDao userDao = doUtilisateurDaoLookUp();

	    try {
		userDao.delete(testAdmin);
	    } catch (Exception e) {
		System.out.println("ECHEC lors de la suppression de l'admin de test");
		e.printStackTrace();
	    } finally {
		userDao.create(testAdmin);
		System.out.println("# \tAdmin insere.");
	    }

	    // INSERTION CLIENTS
	    try {
		userDao.delete(testUserActif);
		userDao.delete(testUserInactif);
		userDao.delete(testUserASupprimer);
	    } catch (Exception e) {
		System.out.println("ECHEC lors de la suppression des utilisateurs de test.");
		e.printStackTrace();
	    } finally {

		userDao.create(testUserActif);
		System.out.println("# \tUtilisateur actif insere.");

		userDao.create(testUserInactif);
		System.out.println("# \tUtilisateur inactif insere.");

		userDao.create(testUserASupprimer);
		System.out.println("# \tUtilisateur a supprimer insere.");

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	try {

	    // INSERTION FACTURE
	    FactureDao billingDao = doFacturationDaoLookup();
	    try {
		billingDao.delete(testFacture);
	    } catch (Exception e) {
		System.out.println("ECHEC lors de la suppression de la facture de test.");
		e.printStackTrace();
	    } finally {
		billingDao.create(testFacture);
		System.out.println("# \tFacture test inseree");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	// CONNEXION
	// 1 . User
	try {
	    System.out.println("CONNEXION ...");
	    AdvCompServer serveur = doServerLookup();
	    AdvCompService service = serveur.connexion(testUserActif.getLogin(), testUserActif.getPassword());
	    System.out.println("CONNEXION SUCCEDED");
	    service.seDeconnecter();
	    System.out.println("CONNEXION ...");
	    serveur = doServerLookup();
	    service = serveur.connexion(testUserActif.getLogin(), testUserActif.getPassword());
	    System.out.println("CONNEXION SUCCEDED");
	    service.faireOperationBasique(10d, 12d, "+");
	    service.seDeconnecter();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// 2 . Admin
	try {
	    System.out.println("CONNEXION AS ADMIN ...");
	    AdvCompServer serveur = doServerLookup();
	    AdvCompAdminService service = serveur.connexionAsAdmin(testAdmin.getLogin(), testAdmin.getPassword());
	    System.out.println("CONNEXION AS ADMIN SUCEEDED");

	    List<ConnexionAttempt> list = service.getAllTheConnexionAttempt();
	    for (ConnexionAttempt connexionAttempt : list) {
		System.out.println("CONNEXION : " + connexionAttempt.toString());
	    }

	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    String beginDateString = "20-04-2021";
	    Date bDate = formatter.parse(beginDateString);
	    String endDateString = "22-04-2021";
	    Date eDate = formatter.parse(endDateString);

	    System.out.println("Nombres de connexions entre " + beginDateString + " et " + endDateString + " : "
		    + service.getNumberOfConnexionFor(bDate, eDate));
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * doServerLookup
     *
     * TODO : Fill method utility
     * 
     * @return
     * @throws NamingException
     * @throws AdvcompException
     */
    private AdvCompServer doServerLookup() throws NamingException, AdvcompException {
	AdvCompServer serveur = InitialContext.doLookup(AdvCompServer.JNDI); // "java:global/AdvCompEjb/AdvCompServerImpl!edu.bd.advcomp.core.service.AdvCompServer");
	if (serveur == null) {
	    throw new AdvcompException("AdvCompServer is null");
	}
	return serveur;
    }

    /**
     * doUserServiceLookup
     *
     * TODO : Fill method utility
     * 
     * @return
     * @throws AdvcompException
     */
    private UtilisateurService doUserServiceLookup() throws AdvcompException {
	// GETS THE USER SERVICE
	UtilisateurService userService;
	try {
	    userService = InitialContext.doLookup(UtilisateurService.JNDI); // "java:global/AdvCompEjb/UtilisateurServiceImpl!edu.bd.advcomp.authentification.service.UtilisateurService");
	    return userService;
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);

	}
    }

    /**
     * doUtilisateurDaoLookUp
     *
     * TODO : Fill method utility
     * 
     * @return
     * @throws AdvcompException
     */
    private UtilisateurDao doUtilisateurDaoLookUp() throws AdvcompException {
	// GETS THE UTILISATEUR DAO
	UtilisateurDao dao;
	try {
	    dao = InitialContext.doLookup(UtilisateurDao.JNDI);
	    return dao;
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}
    }

    /**
     * doFacturationDaoLookup
     *
     * TODO : Fill method utility
     * 
     * @return
     * @throws AdvcompException
     */
    private FactureDao doFacturationDaoLookup() throws AdvcompException {
	// GETS THE FACTURE DAO
	FactureDao factureDao;
	try {
	    factureDao = InitialContext.doLookup(FactureDao.JNDI);
	    return factureDao;
	} catch (Exception e) {
	    throw new AdvcompException(e);
	}
    }

    /**
     * init TODO : init avec PostConstruct pour fair ele bootstrap. Bootstrap de
     * l'application AdvComp
     * 
     * @throws AdvcompException
     * 
     * @throws Exception
     */
    @PostConstruct
    public void init() {
	devInit();
    }

}
