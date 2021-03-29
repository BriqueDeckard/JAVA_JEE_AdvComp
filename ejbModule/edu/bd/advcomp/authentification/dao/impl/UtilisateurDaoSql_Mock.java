// File UtilisateurDaoSql.java - No copyright - 23 mars 2021
package edu.bd.advcomp.authentification.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.DEV_CONFIG;
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
public class UtilisateurDaoSql_Mock implements UtilisateurDao {

    ArrayList<Utilisateur> dbList = new ArrayList<Utilisateur>();

    /**
     * Mock
     */
    private Utilisateur utilisateurToto = new UtilisateurImpl("toto", "secret", "Bob", "1 rue Tutu", Role.CLIENT);

    /**
     * Constructor for UtilisateurDaoSql
     */
    public UtilisateurDaoSql_Mock() {
	dbList.add(new UtilisateurImpl("toto", "secret", "Bob", "1 rue Toto", Role.CLIENT));
	dbList.add(new UtilisateurImpl("tata", "secret", "Bab", "2 rue Tata", Role.CLIENT));
	dbList.add(new UtilisateurImpl("tutu", "secret", "Bub", "3 rue Tutu", Role.CLIENT));
	dbList.add(new UtilisateurImpl("titi", "secret", "Bib", "4 rue Titi", Role.CLIENT));
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
	// TODO : Implements real persistence
	dbList.add(entity);
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     * 
     * @param id
     * @return
     * @return
     * @throws AdvcompException
     */
    @Override
    public Utilisateur retrieve(String id) throws AdvcompException {
	// TODO : Implements real persistence
	return dbList.stream().filter(x -> id.equals(x.getNom())).findAny().orElse(utilisateurToto);
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
	int index = dbList.indexOf(entity);
	dbList.set(index, entity);
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
	int index = dbList.indexOf(entity);
	dbList.remove(index);
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
	return dbList;
    }

}
