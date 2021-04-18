// File FacturationTimerImpl.java - No copyright - 11 avr. 2021
package edu.bd.advcomp.facturation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import edu.bd.advcomp.facturation.event.FacturationSignalEvent;
import edu.bd.advcomp.facturation.service.FacturationTimer;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Singleton
public class FacturationTimerImpl implements FacturationTimer {

    /**
     * TODO : Fill field utility
     */
    @Inject
    Event<FacturationSignalEvent> facturationSignalEvents;

    /**
     * See @see
     * edu.bd.advcomp.facturation.service.FacturationTimer#scheduledFacturation()
     *
     * @throws InterruptedException
     */
    @Override
    @Schedule(second = "0", minute = "55", hour = "23", dayOfMonth = "28", month = "*", persistent = false)
    public void scheduledFacturation() throws InterruptedException {
	System.out.println("************************************************");
	System.out.println("FACTURATION PROGRAMMEE ");

	Calendar today = Calendar.getInstance();

	String day = Integer.toString(today.get(Calendar.DAY_OF_MONTH));
	String month = Integer.toString(today.get(Calendar.MONTH));
	String year = Integer.toString(today.get(Calendar.YEAR));

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	try {
	    Date aMonthAgo = simpleDateFormat.parse(day + "/" + month + "/" + year);
	    facturationSignalEvents.fire(new FacturationSignalEvent(aMonthAgo, new Date()));
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	System.out.println("************************************************");
    }

}
