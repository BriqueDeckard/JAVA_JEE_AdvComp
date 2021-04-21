// File FacturationSignalEvent.java - No copyright - 11 avr. 2021
package edu.bd.advcomp.facturation.event;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class FacturationSignalEvent implements Serializable {

    /**
     * TODO : Fill field utility
     */
    private static final long serialVersionUID = -911646795139613014L;

    /**
     * Gets dateDebut
     * 
     * @return the dateDebut
     */
    public Date getDateDebut() {
	return this.dateDebut;
    }

    /**
     * Gets dateFin
     * 
     * @return the dateFin
     */
    public Date getDateFin() {
	return this.dateFin;
    }

    /**
     * Constructor for FacturationSignalEvent
     *
     * @param dateDebut
     * @param dateFin
     */
    public FacturationSignalEvent(Date dateDebut, Date dateFin) {
	super();
	this.dateDebut = dateDebut;
	this.dateFin = dateFin;
    }

    private Date dateDebut;
    private Date dateFin;

    /**
     * Constructor for FacturationSignalEvent
     *
     */
    private FacturationSignalEvent() {
    }
    // TODO : Fill class body

    /**
     * See @see java.lang.Object#toString()
     *
     * @return
     */
    @Override
    public String toString() {
	return "FacturationSignalEvent [dateDebut=" + this.dateDebut + ", dateFin=" + this.dateFin + "]";
    }




}
