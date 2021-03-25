// File AuthentificationServiceStrict.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.service.impl;

import edu.bd.advcomp.authentification.service.AuthentificationService;

/**
 * Service d'authentification strict.
 * 
 * @author Brique DECKARD
 *
 */
public class AuthentificationServiceStrict implements AuthentificationService {

    /**
     * Constructor for AuthentificationServiceStrict
     */
    public AuthentificationServiceStrict() {
    }

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
	return "tutu".equals(login);
    }
}
