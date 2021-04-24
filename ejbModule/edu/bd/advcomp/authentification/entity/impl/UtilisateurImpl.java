// File UtilisateurImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.entity.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.entity.Utilisateur;

// TODO : Pour JPA
// TODO : @Entity sur la classe 
// TODO : @Table sur la classe
// TODO : @id sur la PK
/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 * 
 */
@Entity
@Table(name = "utilisateur")
@NamedQueries({
	@NamedQuery(name = "liste_user_inactifs", query = "SELECT u from UtilisateurImpl u WHERE u.isActive = FALSE") })
public class UtilisateurImpl implements Utilisateur, Serializable {

    /**
     * TODO : Fill field utility
     */
    private static final long serialVersionUID = 8116352146061006821L;

    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "UtilisateurImpl [login=" + this.login + ", password=" + this.password + ", nom=" + this.nom
		+ ", adresse=" + this.adresse + ", role=" + this.role + ", isActive=" + this.isActive + "]";
    }

    /**
     * Constructor for UtilisateurImpl
     */
    public UtilisateurImpl() {
    }

    /**
     * Login de l'utilisateur
     * 
     * @Id pour JPA
     */
    @Id
    @GeneratedValue(generator = "uuid")
    private String login;
    /**
     * MOt de passe de l'utilisateur
     */
    private String password;
    /**
     * Nom de l'utilisateur
     */
    private String nom;
    /**
     * Adresse postale
     */
    private String adresse;
    /**
     * Rôle (Admin ou simple Clinet)
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Determine si l'utilisateur est activé ou non
     */
    private Boolean isActive = false;

    /**
     * See @see edu.bd.advcomp.authentification.entity.Utilisateur#getLogin()
     *
     * @return
     */
    @Override
    public String getLogin() {
	return login;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.entity.Utilisateur#setLogin(java.lang.String)
     *
     * @param login
     */
    @Override
    public void setLogin(String login) {
	this.login = login;
    }

    /**
     * See @see edu.bd.advcomp.authentification.entity.Utilisateur#getPassword()
     *
     * @return
     */
    @Override
    public String getPassword() {
	return password;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.entity.Utilisateur#setPassword(java.lang.String)
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * See @see edu.bd.advcomp.authentification.entity.Utilisateur#getNom()
     *
     * @return
     */
    @Override
    public String getNom() {
	return nom;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.entity.Utilisateur#setNom(java.lang.String)
     *
     * @param nom
     */
    @Override
    public void setNom(String nom) {
	this.nom = nom;
    }

    /**
     * See @see edu.bd.advcomp.authentification.entity.Utilisateur#getAdresse()
     *
     * @return
     */
    @Override
    public String getAdresse() {
	return adresse;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.entity.Utilisateur#setAdresse(java.lang.String)
     *
     * @param adresse
     */
    @Override
    public void setAdresse(String adresse) {
	this.adresse = adresse;
    }

    /**
     * See @see edu.bd.advcomp.authentification.entity.Utilisateur#getRole()
     *
     * @return
     */
    @Override
    public Role getRole() {
	return role;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.entity.Utilisateur#setRole(edu.bd.advcomp.authentification.Role)
     *
     * @param role
     */
    @Override
    public void setRole(Role role) {
	this.role = role;
    }

    /**
     * See @see edu.bd.advcomp.authentification.entity.Utilisateur#getIsActive()
     *
     * @return
     */
    @Override
    public Boolean getIsActive() {
	return this.isActive;
    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.entity.Utilisateur#setIsActive(boolean)
     *
     * @param active
     */
    @Override
    public void setIsActive(boolean active) {
	this.isActive = active;

    }

    /**
     * Constructor for UtilisateurImpl
     *
     * @param login
     * @param password
     * @param nom
     * @param adresse
     * @param role
     * @param isActive
     */
    public UtilisateurImpl(String login, String password, String nom, String adresse, Role role, Boolean isActive) {
	super();
	this.login = login;
	this.password = password;
	this.nom = nom;
	this.adresse = adresse;
	this.role = role;
	this.isActive = isActive;
    }

}
