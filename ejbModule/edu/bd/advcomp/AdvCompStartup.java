// File AdvCompStartup.java - No copyright - 31 mars 2021
package edu.bd.advcomp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.advcomp.facturation.entity.impl.FactureImpl;
import edu.bd.advcomp.facturation.entity.impl.HistoriqueOperationImpl;
import edu.bd.advcomp.facturation.service.FacturationService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD TODO : @Startup veut dire que ce bean va être créé a
 *         l'utilisation TODO : @Singleton
 */
@Startup
@Singleton
public class AdvCompStartup {

    private Utilisateur clientFactory(Utilisateur utilisateur) {
	utilisateur.setLogin(DEV_CONFIG.client_001.toString());
	utilisateur.setAdresse(DEV_CONFIG.adresse.toString());
	utilisateur.setNom(DEV_CONFIG.client_001.toString());
	utilisateur.setPassword(DEV_CONFIG.secret.toString());
	utilisateur.setRole(Role.CLIENT);

	System.out.println("Utilisateur : " + utilisateur.toString());
	return utilisateur;
    }

    private Utilisateur adminFactory(Utilisateur utilisateur) {

	utilisateur.setLogin(DEV_CONFIG.admin_001.toString());
	utilisateur.setAdresse(DEV_CONFIG.adresse.toString());
	utilisateur.setNom(DEV_CONFIG.admin_001.toString());
	utilisateur.setPassword(DEV_CONFIG.secret.toString());
	utilisateur.setRole(Role.ADMINISTRATEUR);

	System.out.println("Utilisateur : " + utilisateur.toString());
	return utilisateur;
    }

    /**
     * Entity Manager
     */ // persistence.xml --> <persistence-unit name="advcomp"
    @PersistenceContext(unitName = "advcomp")
    EntityManager em;

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
	System.out.println("*************************");
	System.out.println(this.getClass().getSimpleName() + " - BOOTSTRAP EXECUTE ***");
	try {
	    // GETS THE USER SERVICE
	    UtilisateurService userService = InitialContext.doLookup(
		    "java:global/AdvCompEjb/UtilisateurServiceImpl!edu.bd.advcomp.authentification.service.UtilisateurService");
	    if (userService == null) {
		throw new AdvcompException("userService is null");
	    }
	    System.out.println(this.getClass().getSimpleName() + " - GOT USER SERVICE");

	    // INSERTS AN USER
	    Utilisateur client_001 = userService.getNewUtilisateur();
	    System.out.println(this.getClass().getSimpleName() + " - CLIENT_001 = " + client_001.toString());
	    client_001 = clientFactory(client_001);
	    try {
		userService.creerUtilisateur(client_001);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    System.out.println(this.getClass().getSimpleName() + " - USER INSERTED");

	    // GETS THE SERVER
	    AdvCompServer serveur = InitialContext
		    .doLookup("java:global/AdvCompEjb/AdvCompServerImpl!edu.bd.advcomp.core.service.AdvCompServer");
	    if (serveur == null) {
		throw new AdvcompException("AdvCompServer is null");
	    }
	    System.out.println(this.getClass().getSimpleName() + " - GOT SERVER");

	    // GETS THE SERVICE
	    AdvCompService advCompService = null;
	    try {
		advCompService = serveur.connexion(DEV_CONFIG.client_001.toString(), DEV_CONFIG.secret.toString());
	    } catch (Exception e) {
		e.printStackTrace();
		throw new AdvcompException(e);
	    }
	    if (advCompService == null) {
		throw new AdvcompException("AdvCompService is null");
	    }
	    System.out.println(this.getClass().getSimpleName() + " - GOT ADVCOMPSERVICE");

	    Double resultat = advCompService.faireOperationBasique(1d, 1d, "+");
	    resultat = advCompService.faireOperationBasique(2d, 2d, "+");

	    System.out.println("* RESULTAT operation basique = " + resultat);

	    resultat = null;
//
//	    advCompService.commencerOperationChainee(1d, 1d, "+");
//	    advCompService.poursuivreOperationChainee(1d, "+");
//	    resultat = advCompService.acheverOperationChainee();
//	    System.out.println("* RESULTAT operation chaînée = " + resultat);

	    FacturationService facturationService = InitialContext.doLookup(
		    "java:global/AdvCompEjb/FacturationServiceImpl!edu.bd.advcomp.facturation.service.FacturationService");
	    if (facturationService == null) {
		throw new AdvcompException("Facturation service is null");
	    }

	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    String dateInStringBegin = "04-04-2021";
	    Date beginDate = formatter.parse(dateInStringBegin);
	    String dateInStringEnd = "06-04-2021";
	    Date endDate = formatter.parse(dateInStringEnd);

	    facturationService.facturer(beginDate, endDate);

	} catch (Exception e) {
	    AdvcompException exception = new AdvcompException(e);
	    exception.printStackTrace();
	}
	System.out.println(this.getClass().getSimpleName() + "*** BOOTSTRAP END ***");
	System.out.println("*************************");
    }

}
