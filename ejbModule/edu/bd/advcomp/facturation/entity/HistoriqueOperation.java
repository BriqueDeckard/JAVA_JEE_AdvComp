// File HistoriqueOperation.java - No copyright - 23 mars 2021
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
// TODO : Annotation ?
public interface HistoriqueOperation extends Entity {
    /**
     * Do getId
     * TODO : Fill method utility
     * @return
     */
    Long getId();

    /**
     * Do setId
     * TODO : Fill method utility
     * @param id
     */
    void setId(Long id);

    /**
     * Do getDate
     * TODO : Fill method utility
     * @return
     */
    Date getDate();

    /**
     * Do setDate
     * TODO : Fill method utility
     * @param date
     */
    void setDate(Date date);

    /**
     * Do getDescription
     * TODO : Fill method utility
     * @return
     */
    String getDescription();

    /**
     * Do setDescription
     * TODO : Fill method utility
     * @param description
     */
    void setDescription(String description);

    /**
     * Do getUtilisateur
     * TODO : Fill method utility
     * @return
     */
    Utilisateur getUtilisateur();

    /**
     * Do setUtilisateur
     * TODO : Fill method utility
     * @param utilisateur
     */
    void setUtilisateur(Utilisateur utilisateur);

    /**
     * Do getFacture
     * TODO : Fill method utility
     * @return
     */
    public Facture getFacture();

    /**
     * Do setFacture
     * TODO : Fill method utility
     * @param facture
     */
    public void setFacture(Facture facture);
}
