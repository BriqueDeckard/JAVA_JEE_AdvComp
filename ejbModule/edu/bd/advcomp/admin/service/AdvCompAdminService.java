// File AdvCompAdminService.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.admin.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.enterprise.event.Observes;

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.UtilisateurService;
import edu.bd.advcomp.facturation.entity.Facture;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
@Remote
public interface AdvCompAdminService {

    /**
     * Glassfish portable JNDI
     */
    public static String JNDI = "java:global/AdvCompEjb/AdvCompAdminServiceImpl!edu.bd.advcomp.admin.service.AdvCompAdminService";

    /**
     * get All The Facture
     *
     * @return
     * @throws Exception
     * @throws AdvCompException
     */
    List<Facture> getAllTheFacture() throws AdvCompException, Exception;

    /**
     * get All The Connexion Attempt
     *
     * @return
     * @throws AdvCompException
     */
    List<ConnexionAttempt> getAllTheConnexionAttempt() throws AdvCompException;

    /**
     * find User by its login
     *
     * 
     * @param login
     * @return
     * @throws AdvCompException
     */
    Utilisateur findUser(String login) throws AdvCompException;

    /**
     * setAdmin
     *
     * Definir l'admin de la session
     * 
     * @param admin
     */
    void setAdmin(Utilisateur admin);

    /**
     * valider Compte Utilisateur
     *
     * @param utilisateurLogin
     */
    void validerCompteUtilisateur(String utilisateurLogin) throws AdvCompException;

    /**
     * desactiver Compte Utilisateur
     *
     * TODO : Fill method utility
     * 
     * @param utilisateurLogin
     * @throws AdvCompException
     */
    void desactiverCompteUtilisateur(String utilisateurLogin) throws AdvCompException;

    /**
     * supprimer Compte Utilisateur
     *
     * @param utilisateur
     * @throws AdvCompException
     */
    void supprimerCompteUtilisateur(Utilisateur utilisateur) throws AdvCompException;

    /**
     * consulter Facture
     * 
     * @return
     *
     */
    String consulterFacture(String numero);

    /**
     * generer Facture
     *
     * TODO : Fill method utility
     * 
     * @param dateDebut
     * @param dateFin
     */
    void genererFacture(Date dateDebut, Date dateFin) throws AdvCompException;

    /**
     * se Deconnecter
     *
     */
    void seDeconnecter();

    /**
     * retrouver Utilisateurs Inactifs
     *
     * @return
     * @throws AdvCompException
     */
    List<Utilisateur> retrouverUtilisateursInactifs() throws AdvCompException;

    /**
     * getNumberOfConnexionFor
     * 
     * @param bDate
     * @param eDate
     * @return
     * @throws AdvCompException
     */
    Integer getNumberOfConnexionFor(Date bDate, Date eDate) throws AdvCompException;

    /**
     * reglerFacture
     *
     * @param numero
     * @param rib
     * @throws AdvCompException 
     */
    void reglerFacture(String numero, String rib) throws AdvCompException;

}
