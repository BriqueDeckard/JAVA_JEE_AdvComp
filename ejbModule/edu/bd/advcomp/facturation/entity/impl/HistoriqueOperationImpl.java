// File HistoriqueOperationImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.entity.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Entity
@Table(name = "historique")
@NamedQueries({ @NamedQuery(name = "liste_historique", query = "SELECT h FROM HistoriqueOperationImpl h"),
	@NamedQuery(name = "liste_historique_between", query = "SELECT h FROM HistoriqueOperationImpl h WHERE h.date >= :beginDateTime AND h.facture IS NULL ")

})
public class HistoriqueOperationImpl implements HistoriqueOperation {
    /**
     * UID for class.
     */
    private static final long serialVersionUID = 8954103111843672345L;
    /**
     * Identifiant marqué de @Id, indepensable.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    private Long id;
    /**
     * @Temporal
     */
    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    /**
     * - @ManyToOne parce que Historique connait Utilisateur, mais Utilisateur ne
     * connait pas Historique - "targetEntity" parce que l'entité visée est
     * représentée par une interface - nullable à false, parce qu'un historique ne
     * peut pas être créé sans utilisateur
     */
    @ManyToOne(targetEntity = UtilisateurImpl.class)
    @JoinColumn(name = "fk_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    /**
     * - @ManyToOne parce que Historique connait Facture, mais Facture ne connait
     * pas Historique - "targetEntity" parce que l'entité visée est représentée par
     * une interface - nullable à true parce que la facture peut être nulle à la
     * création de Historique
     */
    @ManyToOne(targetEntity = FactureImpl.class)
    @JoinColumn(name = "fk_facture", nullable = true)
    private Facture facture;

    /**
     * Constructor for HistoriqueOperationImpl
     *
     * @param id
     * @param date
     * @param description
     * @param utilisateur
     * @param facture
     */
    public HistoriqueOperationImpl(Long id, Date date, String description, Utilisateur utilisateur) {
	super();
	this.id = id;
	this.date = date;
	this.description = description;
	this.utilisateur = utilisateur;
    }

    /**
     * Constructor for HistoriqueOperationImpl
     *
     */
    public HistoriqueOperationImpl() {
    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.HistoriqueOperation#getId()
     *
     * @return
     */
    @Override
    public Long getId() {
	return this.id;
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#setId(java.lang.Long)
     *
     * @param id
     */
    @Override
    public void setId(Long id) {
	this.id = id;

    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.HistoriqueOperation#getDate()
     *
     * @return
     */
    @Override
    public Date getDate() {
	return this.date;
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#setDate(java.util.Date)
     *
     * @param date
     */
    @Override
    public void setDate(Date date) {
	this.date = date;

    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#getDescription()
     *
     * @return
     */
    @Override
    public String getDescription() {
	return this.description;
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#setDescription(java.lang.String)
     *
     * @param description
     */
    @Override
    public void setDescription(String description) {
	this.description = description;

    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#getUtilisateur()
     *
     * @return
     */
    @Override
    public Utilisateur getUtilisateur() {
	return this.utilisateur;
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#setUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)
     *
     * @param utilisateur
     */
    @Override
    public void setUtilisateur(Utilisateur utilisateur) {
	this.utilisateur = utilisateur;

    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.HistoriqueOperation#getFacture()
     *
     * @return
     */
    @Override
    public Facture getFacture() {
	return this.facture;
    }

    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "HistoriqueOperationImpl [id=" + this.id + ", date=" + this.date + ", description=" + this.description
		+ ", utilisateur=" + this.utilisateur + ", facture=" + this.facture + "]";
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.HistoriqueOperation#setFacture(edu.bd.advcomp.facturation.entity.Facture)
     *
     * @param facture
     */
    @Override
    public void setFacture(Facture facture) {
	this.facture = facture;

    }
}
