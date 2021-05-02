// File ConnexionAttemptImpl.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.entity.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.bd.advcomp.admin.entity.ConnexionAttempt;

/**
 * Impl for connexion attempt
 * 
 * @author Brique DECKARD
 *
 */
@Entity
@Table(name = "connexion")
@NamedQueries({ @NamedQuery(name = "liste_connexions", query = "SELECT c FROM ConnexionAttemptImpl c"),
	@NamedQuery(name = "list_connexion_for", query = "SELECT c FROM ConnexionAttemptImpl c WHERE c.date BETWEEN :bDate AND :eDate ") })
public class ConnexionAttemptImpl implements ConnexionAttempt {

    /**
     * Login de l'utilisateur connecté
     */
    private String userLogin;

    /**
     * Statut de la connexion
     */
    private Boolean connexionStatus;

    /**
     * Date de la connexion
     */
    @Temporal(TemporalType.DATE)
    private Date date = new Date();

    /**
     * Identifiant de la connexion
     */
    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    /**
     * Constructor for ConnexionAttemptImpl
     *
     */
    public ConnexionAttemptImpl() {
	System.out.println("CONNEXION : " + this.toString());
    }

    /**
     * See @see edu.bd.advcomp.admin.entity.ConnexionAttempt#getLogin()
     *
     * @return
     */
    @Override
    public String getLogin() {
	return this.userLogin;
    }

    /**
     * See @see edu.bd.advcomp.admin.entity.ConnexionAttempt#getStatus()
     *
     * @return
     */
    @Override
    public Boolean getStatus() {

	return this.connexionStatus;
    }

    /**
     * See @see edu.bd.advcomp.admin.entity.ConnexionAttempt#getDate()
     *
     * @return
     */
    @Override
    public Date getDate() {
	return this.date;
    }

    /**
     * See @see
     * edu.bd.advcomp.admin.entity.ConnexionAttempt#setLogin(java.lang.String)
     *
     * @param login
     */
    @Override
    public void setLogin(String login) {
	this.userLogin = login;

    }

    /**
     * See @see
     * edu.bd.advcomp.admin.entity.ConnexionAttempt#setStatus(java.lang.Boolean)
     *
     * @param status
     */
    @Override
    public void setStatus(Boolean status) {
	this.connexionStatus = status;

    }

    /**
     * See @see edu.bd.advcomp.admin.entity.ConnexionAttempt#setDate(java.util.Date)
     *
     * @param date
     */
    @Override
    public void setDate(Date date) {
	this.date = date;

    }

    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "ConnexionAttemptImpl [userLogin=" + this.userLogin + ", connexionStatus=" + this.connexionStatus
		+ ", date=" + this.date + ", id=" + this.id + "]";
    }

    /**
     * See @see edu.bd.framework.persistence.Entity#getId()
     *
     * @return
     */
    @Override
    public String getId() {
	return this.id;
    }

    /**
     * See @see edu.bd.framework.persistence.Entity#setId(java.lang.Object)
     *
     * @param id
     */
    @Override
    public void setId(String id) {
	this.id = id;

    }

}
