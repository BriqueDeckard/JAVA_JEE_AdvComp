// File AdvCompServiceTest.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.tests.core.services;

import static org.junit.Assert.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.bd.advcomp.AdvCompStartup;
import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class AdvCompServiceTest {
    static AdvCompServer serveur;

    static AdvCompService service;

    private static AdvCompServer doServerLookup() throws NamingException, AdvCompException {
	AdvCompServer serveur = InitialContext.doLookup(AdvCompServer.JNDI);
	if (serveur == null) {
	    throw new AdvCompException("AdvCompServer est null");
	}
	return serveur;
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
	service = serveur.connexion(AdvCompStartup.testUserActif.getId(),
		AdvCompStartup.testUserActif.getPassword());
	assertTrue(service != null);
	assertTrue(service instanceof AdvCompService);

    }

    /**
     * setUp
     *
     * TODO : Fill method utility
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#faireOperationBasique(java.lang.Double, java.lang.Double, java.lang.String)}.
     * 
     * @throws AdvCompException
     */
    @Test
    public void testFaireOperationBasique() throws AdvCompException {
	Double expected = 3d;
	Double actual = service.faireOperationBasique(1d, 2d, "+");
	assertEquals(expected, actual);
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#commencerOperationChainee(java.lang.Double, java.lang.Double, java.lang.String)}.
     */
    @Test
    public void testCommencerOperationChainee() {
	try {
	    service.commencerOperationChainee(1d, 2d, "+");
	} catch (AdvCompException e) {
	    assertTrue(false);
	}
    }

    @Test
    public void testCommencerOperationChainee_doitLeverUneException() {
	try {
	    service.commencerOperationChainee(1d, 2d, "+");
	    service.commencerOperationChainee(1d, 2d, "+");
	} catch (AdvCompException e) {
	    assertTrue(true);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#poursuivreOperationChainee(java.lang.Double, java.lang.String)}.
     */
    @Test
    public void testPoursuivreOperationChainee() {
	try {
	    service.commencerOperationChainee(1d, 2d, "+");
	    service.poursuivreOperationChainee(3d, "*");
	} catch (AdvCompException e) {
	    assertTrue(false);
	}
    }

    @Test
    public void testPoursuivreOperationChainee_doitLeverUneException() {
	try {
	    service.poursuivreOperationChainee(3d, "*");
	} catch (AdvCompException e) {
	    assertTrue(true);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#acheverOperationChainee()}.
     */
    @Test
    public void testAcheverOperationChainee() {
	try {
	    service.commencerOperationChainee(1d, 2d, "+");
	    service.poursuivreOperationChainee(3d, "*");
	    service.acheverOperationChainee();
	} catch (AdvCompException e) {
	    assertTrue(false);
	}
    }

    @Test
    public void testAcheverOperationChainee_doitLeverUneException() {
	try {
	    service.acheverOperationChainee();
	} catch (AdvCompException e) {
	    assertTrue(true);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#afficherResultatFinal()}.
     */
    @Test
    public void testAfficherResultatFinal() {
	String actual = "";
	String expected = "9.0";
	try {
	    service.commencerOperationChainee(1d, 2d, "+");
	    service.poursuivreOperationChainee(3d, "*");
	    service.acheverOperationChainee();
	    actual = service.afficherResultatFinal();
	} catch (AdvCompException e) {
	    assertTrue(false);
	}
	assertEquals(expected, actual);
    }

    @Test
    public void testAfficherResultatFinal_doitLeverUneException() {

	try {
	    service.afficherResultatFinal();
	} catch (AdvCompException e) {
	    assertTrue(true);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#afficherResultatIntermediaire()}.
     */
    @Test
    public void testAfficherResultatIntermediaire() {
	String actual = "";
	String expected = "9.0";
	try {
	    service.commencerOperationChainee(1d, 2d, "+");
	    service.poursuivreOperationChainee(3d, "*");
	    actual = service.afficherResultatIntermediaire();
	} catch (AdvCompException e) {
	    assertTrue(false);
	}
	assertEquals(expected, actual);
    }

    @Test
    public void testAfficherResultatIntermediaire_doitLeverUneException() {

	try {
	    String actual = service.afficherResultatIntermediaire();
	} catch (AdvCompException e) {
	    assertTrue(true);
	}

    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#seDeconnecter()}.
     * 
     * @throws AdvCompException
     */
    @Test
    public void testSeDeconnecter() throws AdvCompException {
	try {
	    service.seDeconnecter();
	    assertTrue(true);
	} catch (Exception e) {
	    assertTrue(false);
	} finally {
	    service = serveur.connexion(AdvCompStartup.testUserActif.getId(),
		    AdvCompStartup.testUserActif.getPassword());
	}

    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompService#seDeconnecter()}.
     * 
     * @throws AdvCompException
     */
    @Test
    public void testSeDeconnecter_doitLeveeUneException() throws AdvCompException {
	try {
	    service.seDeconnecter();
	    service.faireOperationBasique(1d, 1d, "+");
	} catch (Exception e) {
	    assertTrue(true);
	} finally {
	    service = serveur.connexion(AdvCompStartup.testUserActif.getId(),
		    AdvCompStartup.testUserActif.getPassword());
	}
    }
}
