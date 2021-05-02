// File FirstTest.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.tests.connexion;

import static org.junit.Assert.*;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.bd.advcomp.AdvCompStartup;
import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.DEV_CONFIG;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class TestConnexion {

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

    static AdvCompServer serveur;

    private static AdvCompServer doServerLookup() throws NamingException, AdvCompException {
	AdvCompServer serveur = InitialContext.doLookup(AdvCompServer.JNDI);
	if (serveur == null) {
	    throw new AdvCompException("AdvCompServer is null");
	}
	return serveur;
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
     * testServeur
     *
     * Test l'instanciation du serveur
     */
    @Test
    public void testServeur() {
	assertTrue(serveur instanceof AdvCompServer);
	assertTrue(serveur != null);
    }

    /**
     * testConnexion_ doit Lever Une Exception
     *
     * TODO : Fill method utility
     * 
     * @throws AdvCompException
     */
    @Test(expected = AdvCompException.class)
    public void testConnexion_doitLeverUneException() throws AdvCompException {
	serveur.connexion("login", "password");
    }
    
    





}
