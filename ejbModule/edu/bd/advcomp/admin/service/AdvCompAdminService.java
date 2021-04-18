// File AdvCompAdminService.java - No copyright - 18 avr. 2021
package edu.bd.advcomp.admin.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import edu.bd.advcomp.AdvcompException;
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
     * get All The Facture
     *
     * @return
     * @throws Exception 
     * @throws AdvcompException 
     */
    List<Facture> getAllTheFacture() throws AdvcompException, Exception;

    Utilisateur findUser(String login) throws AdvcompException;

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
    void validerCompteUtilisateur(String utilisateurLogin) throws AdvcompException;

    /**
     * desactiver Compte Utilisateur
     *
     * TODO : Fill method utility
     * 
     * @param utilisateurLogin
     * @throws AdvcompException
     */
    void desactiverCompteUtilisateur(String utilisateurLogin) throws AdvcompException;

    /**
     * supprimer Compte Utilisateur
     *
     * @param utilisateur
     * @throws AdvcompException
     */
    void supprimerCompteUtilisateur(Utilisateur utilisateur) throws AdvcompException;

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
    void genererFacture(Date dateDebut, Date dateFin) throws AdvcompException;

    void seDeconnecter();
}
