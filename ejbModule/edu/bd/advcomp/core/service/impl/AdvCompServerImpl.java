// File AdvCompServerImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
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
	if (client == null) {
	    throw new AdvcompException("Client null !");
	}

	if (!client.getIsActive()) {
	    throw new AdvcompException("Client non actif.");
	}

	AdvCompService remoteService = doAdvCompServiceLookup();

	// Set du client
	remoteService.setClient(client);

	System.out.println(this.getClass().getSimpleName() + " - Connexion succeeded.");
	return remoteService;

    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompServer#connexionAsAdmin(java.lang.String,
     * java.lang.String)
     *
     * @param login
     * @param password
     * @return
     * @throws AdvcompException
     */
    @Override
    public AdvCompAdminService connexionAsAdmin(String login, String password) throws AdvcompException {
	if (!this.authentificationService.authentifier(login, password)) {
	    throw new AdvcompException("Echec lors de l'authentification");
	}
	Utilisateur admin = utilisateurService.obtenirUtilisateur(login);
	if (admin == null) {
	    throw new AdvcompException("Client null !");
	}

	if (!admin.getIsActive()) {
	    throw new AdvcompException("Client non actif.");
	}

	if (admin.getRole() != Role.ADMINISTRATEUR) {
	    throw new AdvcompException("Pas admin");
	}

	AdvCompAdminService remoteService = doAdvCompAdminServiceLookup();

	// Set de l'admin
	remoteService.setAdmin(admin);
	System.out.println(this.getClass().getSimpleName() + " - Connexion succeeded.");
	return remoteService;

    }

    private AdvCompService doAdvCompServiceLookup() throws AdvcompException {
	try {
	    String serviceName = AdvCompService.class.getName();
	    AdvCompService remoteService = (AdvCompService) InitialContext.doLookup(serviceName);
	    // .doLookup("edu.bd.advcomp.core.service.AdvCompService");
	    return remoteService;
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvcompException(e.getMessage());
	}
    }

    private AdvCompAdminService doAdvCompAdminServiceLookup() throws AdvcompException {
	try {
	    String serviceName = AdvCompAdminService.class.getName();
	    AdvCompAdminService remoteService = (AdvCompAdminService) InitialContext.doLookup(
		    "java:global/AdvCompEjb/AdvCompAdminServiceImpl!edu.bd.advcomp.admin.service.AdvCompAdminService");
	    // .doLookup("edu.bd.advcomp.core.service.AdvCompService");
	    return remoteService;
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvcompException(e.getMessage());
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompServer#creerCompte(java.lang.String,
     * java.lang.String)
     *
     * @param login
     * @param password
     * @throws AdvcompException
     */
    @Override
    public void creerCompte(String nom, String login, String password, String adresse) throws AdvcompException {
	Utilisateur nouvelUtilisateur = utilisateurService.getNewUtilisateur();

	nouvelUtilisateur.setLogin(login);
	nouvelUtilisateur.setNom(nom);
	nouvelUtilisateur.setPassword(password);
	nouvelUtilisateur.setIsActive(false);
	nouvelUtilisateur.setAdresse(adresse);

	// TODO : implémenter validation utilisateur !!
	utilisateurService.creerUtilisateur(nouvelUtilisateur);

    }

}
