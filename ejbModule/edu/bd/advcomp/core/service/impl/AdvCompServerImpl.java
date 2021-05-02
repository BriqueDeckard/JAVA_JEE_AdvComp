// File AdvCompServerImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.event.ConnexionEvent;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.Role;
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
@Interceptors({ edu.bd.advcomp.admin.interceptors.impl.ConnexionInterceptorImpl.class })
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
     * Permet de publier un event pour la connexion.
     */
    @Inject
    Event<ConnexionEvent> connexionEvents;

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompServer#connexion(java.lang.String,
     * java.lang.String)
     *
     * @param login @param password @return @throws AdvcompException @throws
     */
    @Override
    public AdvCompService connexion(String login, String password) throws AdvCompException {

	if (!this.authentificationService.authentifier(login, password)) {
	    connexionEvents.fire(new ConnexionEvent(login, false));
	    AdvCompException exception = new AdvCompException("Echec lors de l'authentification");
	    exception.printStackTrace();
	    System.out.println("Identification échouée.");
	    return null;
	}
	Utilisateur client = utilisateurService.obtenirUtilisateur(login);
	if (client == null) {
	    connexionEvents.fire(new ConnexionEvent(login, false));
	    System.out.println("Identification échouée.");
	    throw new AdvCompException("Client null !");
	}

	if (!client.getIsActive()) {
	    connexionEvents.fire(new ConnexionEvent(login, false));
	    System.out.println("Identification échouée.");
	    throw new AdvCompException("Client non actif.");
	}

	AdvCompService remoteService = doAdvCompServiceLookup();

	// Set du client
	remoteService.setClient(client);

	System.out.println(this.getClass().getSimpleName() + " - Connexion succeeded.");
	connexionEvents.fire(new ConnexionEvent(login, true));
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
     * @throws AdvCompException
     */
    @Override
    public AdvCompAdminService connexionAsAdmin(String login, String password) throws AdvCompException {
	if (!this.authentificationService.authentifier(login, password)) {
	    AdvCompException exception = new AdvCompException("Echec lors de l'authentification");
	    // exception.printStackTrace();
	    System.out.println("Identification échouée.");
	    return null;
	}
	Utilisateur admin = utilisateurService.obtenirUtilisateur(login);
	if (admin == null) {
	    AdvCompException exception = new AdvCompException("Echec lors de l'authentification");
	    // exception.printStackTrace();
	    System.out.println("Identification échouée.");
	    return null;
	}

	if (!admin.getIsActive()) {
	    AdvCompException exception = new AdvCompException("Echec lors de l'authentification");
	    // exception.printStackTrace();
	    System.out.println("Client non actif.");
	    return null;

	}

	if (admin.getRole() != Role.ADMINISTRATEUR) {
	    throw new AdvCompException("Pas admin");
	}

	AdvCompAdminService remoteService = doAdvCompAdminServiceLookup();

	// Set de l'admin
	remoteService.setAdmin(admin);
	System.out.println(this.getClass().getSimpleName() + " - Connexion succeeded.");
	return remoteService;

    }

    /**
     * do AdvComp Service Lookup
     *
     * @return
     * @throws AdvCompException
     */
    private AdvCompService doAdvCompServiceLookup() throws AdvCompException {
	try {
	    String serviceName = AdvCompService.class.getName();
	    AdvCompService remoteService = (AdvCompService) InitialContext.doLookup(serviceName);
	    // .doLookup("edu.bd.advcomp.core.service.AdvCompService");
	    return remoteService;
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvCompException(e.getMessage());
	}
    }

    /**
     * do AdvCompAdminService Lookup
     *
     * @return
     * @throws AdvCompException
     */
    private AdvCompAdminService doAdvCompAdminServiceLookup() throws AdvCompException {
	try {
	    String serviceName = AdvCompAdminService.class.getName();
	    AdvCompAdminService remoteService = (AdvCompAdminService) InitialContext.doLookup(AdvCompAdminService.JNDI);
	    return remoteService;
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvCompException(e.getMessage());
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.core.service.AdvCompServer#creerCompte(java.lang.String,
     * java.lang.String)
     *
     * @param login
     * @param password
     * @throws AdvCompException
     */
    @Override
    public void creerCompte(String nom, String login, String password, String adresse) throws AdvCompException {
	Utilisateur nouvelUtilisateur = utilisateurService.getNewUtilisateur();

	nouvelUtilisateur.setId(login);
	nouvelUtilisateur.setNom(nom);
	nouvelUtilisateur.setPassword(password);
	nouvelUtilisateur.setIsActive(false);
	nouvelUtilisateur.setAdresse(adresse);
	System.out.println("Nouvel utilisateur : " + nouvelUtilisateur.toString());
	utilisateurService.creerUtilisateur(nouvelUtilisateur);

    }

}
