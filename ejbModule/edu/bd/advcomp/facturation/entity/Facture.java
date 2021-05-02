// File facture.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.entity;

import java.util.Date;

import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.framework.persistence.Entity;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public interface Facture extends Entity<String> {
    /**
     * Do getUtilisateur TODO : Fill method utility
     * 
     * @return
     */
    public Utilisateur getUtilisateur();

    /**
     * Do setUtilisateur TODO : Fill method utility
     * 
     * @param utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur);

    /**
     * Do getDate TODO : Fill method utility
     * 
     * @return
     */
    public Date getDate();

    /**
     * Do setDate TODO : Fill method utility
     * 
     * @param date
     */
    public void setDate(Date date);

    /**
     * Do getMontant TODO : Fill method utility
     * 
     * @return
     */
    public double getMontant();

    /**
     * Do setMontant TODO : Fill method utility
     * 
     * @param montant
     */
    public void setMontant(double montant);

    /**
     * Do isSoldee TODO : Fill method utility
     * 
     * @return
     */
    public boolean isSoldee();

    /**
     * Do setSoldee TODO : Fill method utility
     * 
     * @param soldee
     */
    public void setSoldee(boolean soldee);
}
