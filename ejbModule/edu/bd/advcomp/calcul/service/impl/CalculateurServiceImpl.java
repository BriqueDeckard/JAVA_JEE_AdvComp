// File CalculateurServiceImpl.java - No copyright - 23 mars 2021
package edu.bd.advcomp.calcul.service.impl;

import javax.ejb.Stateless;

import edu.bd.advcomp.calcul.CalculException;
import edu.bd.advcomp.calcul.service.CalculateurService;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class CalculateurServiceImpl implements CalculateurService {

    /**
     * Constructor for CalculateurServiceImpl
     */
    public CalculateurServiceImpl() {
    }

    /**
     * See @see
     * edu.bd.advcomp.calcul.service.CalculateurService#additionner(java.lang.Double,
     * java.lang.Double)
     * 
     * @param facteur1
     * @param facteur2
     * @return
     * @throws CalculException
     */
    @Override
    public Double additionner(Double facteur1, Double facteur2) throws CalculException {
	System.out.println("INFO : Additionner " + facteur1 + " + " + facteur2);
	checkFacteurs(facteur1, facteur2);
	try {
	    return facteur1 + facteur2;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new CalculException("Echec addition", e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.calcul.service.CalculateurService#soustraire(java.lang.Double,
     * java.lang.Double)
     * 
     * @param facteur1
     * @param facteur2
     * @return
     * @throws CalculException
     */
    @Override
    public Double soustraire(Double facteur1, Double facteur2) throws CalculException {
	System.out.println("INFO : Soustraire " + facteur1 + " - " + facteur2);
	checkFacteurs(facteur1, facteur2);
	try {
	    return facteur1 - facteur2;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new CalculException("Echec soustraction", e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.calcul.service.CalculateurService#multiplier(java.lang.Double,
     * java.lang.Double)
     * 
     * @param facteur1
     * @param facteur2
     * @return
     * @throws CalculException
     */
    @Override
    public Double multiplier(Double facteur1, Double facteur2) throws CalculException {
	System.out.println("INFO : Multiplier " + facteur1 + " * " + facteur2);
	checkFacteurs(facteur1, facteur2);
	try {
	    return facteur1 * facteur2;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new CalculException("Echec multiplication", e);
	}
    }

    /**
     * See @see
     * edu.bd.advcomp.calcul.service.CalculateurService#diviser(java.lang.Double,
     * java.lang.Double)
     * 
     * @param facteur1
     * @param facteur2
     * @return
     * @throws CalculException
     */
    @Override
    public Double diviser(Double facteur1, Double facteur2) throws CalculException {
	System.out.println("INFO : diviser " + facteur1 + " / " + facteur2);
	checkFacteurs(facteur1, facteur2);
	if (facteur2 == 0) {
	    throw new CalculException("La division par Zero est interdite.");
	}
	try {
	    return facteur1 / facteur2;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new CalculException("Echec division", e);
	}
    }

    /**
     * checkFacteurs
     *
     * Vérifie qu'aucun des deux facteur n'est null.
     * 
     * @param facteur1
     * @param facteur2
     * @throws CalculException
     */
    private void checkFacteurs(Double facteur1, Double facteur2) throws CalculException {
	if ((facteur1 == null) || (facteur2 == null)) {
	    throw new CalculException("L'un des facteurs est null");
	}
    }


}
