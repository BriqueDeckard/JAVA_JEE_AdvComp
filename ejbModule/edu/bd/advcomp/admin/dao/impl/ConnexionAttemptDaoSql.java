// File ConnexionAttemptDaoSql.java - No copyright - 21 avr. 2021
package edu.bd.advcomp.admin.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import edu.bd.advcomp.AdvCompException;
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
     * @throws AdvCompException
     */
    @Override
    public List<ConnexionAttempt> retrieveAll() throws AdvCompException {
	Query query = this.getEm().createNamedQuery("liste_connexions", ConnexionAttempt.class);
	List<ConnexionAttempt> list = query.getResultList();
	return list;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDaoImpl#getNew()
     *
     * @return
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public ConnexionAttempt getNew() throws AdvCompException, Exception {
	return new ConnexionAttemptImpl();
    }

    /**
     * See @see
     * edu.bd.framework.persistence.EntityDaoImpl#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvCompException
     */
    @Override
    public ConnexionAttempt retrieve(String id) throws AdvCompException {
	System.out.println(this.getClass().getSimpleName() + "RETRIEVE Connexion attempt : " + id);
	if (id.isEmpty()) {
	    throw new AdvCompException("ERROR : Id is null");
	}
	try {
	    ConnexionAttempt connexion = this.getEm().find(ConnexionAttemptImpl.class, id);
	    return connexion;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvCompException(e);
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
	System.out.println("BDATE : " + bDate.toString());
	query.setParameter("eDate", eDate);
	System.out.println("EDATE : " + eDate.toString());
	List<ConnexionAttempt> list = query.getResultList();
	return list;
    }

}
