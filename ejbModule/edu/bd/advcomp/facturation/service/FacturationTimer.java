// File FacturationTimer.java - No copyright - 11 avr. 2021
package edu.bd.advcomp.facturation.service;

import javax.ejb.Local;

/**
 * Timer bean to schedule facturation
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface FacturationTimer {

    /**
     * scheduled facturation
     *
     * @throws InterruptedException
     */
    void scheduledFacturation() throws InterruptedException;

}
