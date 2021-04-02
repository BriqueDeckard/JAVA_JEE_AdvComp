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
	System.out.println(this.getClass().getCanonicalName() + " CREATE.");
	try {
	    em.persist(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
	return entity;
    }

}
