// File AdvCompStartup.java - No copyright - 31 mars 2021
package edu.bd.advcomp;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
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
     */
    @PostConstruct
    public void init() {
	System.out.println("*** BOOTSTRAP EXECUTE ***");
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
