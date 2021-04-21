// File ConnexionAttemptDaoSql.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.admin.dao.ConnexionAttemptDao;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.admin.entity.impl.ConnexionAttemptImpl;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.advcomp.facturation.entity.impl.FactureImpl;
import edu.bd.framework.persistence.EntityDaoImpl;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Stateless
public class ConnexionAttemptDaoSql extends EntityDaoImpl<ConnexionAttempt, String> implements ConnexionAttemptDao {

    /**
     * Constructor for ConnexionAttemptDaoSql
     *
     */
    public ConnexionAttemptDaoSql() {
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieveAll()
     *
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public List<ConnexionAttempt> retrieveAll() throws AdvcompException, Exception {
	Query query = this.getEm().createNamedQuery("liste_connexions", ConnexionAttempt.class);
	List<ConnexionAttempt> list = query.getResultList();
	return list;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDaoImpl#getNew()
     *
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public ConnexionAttempt getNew() throws AdvcompException, Exception {
	return new ConnexionAttemptImpl();
    }

    /**
     * See @see
     * edu.bd.framework.persistence.EntityDaoImpl#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvcompException
     */
    @Override
    public ConnexionAttempt retrieve(String id) throws AdvcompException {
	System.out.println(this.getClass().getSimpleName() + "RETRIEVE Connexion attempt : " + id);
	if (id.isEmpty()) {
	    throw new AdvcompException("ERROR : Id is null");
	}
	try {
	    ConnexionAttempt connexion = this.getEm().find(ConnexionAttemptImpl.class, id);
	    return connexion;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
    }

    /**
     * See @see edu.bd.advcomp.admin.dao.ConnexionAttemptDao#retrieveAllBetween()
     *
     * @return
     */
    @Override
    public List<ConnexionAttempt> retrieveAllBetween(Date bDate, Date eDate) {
	Query query = this.getEm().createNamedQuery("list_connexion_for", ConnexionAttempt.class);
	query.setParameter("bDate", bDate);
	query.setParameter("eDate", eDate);
	List<ConnexionAttempt> list = query.getResultList();
	return list;
    }

}
