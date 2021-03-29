// File AdvCompServerImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import java.util.Properties;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.AuthentificationService;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateful
public class AdvCompServerImpl implements AdvCompServer {

    /**
     * Service d'authentification
     */
    @Inject
    private AuthentificationService authentificationService;

    /**
     * Service de gestion des utilisateurs
     */
    @Inject
    private UtilisateurService utilisateurService;

    /**
     * Constructor for AdvCompServerImpl
     *
     */
    public AdvCompServerImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompServer#connexion(java.lang.String,
     * java.lang.String)
     *
     * @param login @param password @return @throws AdvcompException @throws
     */
    @Override
    public AdvCompService connexion(String login, String password) throws AdvcompException {

	if (!this.authentificationService.authentifier(login, password)) {
	    throw new AdvcompException("Echec lors de l'authentification");
	}
	Utilisateur client = utilisateurService.obtenirUtilisateur(login);
	if(client == null) {
	    throw new AdvcompException("Client null !");
	}

	// [ LOOKUP ]
	try {
	    Properties props = new Properties();
	    props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
	    props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
	    props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

	    InitialContext ctx = new InitialContext(props);
	    AdvCompService remoteService = (AdvCompService) ctx.lookup("edu.bd.advcomp.core.service.AdvCompService");
	    
	    // Set du client
	    remoteService.setClient(client);
	    
	    System.out.println("Lookup succeeded.");
	    return remoteService;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;

    }

}
