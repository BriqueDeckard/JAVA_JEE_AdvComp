// File UtilisateurDaoSql.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.dao.impl;

import javax.ejb.Stateless;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.authentification.Role;
import edu.bd.advcomp.authentification.dao.UtilisateurDao;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.entity.impl.UtilisateurImpl;

/**
 * Implémentation du DAO pour la maintenance des {@link Utilisateur}
 * 
 * @author Brique DECKARD
 *
 */
// TODO : Or stateful ?
@Stateless
public class UtilisateurDaoSql implements UtilisateurDao {

    /**
     * Mock
     */
    private Utilisateur utilisateurToto = new UtilisateurImpl("toto", "secret", "Bob", "1 rue Tutu", Role.CLIENT);

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
    @Override
    public Utilisateur create(Utilisateur entity) throws AdvcompException {
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     * 
     * @param id
     * @return
     * @throws AdvcompException
     */
    @Override
    public Utilisateur retrieve(String id) throws AdvcompException {
	// TODO : Implements real persistence
	if ("toto".equals(id)) {
	    return utilisateurToto;
	}
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
    public Utilisateur update(Utilisateur entity) throws AdvcompException {
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#delete(java.lang.Object)
     * 
     * @param entity
     * @throws AdvcompException
     */
    @Override
    public void delete(Utilisateur entity) throws AdvcompException {
	// TODO Fill method utility.
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

}
