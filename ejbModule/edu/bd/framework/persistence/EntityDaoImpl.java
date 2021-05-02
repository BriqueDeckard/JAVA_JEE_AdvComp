// File EntityDaoImpl.java - No copyright - 2 avr. 2021
package edu.bd.framework.persistence;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.bd.advcomp.AdvCompException;

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
     * Gets em 
     * @return the em
     */
    public EntityManager getEm() {
        return this.em;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#create(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public ENTITY_TYPE create(ENTITY_TYPE entity) throws AdvCompException, Exception {
	System.out
		.println("INFO : " + this.getClass().getSimpleName() + " CREATE " + entity.getClass().getSimpleName());
	try {
	    em.persist(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvCompException(e);
	}
	System.out.println("INFO : " + entity.toString() + " CREATED BY " + this.getClass().getSimpleName());
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#update(java.lang.Object)
     *
     * @param entity
     * @return
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public ENTITY_TYPE update(ENTITY_TYPE entity) throws AdvCompException, Exception {
	System.out
		.println("INFO : " + this.getClass().getSimpleName() + " UPDATE " + entity.getClass().getSimpleName());
	try {
	    em.merge(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new AdvCompException(e);
	}
	System.out.println("INFO : " + entity.toString() + " UPDATED BY " + this.getClass().getSimpleName());
	return entity;
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#delete(java.lang.Object)
     *
     * @param entity
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public void delete(ENTITY_TYPE entity) throws AdvCompException, Exception {
	System.out
		.println("INFO : " + this.getClass().getSimpleName() + " DELETE " + entity.getClass().getSimpleName());
	entity = em.merge(entity);
	this.em.remove(entity);
	System.out.println("INFO : " + entity.toString() + " DELETED BY " + this.getClass().getSimpleName());
    }

    /**
     * See @see edu.bd.framework.persistence.EntityDao#getNew()
     *
     * @return
     * @throws AdvCompException
     * @throws Exception
     */
    @Override
    public abstract ENTITY_TYPE getNew() throws AdvCompException, Exception;

    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieve(java.lang.Object)
     *
     * @param id
     * @return
     * @throws AdvCompException
     */
    @Override
    public abstract ENTITY_TYPE retrieve(ID_TYPE id) throws AdvCompException;
    
    /**
     * See @see edu.bd.framework.persistence.EntityDao#retrieveAll()
     *
     * @return
     * @throws AdvCompException
     */
    @Override
    public abstract List<ENTITY_TYPE> retrieveAll() throws AdvCompException;

}
