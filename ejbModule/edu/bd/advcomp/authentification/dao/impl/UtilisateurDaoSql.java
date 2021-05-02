// File UtilisateurDaoSql.java - No copyright - 23 mars 2021 package
package edu.bd.advcomp.authentification.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.DEV_CONFIG;
import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.advcomp.facturation.entity.Facture;
import edu.bd.framework.persistence.EntityDaoImpl;

/**
 * Implémentation du DAO pour la maintenance des {@link Utilisateur}
 * 
 * @author Brique DECKARD
 *
 */

@Stateless
public class UtilisateurDaoSql extends EntityDaoImpl<Utilisateur, String> implements UtilisateurDao {

//    @PersistenceContext(unitName = "advcomp")
//    EntityManager em;

    /**
     * Constructor for UtilisateurDaoSql
     */

    public UtilisateurDaoSql() {
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     * 
     * @param id
     * @return
     * @throws AdvCompException
     */

    @Override
    public Utilisateur retrieve(String id) throws AdvCompException {
	System.out.println("RETRIEVE Utilisateur " + id);
	if (id.isEmpty()) {
	    throw new AdvCompException("ERROR : Id is null");
	}
	try {
	    Utilisateur user = this.getEm().find(UtilisateurImpl.class, id);
	    return user;

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
    public Utilisateur getNew() throws AdvCompException {
	return new UtilisateurImpl();
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieveAll()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public List<Utilisateur> retrieveAll() throws AdvCompException {
	Query query = this.getEm().createNamedQuery("liste_user", Utilisateur.class);
	try {
	    List<Utilisateur> list = (List<Utilisateur>) query.getResultList();
	    return list;
	} catch (Exception e) {
	    throw new AdvCompException(e);
	}

    }

    /**
     * See @see
     * edu.bd.advcomp.authentification.dao.UtilisateurDao#retrieveAllInactiveUsers()
     *
     * @return
     */
    @Override
    public List<Utilisateur> retrieveAllInactiveUsers() {
	Query query = this.getEm().createNamedQuery("liste_user_inactifs", Utilisateur.class);
	List<Utilisateur> list = query.getResultList();
	return list;
    }

}
