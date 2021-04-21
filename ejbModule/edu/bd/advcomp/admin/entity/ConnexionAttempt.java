// File ConnexionAttempt.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.entity;

import java.util.Date;

import edu.bd.framework.persistence.Entity;

/**
 * Stocke une tentative de connexion
 * 
 * @author Brique DECKARD
 *
 */
public interface ConnexionAttempt extends Entity {
    /**
     * get corresponding Login
     *
     * @return
     */
    String getLogin();

    /**
     * set Login
     *
     */
    void setLogin(String login);

    /**
     * get connexion Status
     *
     * @return
     */
    Boolean getStatus();

    /**
     * set Status
     *
     */
    void setStatus(Boolean status);

    /**
     * get connexion Date
     *
     * @return
     */
    Date getDate();

    /**
     * set Date
     *
     */
    void setDate(Date date);


}
