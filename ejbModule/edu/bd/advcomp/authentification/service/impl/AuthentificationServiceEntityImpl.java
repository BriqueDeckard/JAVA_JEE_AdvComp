// File AuthentificationServiceEntityImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.service.impl;

import javax.inject.Inject;

import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.AuthentificationService;
import edu.bd.advcomp.authentification.service.UtilisateurService;

/**
 * Implémentation du service d'authentification
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation Stateful ?
public class AuthentificationServiceEntityImpl implements AuthentificationService {

    /**
     * Constructor for AuthentificationServiceEntityImpl
     */
    public AuthentificationServiceEntityImpl() {
    }

    /**
     * Service de getion des utilisateurs
     */
    @Inject
    private UtilisateurService utilisateurService;

    /**
     * See @see
     * edu.bd.advcomp.authentification.service.AuthentificationService#authentifier(java.lang.String,
     * java.lang.String)
     * 
     * @param login
     * @param password
     * @return
     */
    @Override
    public Boolean authentifier(String login, String password) {
	try {
	    Utilisateur utilisateur = utilisateurService.obtenirUtilisateur(login);
	    return utilisateur != null && password != null && utilisateur.getPassword().equals(password);

	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }
}
