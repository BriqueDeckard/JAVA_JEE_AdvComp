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
import edu.bd.advcomp.AdvcompException;
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

    @PersistenceContext(unitName = "advcomp")
    EntityManager em;

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
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public Facture retrieve(String id) throws AdvcompException {
	System.out.println("RETRIEVE Facture " + id);
	if (id.isEmpty()) {
	    throw new AdvcompException("ERROR : Id is null");
	}
	try {
	    Facture facture = em.find(FactureImpl.class, id);
	    return facture;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#getNew()
     *
     * @return
     * @throws AdvcompException
     */
    @Override
    public Facture getNew() throws AdvcompException {
	return new FactureImpl();
    }


    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieveAll()
     *
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public List<Facture> retrieveAll() throws AdvcompException, Exception {
	Query query = em.createNamedQuery("liste_factures", Facture.class);
	List<Facture> list = query.getResultList();
	System.out.println("List size : " + list.size());
	return list;
    }
    
    
}
