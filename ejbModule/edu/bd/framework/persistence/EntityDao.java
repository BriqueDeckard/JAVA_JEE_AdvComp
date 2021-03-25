// File EntityDao.java - No copyright - 23 mars 2021
package edu.bd.framework.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.SystemException;

import edu.bd.advcomp.AdvcompException;

/**
 * TODO Fill type utility Assurer que le DAO propose un CRUD sur les entités.
 * <br/>
 * 
 * @author Brique DECKARD <br/>
 */
public interface EntityDao<ENTITY_TYPE, ID_TYPE> {
    /**
     * <u><b>create : </b></u><br/>
     * Créer une entitée. <br/>
     * 
     * @param entity : l'entitée à insérer
     * @return : l'entitée insérée
     * @throws AdvcompException
     * @throws SystemException
     * @throws SecurityException
     * @throws IllegalStateException
     */
    public ENTITY_TYPE create(ENTITY_TYPE entity) throws AdvcompException, Exception;

    /**
     * <u><b>retrieve : </b></u><br/>
     * Retrouver une entitée. <br/>
     * 
     * @param id
     * @return
     * @throws AdvcompException
     */
    public ENTITY_TYPE retrieve(ID_TYPE id) throws AdvcompException, Exception;

    /**
     * <u><b>update : </b></u><br/>
     * Mettre à jour une entitée. <br/>
     * 
     * @param entity
     * @return
     * @throws AdvcompException
     */
    public ENTITY_TYPE update(ENTITY_TYPE entity) throws AdvcompException, Exception;

    /**
     * <u><b>delete : </b></u><br/>
     * Supprimer une entitée <br/>
     * 
     * @param entity
     * @throws AdvcompException
     */
    public void delete(ENTITY_TYPE entity) throws AdvcompException, Exception;

    /**
     * <u><b>retrieveAll : </b></u><br/>
     * Retrouver toutes les entitées. <br/>
     * 
     * @return
     * @throws AdvcompException
     */
    default List<ENTITY_TYPE> retrieveAll() throws AdvcompException, Exception {
	return new ArrayList<ENTITY_TYPE>();
    }

    /**
     * <u><b>getNew : </b></u><br/>
     * Obtenir une nouvelle entitée. <br/>
     * 
     * @return
     * @throws AdvcompException
     */
    public ENTITY_TYPE getNew() throws AdvcompException, Exception;

}
