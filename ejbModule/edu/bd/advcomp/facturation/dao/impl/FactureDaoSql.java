// File FactureDaoSql.java - No copyright - 24 mars 2021
package edu.bd.advcomp.facturation.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.HistoriqueOperation;
import edu.bd.advcomp.facturation.entity.impl.FactureImpl;
import edu.bd.framework.persistence.EntityDaoImpl;

/**
 * Dao pour la maintenance des factures
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class FactureDaoSql extends EntityDaoImpl<Facture, String> implements FactureDao {

    /**
     * Constructor for FactureDaoSql
     *
     */
    public FactureDaoSql() {
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public Facture retrieve(String id) throws AdvCompException {
	System.out.println(this.getClass().getSimpleName() + "RETRIEVE Facture : " + id);
	if (id.isEmpty()) {
	    throw new AdvCompException("ERROR : Id is null");
	}
	try {
	    Facture facture = this.getEm().find(FactureImpl.class, id);
	    return facture;
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
     */
    @Override
    public Facture getNew() throws AdvCompException {
	return new FactureImpl();
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieveAll()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public List<Facture> retrieveAll() throws AdvCompException {
	Query query = this.getEm().createNamedQuery("liste_factures", Facture.class);
	List<Facture> list = query.getResultList();
	return list;
    }

}
