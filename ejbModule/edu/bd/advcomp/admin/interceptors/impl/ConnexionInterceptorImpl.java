// File ConnexionInterceptor.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.interceptors.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import edu.bd.advcomp.admin.interceptors.ConnexionInterceptor;
import edu.bd.advcomp.authentification.entity.Utilisateur;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class ConnexionInterceptorImpl implements ConnexionInterceptor {

    /**
     * See @see
     * edu.bd.advcomp.admin.interceptors.ConnexionInterceptor#interceptConnexion(javax.interceptor.InvocationContext)
     *
     * @param ic
     * @return
     * @throws Exception
     */
    @Override
    @AroundInvoke
    public Object interceptConnexion(InvocationContext ic) throws Exception {

	System.out.println("\t" + this.getClass().getSimpleName() + " INTERCEPT " + ic.getMethod().getName());
	System.out.println("\t" + ic.getParameters());
	if (ic.getMethod().getName() == "connexion") {
	    try {
		String login = (String) ic.getParameters()[0];
		String secret = (String) ic.getParameters()[1];
		System.out.println("TENTATIVE DE CONNEXION DE : " + login);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}	

	return ic.proceed();
    }

}
