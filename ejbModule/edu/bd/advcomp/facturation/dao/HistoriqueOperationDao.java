// File HistoriqueOperationDao.java - No copyright - 23 mars 2021
package edu.bd.advcomp.facturation.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.framework.persistence.EntityDao;

/**
 * DAO pour la maintenance des {@link HistoriqueOperation}
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface HistoriqueOperationDao extends EntityDao<HistoriqueOperation, Long> {
    /**
     * Obtenir les operations à facturer
     * 
     * @param dateDebut
     * @param datefin
     * @return
     */
    List<HistoriqueOperation> getOperationAFacturer(Date dateDebut, Date datefin);

}
