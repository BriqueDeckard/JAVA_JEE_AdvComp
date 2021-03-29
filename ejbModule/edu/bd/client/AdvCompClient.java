// File AdvCompClient.java - No copyright - 28 mars 2021
package edu.bd.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.DEV_CONFIG;
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
	    AdvCompService service = server.connexion(DEV_CONFIG.USERNAME.toString(), DEV_CONFIG.PASSWORD.toString());
	    
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
	Properties props = new Properties();
	props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
	props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
	props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

	try {
	    // Obtention du contexte
	    InitialContext context = new InitialContext(props);
	    // Obtention du bean
	    AdvCompServer bean = (AdvCompServer) context.doLookup("edu.bd.advcomp.core.service.AdvCompServer");
	    return bean;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
