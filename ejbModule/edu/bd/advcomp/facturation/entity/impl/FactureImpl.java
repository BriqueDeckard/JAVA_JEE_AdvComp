// File FactureImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.entity.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "facture")
public class FactureImpl implements Facture {
    // TODO : fill class

    /**
     * Utilisateur lié à la facture
     * - @ManyToOne parce que Facture connait utilisateur, mais utilisateur ne connait pas facture
     * - nullable à false parce qu'une facture n'existe pas sans utilisateur
     * - target entity parce que l'entité est accessible via une interface
     */
    @ManyToOne(targetEntity = UtilisateurImpl.class, cascade = {CascadeType.ALL} )
    @JoinColumn(name = "fk_utilisateur", nullable = false)
    private Utilisateur utilisateur;
    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "FactureImpl [utilisateur=" + this.utilisateur + ", numero=" + this.numero + ", date=" + this.date
		+ ", montant=" + this.montant + ", isSoldee=" + this.isSoldee + "]";
    }

    /**
     * Numéro de la facture - Clé primaire
     */
    @Id
    private String numero;
    /**
     * Date de la facture
     */
    @Temporal(TemporalType.DATE)
    private Date date;
    /**
     * montant de la facture
     */
    private double montant;
    /**
     * La facture est elle soldée ?
     */
    private boolean isSoldee;

    /**
     * Constructor for FactureImpl
     *
     * @param utilisateur
     * @param numero
     * @param date
     * @param montant
     * @param isSoldee
     */
    public FactureImpl(Utilisateur utilisateur, String numero, Date date, double montant, boolean isSoldee) {
	super();
	this.utilisateur = utilisateur;
	this.numero = numero;
	this.date = date;
	this.montant = montant;
	this.isSoldee = isSoldee;
    }

    /**
     * Constructor for FactureImpl
     *
     */
    public FactureImpl() {
    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#getUtilisateur()
     *
     * @return
     */
    @Override
    public Utilisateur getUtilisateur() {
	return this.utilisateur;
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.Facture#setUtilisateur(edu.bd.advcomp.authentification.entity.Utilisateur)
     *
     * @param utilisateur
     */
    @Override
    public void setUtilisateur(Utilisateur utilisateur) {
	this.utilisateur = utilisateur;

    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#getNumero()
     *
     * @return
     */
    @Override
    public String getNumero() {
	return this.numero;
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.entity.Facture#setNumero(java.lang.String)
     *
     * @param numero
     */
    @Override
    public void setNumero(String numero) {
	this.numero = numero;

    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#getDate()
     *
     * @return
     */
    @Override
    public Date getDate() {
	return this.date;
    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#setDate(java.util.Date)
     *
     * @param date
     */
    @Override
    public void setDate(Date date) {
	this.date = date;

    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#getMontant()
     *
     * @return
     */
    @Override
    public double getMontant() {
	return this.montant;
    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#setMontant(double)
     *
     * @param montant
     */
    @Override
    public void setMontant(double montant) {
	this.montant = montant;

    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#isSoldee()
     *
     * @return
     */
    @Override
    public boolean isSoldee() {
	return this.isSoldee;
    }

    /**
     * See @see edu.bd.advcomp.facturation.entity.Facture#setSoldee(boolean)
     *
     * @param soldee
     */
    @Override
    public void setSoldee(boolean soldee) {
	this.isSoldee = soldee;

    }


}
