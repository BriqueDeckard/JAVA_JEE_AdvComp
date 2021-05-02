// File AdvCompServer.java - No copyright - 23 mars 2021
package edu.bd.advcomp.core.service;

import javax.ejb.Remote;

import edu.bd.advcomp.AdvCompException;
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
     * {@link AdvCompException} est levée.
     * 
     * @param login
     * @param password
     * @return
     * @throws AdvCompException
     */
    public AdvCompService connexion(String login, String password) throws AdvCompException;

    /**
     * Traite la demande de connexion d'un admin et lui retourne un
     * {@link AdvCompAdminService} si l'authentification aboutit, dans les autres
     * cas une {@link AdvCompException} est levée.
     *
     * TODO : Fill method utility
     * 
     * @param login
     * @param password
     * @return
     * @throws AdvCompException
     */
    public AdvCompAdminService connexionAsAdmin(String login, String password) throws AdvCompException;

    /**
     * Traite la demande de creation de compe d'un utilisateur.
     *
     * @param nom
     * @param login
     * @param password
     * @param adresse
     * @throws AdvCompException
     */
    public void creerCompte(String nom, String login, String password, String adresse) throws AdvCompException;
}
