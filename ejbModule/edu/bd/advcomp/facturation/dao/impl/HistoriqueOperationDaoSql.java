// File HistoriqueOperationDaoSql.java - No copyright - 29 mars 2021
package edu.bd.advcomp.facturation.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.QueryParam;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.facturation.dao.HistoriqueOperationDao;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.advcomp.facturation.entity.impl.HistoriqueOperationImpl;
import edu.bd.framework.persistence.EntityDaoImpl;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class HistoriqueOperationDaoSql extends EntityDaoImpl<HistoriqueOperation, Long>
	implements HistoriqueOperationDao {

//    @PersistenceContext(unitName = "advcomp")
//    EntityManager em;

    /**
     * Constructor for HistoriqueOperationDaoSql
     *
     */
    public HistoriqueOperationDaoSql() {
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvCompException
     */
    @Override
    public HistoriqueOperation retrieve(Long id) throws AdvCompException {
	System.out.println("RETRIEVE HistoriqueOperation " + id);
	if (id == null) {
	    throw new AdvCompException("ERROR : Id is null");
	}
	try {
	    HistoriqueOperation entity = this.getEm().find(HistoriqueOperationImpl.class, id);
	    return entity;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvCompException(e);
	}
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#getNew()
     *
     * @return
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public HistoriqueOperation getNew() throws AdvCompException, Exception {
	return new HistoriqueOperationImpl();
    }

    /**
     * See @see
     * edu.bd.advcomp.facturation.dao.HistoriqueOperationDao#getOperationAFacturer(java.util.Date,
     * java.util.Date)
     *
     * @param dateDebut
     * @param datefin
     * @return
     */
    @Override
    public List<HistoriqueOperation> getOperationAFacturer(Date dateDebut, Date datefin) {
	System.out.println("---------------------------------------------------");
	System.out.println("INFO : Get operation a facturer");

	Query query = this.getEm().createNamedQuery("liste_historique_between", HistoriqueOperation.class);
	query.setParameter("beginDateTime", dateDebut);
	List<HistoriqueOperation> list = query.getResultList();

	return list;
    }

    /**
     * See @see edu.bd.advcomp.facturation.dao.HistoriqueOperationDao#retrieveAll()
     *
     * @return
     */
    @Override
    public List<HistoriqueOperation> retrieveAll() {
	Query query = this.getEm().createNamedQuery("liste_historique", HistoriqueOperation.class);
	List<HistoriqueOperation> list = query.getResultList();
	return list;

    }
}
