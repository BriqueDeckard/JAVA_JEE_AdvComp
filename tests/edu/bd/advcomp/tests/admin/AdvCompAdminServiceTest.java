// File AdvCompAdminServiceTest.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.tests.admin;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.bd.advcomp.AdvCompStartup;
import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.entity.Facture;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class AdvCompAdminServiceTest {
    static AdvCompServer serveur;

    static AdvCompAdminService service;
    static AdvCompService advCompService;

    private static AdvCompServer doServerLookup() throws NamingException, AdvCompException {
	AdvCompServer serveur = InitialContext.doLookup(AdvCompServer.JNDI);
	if (serveur == null) {
	    throw new AdvCompException("AdvCompServer est null");
	}
	return serveur;
    }

    @Test(expected = AdvCompException.class)
    public final void testSeConnecter_doitLeverUneException() throws AdvCompException {
	serveur.connexionAsAdmin(AdvCompStartup.testUserActif.getId(), AdvCompStartup.testUserActif.getPassword());
    }

    @Test
    public final void testSeConnecter_doitRetournerUnAdvCompAdminService() throws AdvCompException {
	AdvCompAdminService adminService = serveur.connexionAsAdmin(AdvCompStartup.testAdmin.getId(),
		AdvCompStartup.testAdmin.getPassword());

	assertTrue(adminService != null);
	assertTrue(adminService instanceof AdvCompAdminService);
    }

    /**
     * setUpBeforeClass
     *
     * TODO : Fill method utility
     * 
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	serveur = doServerLookup();
	System.out.println("Demarrage du serveur.");
	service = serveur.connexionAsAdmin(AdvCompStartup.testAdmin.getId(), AdvCompStartup.testAdmin.getPassword());
	assertTrue(service != null);
	assertTrue(service instanceof AdvCompAdminService);

	advCompService = serveur.connexion(AdvCompStartup.testUserActif.getId(),
		AdvCompStartup.testUserActif.getPassword());
	assertTrue(advCompService != null);
	assertTrue(advCompService instanceof AdvCompService);
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.admin.service.AdvCompAdminService#validerCompteUtilisateur(java.lang.String)}.
     * 
     * @throws AdvCompException
     */
    @Test
    public final void testValiderCompteUtilisateur() throws AdvCompException {
	try {
	    service.validerCompteUtilisateur(AdvCompStartup.testUserInactif.getId());
	    Utilisateur actual = service.findUser(AdvCompStartup.testUserInactif.getId());
	    assertTrue(actual.getIsActive());
	} catch (Exception e) {
	    assertTrue(false);
	} finally {
	    service.desactiverCompteUtilisateur(AdvCompStartup.testUserInactif.getId());
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.admin.service.AdvCompAdminService#supprimerCompteUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)}.
     */
    @Test
    public final void testSupprimerCompteUtilisateur() {
	try {
	    service.supprimerCompteUtilisateur(AdvCompStartup.testUserASupprimer);
	    Utilisateur actual = null;
	    actual = service.findUser(AdvCompStartup.testUserASupprimer.getId());
	    assertNull(actual);
	} catch (Exception e) {
	    assertTrue(false);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.admin.service.AdvCompAdminService#consulterFacture()}.
     */
    @Test
    public final void testConsulterFacture() {
	try {
	    String expected = AdvCompStartup.testFacture.toString();
	    String actual = service.consulterFacture(AdvCompStartup.testFacture.getId());
	    assertEquals(expected, actual);

	} catch (Exception e) {
	    assertTrue(false);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.admin.service.AdvCompAdminService#genererFacture(java.util.Date, java.util.Date)}.
     */
    @Test
    public final void testGenererFacture() {
	try {

	    advCompService.faireOperationBasique(10d, 10d, "+");
	    Date dateDebut = new Date();
	    dateDebut.setMonth(dateDebut.getMonth() - 1);
	    dateDebut.setDate(dateDebut.getDate() + 1);
	    System.out.println("dd : " + dateDebut.toString());

	    Date dateFin = new Date();
	    System.out.println("df : " + dateFin.toString());
	    service.genererFacture(dateDebut, dateFin);
	    List<Facture> factures = service.getAllTheFacture();
	    assertTrue(factures.size() > 0);

	} catch (Exception e) {
	    assertTrue(false);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.admin.service.AdvCompAdminService#seDeconnecter()}.
     * 
     * @throws AdvCompException
     */
    @Test
    public final void testSeDeconnecter() throws AdvCompException {
	try {
	    service.seDeconnecter();
	    assertTrue(true);
	} catch (Exception e) {
	    assertTrue(false);
	} finally {
	    service = serveur.connexionAsAdmin(AdvCompStartup.testAdmin.getId(),
		    AdvCompStartup.testAdmin.getPassword());
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.admin.service.AdvCompAdminService#seDeconnecter()}.
     * 
     * @throws AdvCompException
     */
    @Test
    public final void testSeDeconnecter_doitLeverUneException() throws AdvCompException {
	try {
	    service.seDeconnecter();
	    service.desactiverCompteUtilisateur(AdvCompStartup.testUserActif.getId());
	} catch (Exception e) {
	    assertTrue(true);
	} finally {
	    service = serveur.connexionAsAdmin(AdvCompStartup.testAdmin.getId(),
		    AdvCompStartup.testAdmin.getPassword());
	    service.validerCompteUtilisateur(AdvCompStartup.testUserActif.getId());
	}
    }
}
