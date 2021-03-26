// File CalculateurService.java - No copyright - 23 mars 2021
package edu.bd.advcomp.calcul.service;

import edu.bd.advcomp.calcul.CalculException;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Annotation @Local ?
public interface CalculateurService {
    /**
     * Realiser une addition
     * 
     * @param facteur1
     * @param facteur2
     * @return le resultat de l'addition des deux facteurs
     * @throws CalculException
     */
    public Double additionner(Double facteur1, Double facteur2) throws CalculException;

    /**
     * Realiser une soustraction
     * 
     * @param facteur1
     * @param facteur2
     * @return le resultat de la soustraction de facteur2 à facteur1
     */
    public Double soustraire(Double facteur1, Double facteur2) throws CalculException;

    /**
     * Realiser une multiplication
     * 
     * @param facteur1
     * @param facteur2
     * @return le resultat de la multiplication des deux facteurs
     */
    public Double multiplier(Double facteur1, Double facteur2) throws CalculException;

    /**
     * Realiser une division
     * 
     * @param facteur1
     * @param facteur2
     * @return le resultat de la division de facteur1 par facteur2
     */
    public Double diviser(Double facteur1, Double facteur2) throws CalculException;
}
