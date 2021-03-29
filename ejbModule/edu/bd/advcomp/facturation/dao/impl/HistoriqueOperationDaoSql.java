// File HistoriqueOperationDaoSql.java - No copyright - 29 mars 2021
package edu.bd.advcomp.facturation.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;

/**
 * TODO Fill type utility
 * @author Brique DECKARD
 *
 */
@Stateless
public class HistoriqueOperationDaoSql implements HistoriqueOperationDao {

    /**
     * Constructor for HistoriqueOperationDaoSql
     *
     */
    public HistoriqueOperationDaoSql() {
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#create(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public HistoriqueOperation create(HistoriqueOperation entity) throws AdvcompException, Exception {
	// TODO Fill method utility.
	return null;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvcompException
     */
    @Override
    public HistoriqueOperation retrieve(Long id) throws AdvcompException {
	// TODO Fill method utility.
	return null;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#update(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public HistoriqueOperation update(HistoriqueOperation entity) throws AdvcompException, Exception {
	// TODO Fill method utility.
	return null;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#delete(java.lang.Object)
     *
     * @param entity
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public void delete(HistoriqueOperation entity) throws AdvcompException, Exception {
	// TODO Fill method utility.

    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#getNew()
     *
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public HistoriqueOperation getNew() throws AdvcompException, Exception {
	// TODO Fill method utility.
	return null;
    }

    /**
     * See @see edu.bd.advcomp.facturation.dao.HistoriqueOperationDao#getOperationAFacturer(java.util.Date, java.util.Date)
     *
     * @param dateDebut
     * @param datefin
     * @return
     */
    @Override
    public List<HistoriqueOperation> getOperationAFacturer(Date dateDebut, Date datefin) {
	// TODO Fill method utility.
	return null;
    }
    // TODO : Fill class body
}
