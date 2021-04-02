// File AdvCompClient.java - No copyright - 28 mars 2021
package edu.bd.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.DEV_CONFIG;
import edu.bd.advcomp.authentification.service.AuthentificationService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class AdvCompClient {

    /**
     * Constructor for AdvCompClient
     *
     */
    public AdvCompClient() {
    }

    public static void main(String args[]) throws AdvcompException {
	// Connexion
	// UTILISER JNDI
	// 1. lookup sur advcomp_server
	try {
	    AdvCompServer server = doLookup();
	    AdvCompService service = server.connexion(DEV_CONFIG.toto.toString(), DEV_CONFIG.secret.toString());

	    System.out.println("Connection suceeded");
	    System.out.println(service.faireOperationBasique(10d, 3d, "+"));
	    System.out.println(service.faireOperationBasique(10d, 3d, "-"));
	    System.out.println(service.faireOperationBasique(10d, 3d, "*"));
	    System.out.println(service.faireOperationBasique(10d, 3d, "/"));

	} catch (NamingException e) {
	    e.printStackTrace();
	}

    }

    private static AdvCompServer doLookup() throws NamingException {
	// Set des properties du context
	try {

	    AdvCompServer bean = (AdvCompServer) InitialContext.doLookup("edu.bd.advcomp.core.service.AdvCompServer");
	    return bean;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
