// File ConnexionInterceptor.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.interceptors;

import javax.ejb.Local;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Intercepteur de connexion.
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface ConnexionInterceptor {

    /**
     * intercept connexion
     *
     * @param ic
     * @return
     * @throws Exception
     */
    Object interceptConnexion(InvocationContext ic) throws Exception;

}