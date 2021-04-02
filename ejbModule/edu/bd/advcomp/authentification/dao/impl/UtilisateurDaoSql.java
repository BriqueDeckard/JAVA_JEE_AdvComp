// File UtilisateurDaoSql.java - No copyright - 23 mars 2021 package
package edu.bd.advcomp.authentification.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.DEV_CONFIG;
import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;
import edu.bd.framework.persistence.EntityDaoImpl;

/**
 * Implémentation du DAO pour la maintenance des {@link Utilisateur}
 * 
 * @author Brique DECKARD
 *
 */

@Stateless
public class UtilisateurDaoSql extends EntityDaoImpl<Utilisateur, String> implements UtilisateurDao {

    @PersistenceContext(unitName = "advcomp")
    EntityManager em;

    /**
     * Constructor for UtilisateurDaoSql
     */

    public UtilisateurDaoSql() {
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#create(java.lang.Object)
     * 
     * @param entity
     * @return
     * @throws AdvcompException
     */

    /*
     * @Override public Utilisateur create(Utilisateur entity) throws
     * AdvcompException { System.out.println("CREATE Utilisateur " +
     * entity.getLogin()); if (entity == null) { throw new
     * AdvcompException("ERROR : Entity is null"); } try { em.persist(entity);
     * return entity; } catch (Exception e) { e.printStackTrace(); throw new
     * AdvcompException("ERROR : Echec persistance"); }
     * 
     * }
     */

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     * 
     * @param id
     * @return
     * @throws AdvcompException
     */

    @Override
    public Utilisateur retrieve(String id) throws AdvcompException {
	System.out.println("RETRIEVE Utilisateur " + id);
	if (id.isEmpty()) {
	    throw new AdvcompException("ERROR : Id is null");
	}
	try {
	    Utilisateur user = em.find(UtilisateurImpl.class, id);
	    return user;

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException("ERROR : Echec persistance");
	}

    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#update(java.lang.Object)
     * 
     * @param entity
     * @return
     * @throws AdvcompException
     */

    @Override
    public Utilisateur update(Utilisateur entity) throws AdvcompException {
	System.out.println("UPDATE Utilisateur " + entity.getLogin());
	if (entity == null) {
	    throw new AdvcompException("ERROR : Entity is null");
	}
	try {
	    em.merge(entity);
	    return entity;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException("ERROR : Echec persistance");
	}

    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#delete(java.lang.Object)
     * 
     * @param entity
     * @throws AdvcompException
     */

    @Override
    public void delete(Utilisateur entity) throws AdvcompException {
	System.out.println("DELETE Utilisateur " + entity.getLogin());
	if (entity == null) {
	    throw new AdvcompException("ERROR : Entity is null");
	}
	try {
	    em.remove(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException("ERROR : Echec suppression");
	}
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#getNew()
     * 
     * @return
     * @throws AdvcompException
     */

    @Override
    public Utilisateur getNew() throws AdvcompException {
	return new UtilisateurImpl();
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieveAll()
     *
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public List<Utilisateur> retrieveAll() throws AdvcompException, Exception {
	return null;
    }

}
