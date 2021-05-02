// File Entity.java - No copyright - 23 mars 2021
package edu.bd.framework.persistence;

import java.io.Serializable;

/**
 * <br/>
 * S'assurer que la classe marquée est bien une entitée. <br/>
 * 
 * @author Brique DECKARD
 *
 */
public interface Entity<IDENTIFIER_TYPE> extends Serializable {

    IDENTIFIER_TYPE getId();

    void setId(IDENTIFIER_TYPE id);

}
