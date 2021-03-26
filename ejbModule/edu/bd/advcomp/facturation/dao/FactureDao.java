// File FactureDao.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.dao;

import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.framework.persistence.EntityDao;

/**
 * DAO pour la maintenance des {@link Facture}
 * 
 * @author Brique DECKARD
 *
 */
// TODO :  Annotation @Local ?
public interface FactureDao extends EntityDao<Facture, String> {
}
