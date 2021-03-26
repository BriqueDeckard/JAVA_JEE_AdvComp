// File AdvCompServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service.impl;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.core.service.AdvCompService;

/**
 * TODO Fill type utility
 * @author Brique DECKARD
 *
 */
// TODO :  Annotation @Stateful ?
public class AdvCompServiceImpl implements AdvCompService {

    /**
     * Constructor for AdvCompServiceImpl
     *
     */
    public AdvCompServiceImpl() {
    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#setClient(edu.bd.advcomp.authentification.entity.Utilisateur)
     *
     * @param client
     */
    @Override
    public void setClient(Utilisateur client) {
	// TODO Fill method utility.

    }

    /**
     * See @see edu.bd.advcomp.core.service.AdvCompService#faireOperationBasique(java.lang.Double, java.lang.Double, java.lang.String)
     *
     * @param facteur1
     * @param facteur2
     * @param operateur
     * @return
     * @throws AdvcompException
     */
    @Override
    public Double faireOperationBasique(Double facteur1, Double facteur2, String operateur) throws AdvcompException {
	// TODO Fill method utility.
	return null;
    }
    // TODO : Fill class body
}
