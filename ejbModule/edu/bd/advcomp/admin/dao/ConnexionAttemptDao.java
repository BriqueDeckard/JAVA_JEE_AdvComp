// File ConnexionAttemptDao.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.framework.persistence.EntityDao;

/**
 * Dao pour la persistence des entités {@link ConnexionAttempt}
 * 
 * @author Brique DECKARD
 *
 */
@Local
public interface ConnexionAttemptDao extends EntityDao<ConnexionAttempt, String> {

    public static String JNDI = "java:global/AdvCompEjb/ConnexionAttemptDaoSql!edu.bd.advcomp.admin.dao.ConnexionAttemptDao";



    /**
     * retrieveAllBetween
     *
     * retrouve les connexions qui ont eu lieu entre deux dates
     * @param bDate
     * @param eDate
     * @return
     */
    List<ConnexionAttempt> retrieveAllBetween(Date bDate, Date eDate);
}
