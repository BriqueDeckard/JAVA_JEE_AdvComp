// File AdvCompServer.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import javax.ejb.Remote;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.admin.service.AdvCompAdminService;

/**
 * Serveur fournisseur de service {@link AdvCompService} aux clients
 * authentifiés.
 * 
 * @author Brique DECKARD
 *
 */
@Remote
public interface AdvCompServer {

    String JNDI = "java:global/AdvCompEjb/AdvCompServerImpl!edu.bd.advcomp.core.service.AdvCompServer";

    /**
     * Traite la demande de connexion d'un client et lui retourne un
     * {@link AdvCompService} si l'authentification a abouti dans les autres cas une
     * {@link AdvcompException} est levée.
     * 
     * @param login
     * @param password
     * @return
     * @throws AdvcompException
     */
    public AdvCompService connexion(String login, String password) throws AdvcompException;

    /**
     * Traite la demande de connexion d'un admin et lui retourne un
     * {@link AdvCompAdminService} si l'authentification aboutit, dans les autres
     * cas une {@link AdvcompException} est levée.
     *
     * TODO : Fill method utility
     * 
     * @param login
     * @param password
     * @return
     * @throws AdvcompException
     */
    public AdvCompAdminService connexionAsAdmin(String login, String password) throws AdvcompException;

    /**
     * Traite la demande de creation de compe d'un utilisateur.
     *
     * @param nom
     * @param login
     * @param password
     * @param adresse
     * @throws AdvcompException
     */
    public void creerCompte(String nom, String login, String password, String adresse) throws AdvcompException;
}
