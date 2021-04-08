// File EntityDaoImpl.java - No copyright - 2 avr. 2021
package edu.bd.framework.persistence;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.AdvcompException;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public abstract class EntityDaoImpl<ENTITY_TYPE, ID_TYPE> implements EntityDao<ENTITY_TYPE, ID_TYPE> {

    private Class<ENTITY_TYPE> type;

    /**
     * Constructor for EntityDaoImpl
     *
     */
    public EntityDaoImpl() {
	Type t = getClass().getGenericSuperclass();
	ParameterizedType parameterizedType = (ParameterizedType) t;
	type = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * Entity manager for entity persistence.
     */
    @PersistenceContext(unitName = "advcomp")
    EntityManager em;

    /**
     * See @see edu.bd.framework.persistence.EntityDao#create(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public ENTITY_TYPE create(ENTITY_TYPE entity) throws AdvcompException, Exception {
	System.out.println(this.getClass().getSimpleName() + " CREATE.");
	try {
	    em.persist(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
	System.out.println(this.getClass().getSimpleName() + " CREATED.");
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#update(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public ENTITY_TYPE update(ENTITY_TYPE entity) throws AdvcompException, Exception {
	System.out.println(this.getClass().getSimpleName() + " UPDATE");
	try {
	    em.merge(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
	System.out.println(this.getClass().getSimpleName() + " UPDATED");
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#delete(java.lang.Object)
     *
     * @param entity
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public void delete(ENTITY_TYPE entity) throws AdvcompException, Exception {
	System.out.println(this.getClass().getSimpleName() + " DELETE");
	this.em.remove(entity);
	System.out.println(this.getClass().getSimpleName() + " DELETED");
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#getNew()
     *
     * @return
     * @throws AdvcompException
     * @throws Exception
     */
    @Override
    public abstract ENTITY_TYPE getNew() throws AdvcompException, Exception;

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvcompException
     */
    @Override
    public abstract ENTITY_TYPE retrieve(ID_TYPE id) throws AdvcompException;

}
