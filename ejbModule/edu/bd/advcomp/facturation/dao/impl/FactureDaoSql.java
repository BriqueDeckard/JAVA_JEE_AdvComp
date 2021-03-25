// File FactureDaoSql.java - No copyright - 24 mars 2021
package edu.bd.advcomp.facturation.dao.impl;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.facturation.dao.FactureDao;
import edu.bd.advcomp.facturation.entity.Facture;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class FactureDaoSql implements FactureDao {

    /**
     * Transaction 
     */
    @Resource
    private UserTransaction transaction;

    /**
     * Constructor for FactureDaoSql
     *
     */
    public FactureDaoSql() {
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
    public Facture create(Facture entity) throws AdvcompException, Exception {

	try {
	    transaction.begin();
	    System.out.println("INFO : Create Facture " + entity);
	    System.out.println("INFO : INSERT INTO ...");
	    // TODO : implémenter la vrai structure/persistance de données
	    transaction.commit();
	    return entity;
	} catch (Exception e) {
	    System.out.println("EXCEPT : ROLLBACK");
	    System.out.println("EXCEPT : Delete Facture " + entity);
	    transaction.rollback();
	}
	return null;
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
	System.out.println("INFO : Retrieve Facture " + id);
	System.out.println("INFO : SELECT ... WHERE ...");
	// TODO : implémenter la vrai structure/persistance de données
	return null;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#update(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvcompException
     */
    @Override
    public Facture update(Facture entity) throws AdvcompException, Exception {
	try {
	    transaction.begin();
	    System.out.println("INFO : Update Facture " + entity);
	    System.out.println("INFO : UPDATE ... SET ... WHERE ...");
	    // TODO : implémenter la vrai structure/persistance de données
	    transaction.commit();
	} catch (Exception e) {
	    System.out.println("EXCEPT : ROLLBACK");
	    System.out.println("EXCEPT : Reverse update Facture " + entity);
	    transaction.rollback();
	}
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#delete(java.lang.Object)
     *
     * @param entity
     * @throws AdvcompException
     */
    @Override
    public void delete(Facture entity) throws AdvcompException, Exception {
	try {
	    transaction.begin();
	    System.out.println("INFO : Delete Facture " + entity);
	    System.out.println("INFO : DELETE FROM ... WHERE ...");
	    // TODO : implémenter la vrai structure/persistance de données
	    transaction.commit();
	} catch (Exception e) {
	    System.out.println("EXCEPT : ROLLBACK");
	    System.out.println("EXCEPT : Reverse delete facture " + entity);
	    transaction.rollback();
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
	// TODO : Fill method utility.
	// TODO : Injecter une entitée facture ?
	return null;
    }
    // TODO : Fill class body
}
