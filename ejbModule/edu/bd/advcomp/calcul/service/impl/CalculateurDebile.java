// File CalculateurDebile.java - No copyright - 23 mars 2021
package edu.bd.advcomp.calcul.service.impl;

import edu.bd.advcomp.calcul.CalculException;
import edu.bd.advcomp.calcul.service.CalculateurService;

/**
 * Implémentation pas du tout raisonnable du Calculateur 
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation @Stateless ?
public class CalculateurDebile implements CalculateurService {

    /**
     * Constructor for CalculateurDebile
     */
    public CalculateurDebile() {
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
	return 11d;
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
	return 13d;
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
	return 17d;
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
	return 19d;
    }
}
