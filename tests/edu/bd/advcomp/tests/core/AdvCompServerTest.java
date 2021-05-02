// File AdvCompServerTest.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.tests.core;

import static org.junit.Assert.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.bd.advcomp.AdvCompStartup;
import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class AdvCompServerTest {

    static AdvCompServer serveur;

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
     * {@link edu.bd.advcomp.core.service.AdvCompServer#connexion(java.lang.String, java.lang.String)}.
     * 
     * @throws AdvCompException
     */
    @Test
    public void testConnexionUserActif() throws AdvCompException {
	try {
	    AdvCompService service = serveur.connexion(AdvCompStartup.testUserActif.getId(),
		    AdvCompStartup.testUserActif.getPassword());
	    assertTrue(service != null);
	    assertTrue(service instanceof AdvCompService);
	} catch (Exception e) {
	    assertTrue(false);
	}

    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompServer#connexion(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testConnexionUserInactif() {
	try {
	    AdvCompService service = serveur.connexion(AdvCompStartup.testUserInactif.getId(),
		    AdvCompStartup.testUserInactif.getPassword());
	    assertTrue(service == null);

	} catch (Exception e) {
	    assertTrue(true);
	}

    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompServer#connexionAsAdmin(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testConnexionAsAdmin() {
	try {
	    AdvCompAdminService service = serveur.connexionAsAdmin(AdvCompStartup.testAdmin.getId(),
		    AdvCompStartup.testAdmin.getPassword());

	    assertTrue(service != null);
	    assertTrue(service instanceof AdvCompAdminService);
	} catch (Exception e) {
	    assertTrue(false);
	}
    }

    /**
     * Test method for
     * {@link edu.bd.advcomp.core.service.AdvCompServer#creerCompte(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCreerCompte() {
	try {
	    serveur.creerCompte("Pierre", "Brique", "secret", "Clermont Ferrain");
	} catch (Exception e) {
	    assertTrue(false);
	}
    }
}
