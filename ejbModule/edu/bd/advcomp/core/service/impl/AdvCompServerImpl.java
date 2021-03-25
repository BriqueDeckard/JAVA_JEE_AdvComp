// File AdvCompServerImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import javax.inject.Inject;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.AuthentificationService;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.core.service.AdvCompServiceFactory;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
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
     * Factory for services
     */
    @Inject
    private AdvCompServiceFactory advCompServiceFactory;

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
     * @param login
     * @param password
     * @return
     * @throws AdvcompException
     */
    @Override
    public AdvCompService connexion(String login, String password) throws AdvcompException {
	if (!this.authentificationService.authentifier(login, password)) {
	    throw new AdvcompException("Echec lors de l'authentification");
	}
	Utilisateur client = utilisateurService.obtenirUtilisateur(login);
	return advCompServiceFactory.createAdvCompService(client);

    }

}
