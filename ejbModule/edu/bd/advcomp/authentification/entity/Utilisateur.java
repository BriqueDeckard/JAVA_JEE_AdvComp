// File Utilisateur.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.entity;

import edu.bd.advcomp.authentification.Role;
import edu.bd.framework.persistence.Entity;

/**
 * Interface pour l'entitée Utilisateur
 * 
 * @author Brique DECKARD
 *
 */
public interface Utilisateur extends Entity {
    /**
     * getLogin Retourne le Login
     * 
     * @return
     */
    String getLogin();

    /**
     * setLogin Définit le login
     * 
     * @param login
     */
    void setLogin(String login);

    /**
     * getPassword Retourne le mot de passe
     * 
     * @return
     */
    String getPassword();

    /**
     * setPassword Définit le mot de passe
     * 
     * @param password
     */
    void setPassword(String password);

    /**
     * getNom Obtenir le nom
     * 
     * @return
     */
    String getNom();

    /**
     * setNom Définit le nom
     * 
     * @param nom
     */
    void setNom(String nom);

    /**
     * getAdresse Obtenir l'adresse
     * 
     * @return
     */
    String getAdresse();

    /**
     * setAdresse Définit l'adresse
     * 
     * @param adresse
     */
    void setAdresse(String adresse);

    /**
     * getRole Obtenir le role
     * 
     * @return
     */
    Role getRole();

    /**
     * setRole Définit le rôle
     * 
     * @param role
     */
    void setRole(Role role);

}
