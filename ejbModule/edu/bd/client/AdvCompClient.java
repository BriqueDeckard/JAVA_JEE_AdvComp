// File AdvCompClient.java - No copyright - 28 mars 2021
package edu.bd.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.serviceEndpointInterfaceMappingType;

import edu.bd.advcomp.AdvcompException;
import edu.bd.advcomp.DEV_CONFIG;
import edu.bd.advcomp.admin.service.AdvCompAdminService;
import edu.bd.advcomp.authentification.entity.Utilisateur;
import edu.bd.advcomp.authentification.service.AuthentificationService;
import edu.bd.advcomp.core.service.AdvCompServer;
import edu.bd.advcomp.core.service.AdvCompService;
import edu.bd.advcomp.facturation.entity.Facture;

/**
 * TODO Fill type utility
 * 
 * @author Brique DECKARD
 *
 */
public class AdvCompClient {
    private static String bigSeparator = "///////////////////////////////////////////\n";
    private static String smallSeparator = "-------------------------------------------\n";
    private static String midSeparator = "===========================================\n";

    /**
     * Constructor for AdvCompClient
     *
     */
    public AdvCompClient() {
    }

    public static void main(String args[]) throws AdvcompException {
	System.out.println(bigSeparator);
	System.out.println("Bienvenu dans AdvComp.");
	System.out.println(midSeparator);
	System.out.println("Chargement du serveur ...");
	AdvCompServer serveur;
	try {
	    serveur = doLookup();
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvcompException(e);
	}
	System.out.println("... serveur chargé.");

	prompt(serveur);

    }

    /**
     * prompt
     *
     * TODO : Fill method utility
     * 
     * @param serveur
     * @throws AdvcompException
     */
    private static void prompt(AdvCompServer serveur) throws AdvcompException {
	AdvCompService service = null;
	AdvCompAdminService admin = null;

	while (service == null && admin == null) {
	    String choice = doChoicePrompt();
	    System.out.println("CHOICE : " + choice);

	    // System.out.println("Tentative de connexion pour : " + login + " mdp : " +
	    // secret);
	    if (choice.toLowerCase().equals("u".toLowerCase())) {
		String login = doLoginPrompt(0);
		String secret = doSecretPrompt();
		service = serveur.connexion(login, secret);
		if (service == null) {
		    System.out.println("La connexion a échoué.");
		}
	    } else if (choice.toLowerCase().equals("c".toLowerCase())) {
		System.out.println("CREATE ACCOUNT");
		String login = doCreateLoginPrompt();
		String secret = doCreateSecretPrompt();
		String nom = doCreateNamePrompt();
		String adresse = doCreateAddressPrompt();
		serveur.creerCompte(login, nom, secret, adresse);
		System.out.println("ACCOUNT CREATED");
	    } else {
		String login = doLoginPrompt(1);
		String secret = doSecretPrompt();
		admin = serveur.connexionAsAdmin(login, secret);
		if (admin == null) {
		    System.out.println("La connexion a échoué.");
		}
	    }
	}

	if (admin != null) {
	    System.out.println("Connecté en tant qu'administrateur");
	    System.out.println("UI non implémentée.");

	    String serviceChoice = "42";

	    while (!serviceChoice.toUpperCase().equals("D".toUpperCase())) {
		serviceChoice = doAdminServiceChoicePrompt();
		Scanner myScanner = new Scanner(System.in);
		String login = "";
		String numero = "";
		Utilisateur user = null;
		String facture = null;
		switch (serviceChoice.toUpperCase()) {
		case "V":
		    System.out.println("VALIDER");
		    myScanner = new Scanner(System.in);

		    user = null;
		    while (user == null) {
			System.out.println("Entrez un login : ");
			login = myScanner.nextLine();
			user = admin.findUser(login);
			if (user != null) {
			    break;
			}

		    }
		    System.out.println("User : " + user.getIsActive());

		    admin.validerCompteUtilisateur(user.getLogin());
		    user = admin.findUser(login);
		    System.out.println("User : " + user.getIsActive());

		    break;
		case "I":
		    System.out.println("INVALIDER");
		    myScanner = new Scanner(System.in);
		    login = "";
		    user = null;
		    while (user == null) {

			System.out.println("Entrez un login : ");
			login = myScanner.nextLine();
			user = admin.findUser(login);
			if (user != null) {
			    break;
			}

		    }
		    System.out.println("User : " + user.getIsActive());

		    admin.desactiverCompteUtilisateur(user.getLogin());
		    user = admin.findUser(login);
		    System.out.println("User : " + user.getIsActive());

		    break;
		case "S":
		    System.out.println("SUPPRIMER");
		    myScanner = new Scanner(System.in);
		    login = "";
		    user = null;
		    while (user == null) {

			System.out.println("Entrez un login : ");
			login = myScanner.nextLine();
			user = admin.findUser(login);
			if (user != null) {
			    break;
			}

		    }

		    admin.supprimerCompteUtilisateur(user);
		    break;
		case "G":
		    System.out.println("GENERER FACTURE : ");

		    int anneeDebut = 0;
		    while (anneeDebut <= 1980 || anneeDebut >= 2021) {
			System.out.println("Entrez l'année de début : ");
			try {
			    anneeDebut = Integer.parseInt(myScanner.nextLine());
			} catch (Exception e) {
			    System.out.println("Vous devez entrer une année valide.");
			}
		    }
		    String anneeDebutStr = Integer.toString(anneeDebut);

		    int moisDebut = 0;
		    while (moisDebut <= 0 || moisDebut > 12) {
			System.out.println("Entrez le mois de début : ");
			try {
			    moisDebut = Integer.parseInt(myScanner.nextLine());
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un mois  valide.");
			}
		    }
		    String moisDebutStr = Integer.toString(moisDebut);

		    int jourDebut = 0;
		    while (jourDebut <= 0 || jourDebut > 30) {
			System.out.println("Entrez le jour de début : ");
			try {
			    jourDebut = Integer.parseInt(myScanner.nextLine());
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un jour  valide.");
			}
		    }
		    String jourDebutStr = Integer.toString(jourDebut);

		    int anneeFin = 0;

		    while (anneeFin <= 1980 || anneeFin > 2021) {
			System.out.println("Entrez l'année de fin : ");
			try {
			    anneeFin = Integer.parseInt(myScanner.nextLine());
			} catch (Exception e) {
			    System.out.println("Vous devez entrer une année valide.");
			}
		    }
		    String anneeFinStr = Integer.toString(anneeFin);

		    int moisFin = 0;
		    while (moisFin <= 0 || moisFin > 12) {
			System.out.println("Entrez le mois de fin : ");
			try {
			    moisFin = Integer.parseInt(myScanner.nextLine());
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un mois  valide.");
			}
		    }
		    String moisFinStr = Integer.toString(moisFin);

		    int jourFin = 0;
		    while (jourFin <= 0 || jourFin > 30) {
			System.out.println("Entrez le jour de fin : ");
			try {
			    jourFin = Integer.parseInt(myScanner.nextLine());
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un jour valide.");
			}
		    }
		    String jourFinStr = Integer.toString(jourFin);

		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
		    System.out.println(anneeDebutStr + "/" + moisDebut + "/" + jourDebutStr + " -> " + anneeFinStr
			    + " / " + moisFinStr + " / " + jourFinStr);
		    try {
			Date dateDebut = formatter.parse(anneeDebutStr + "/" + moisDebut + "/" + jourDebutStr);
			Date dateFin = formatter.parse(anneeFinStr + "/" + moisFinStr + "/" + jourFinStr);

			admin.genererFacture(dateDebut, dateFin);
			System.out.println("Facture générée.");
		    } catch (Exception e) {
			throw new AdvcompException(e);
		    }
		    break;
		case "C":
		    System.out.println("CONSULTER FACTURE");
		    myScanner = new Scanner(System.in);
		    numero = "";
		    facture = null;
		    while (facture == null) {

			System.out.println("Entrez un numéro : ");
			try {
			    numero = myScanner.nextLine();
			    facture = admin.consulterFacture(numero);
			    System.out.println("Facture : \n" + facture);
			} catch (Exception e) {
			    System.out.println("Erreur : " + e.getStackTrace());
			}

		    }

		    admin.supprimerCompteUtilisateur(user);
		    break;
		case "U":
		    System.out.println("TROUVER UTILISATEUR");
		    myScanner = new Scanner(System.in);
		    login = "";
		    user = null;
		    while (user == null) {

			System.out.println("Entrez un login : ");
			login = myScanner.nextLine();
			user = admin.findUser(login);
			System.out.println("Client : " + user.toString());
		    }

		    break;
		default:
		    break;
		}

	    }
	    System.out.println("Deconnecté.");
	    prompt(serveur);
	}

	if (service != null) {
	    String serviceChoice = "42";

	    System.out.println("Connecté en tant qu'utilisateur.");

	    while (!serviceChoice.toUpperCase().equals("D".toUpperCase())) {
		serviceChoice = doServiceChoicePrompt();
		Double facteur1 = 0d;
		Double facteur2 = 0d;
		String operateur = "#";
		switch (serviceChoice.toUpperCase()) {
		case "B":
		    System.out.println("Calcul basique : ");
		    facteur1 = doFacteurPrompt();
		    facteur2 = doFacteurPrompt();
		    operateur = doOperateurPrompt();
		    Double resultat = service.faireOperationBasique(facteur1, facteur2, operateur);
		    System.out.println(facteur1 + " " + operateur + " " + facteur2 + " = " + resultat);
		    break;
		case "C":
		    System.out.println("Calcul chainé :");
		    facteur1 = doFacteurPrompt();
		    facteur2 = doFacteurPrompt();
		    operateur = doOperateurPrompt();
		    resultat = 0d;
		    service.commencerOperationChainee(facteur1, facteur2, operateur);
		    String poursuivre = "";
		    Scanner myScanner = new Scanner(System.in);
		    while (!(poursuivre.toUpperCase().equals("N"))) {
			System.out.println("Continuer ? (O/N)");
			poursuivre = doContinueChoice();
			System.out.println("Vous avez entré : " + poursuivre);
			if ((poursuivre.toUpperCase().equals("N"))) {
			    System.out.println("Arrêt");
			    resultat = service.acheverOperationChainee();
			    break;
			} else {
			    System.out.println("Poursuite");
			    System.out.println("Resultat intermédiaire : " + service.afficherResultatIntermediaire());
			    facteur2 = doFacteurPrompt();
			    operateur = doOperateurPrompt();
			    service.poursuivreOperationChainee(facteur2, operateur);
			}

		    }
		    System.out.println(service.afficherResultatFinal());
		    break;

		default:
		    break;
		}
	    }
	    System.out.println("Deconnecté.");
	    prompt(serveur);

	}
    }

    /**
     * doCreateAddressPrompt
     *
     * TODO : Fill method utility
     * 
     * @return
     */
    private static String doCreateAddressPrompt() {
	Scanner myScanner = new Scanner(System.in);
	String adresse = " ";
	while (adresse.length() <= 2) {

	    System.out.println("Entrez une adresse : ");
	    adresse = myScanner.nextLine();
	}

	return adresse;
    }

    /**
     * doCreateNamePrompt
     *
     * TODO : Fill method utility
     * 
     * @return
     */
    private static String doCreateNamePrompt() {
	Scanner myScanner = new Scanner(System.in);
	String name = " ";
	while (name.length() <= 1) {

	    System.out.println("Entrez un nom : ");
	    name = myScanner.nextLine();
	}

	return name;
    }

    /**
     * doCreateSecretPrompt
     *
     * TODO : Fill method utility
     * 
     * @return
     */
    private static String doCreateSecretPrompt() {
	Scanner myScanner = new Scanner(System.in);
	String secret = " ";
	while (secret.length() < 5) {

	    System.out.println("Entrez un mot de passe (au moins 5 caractères) : ");
	    secret = myScanner.nextLine();
	}

	return secret;
    }

    private static String doCreateLoginPrompt() {
	Scanner myScanner = new Scanner(System.in);
	String login = " ";
	while (login.length() < 5) {

	    System.out.println("Entrez un login (au moins 5 caractères) : ");
	    login = myScanner.nextLine();
	}

	return login;
    }

    private static String doContinueChoice() {
	Scanner myScanner = new Scanner(System.in);
	String poursuivre = "";
	while (!(poursuivre.toUpperCase().equals("O")) && !(poursuivre.toUpperCase().equals("N"))) {

	    System.out.println("Voulez vous continuer ? (0/N) : ");
	    poursuivre = myScanner.nextLine();
	}

	return poursuivre;
    }

    private static String doOperateurPrompt() {

	Scanner myScanner = new Scanner(System.in);
	String operateur = "";
	while (!(operateur.equals("*")) && !(operateur.equals("/")) && !(operateur.equals("+"))
		&& !(operateur.equals("-"))) {
	    System.out.println("Entrez un operateur : ");
	    operateur = myScanner.nextLine();
	}

	return operateur;
    }

    private static Double doFacteurPrompt() {
	System.out.println("Entrez une valeur (Double) : ");
	Scanner myScanner = new Scanner(System.in);
	Double valeur = null;
	while (valeur == null) {
	    String input = myScanner.nextLine();
	    try {
		valeur = Double.parseDouble(input);
	    } catch (java.lang.NumberFormatException e) {
		System.out.println("Vous devez entrer un nombre.");
		valeur = null;
	    }
	}

	System.out.println("Vous avez entré " + valeur);
	return valeur;
    }

    private static String doAdminServiceChoicePrompt() {
	String choice = "42";
	while (!(choice.toUpperCase().equals("V".toUpperCase())) && !(choice.toUpperCase().equals("I".toUpperCase()))
		&& !(choice.toUpperCase().equals("S".toUpperCase()))
		&& !(choice.toUpperCase().equals("G".toUpperCase()))
		&& !(choice.toUpperCase().equals("C".toUpperCase()))
		&& !(choice.toUpperCase().equals("U".toUpperCase()))
		&& !(choice.toUpperCase().equals("D".toUpperCase()))) {
	    System.out.println("Que voulez vous faire ?");
	    Scanner myScanner = new Scanner(System.in);
	    System.out.println("'V' pour valider un compte\n" + "'I' pour invalider un compte\n"
		    + "'S' pour supprimer un compte \n" + "'G' pour générer une facture \n"
		    + "'C' pour Consulter une facture \n" + "'U' pour trouver un utilisateur \n"
		    + "'D' pour vous déconnecter.");
	    choice = myScanner.nextLine();
	    System.out.println("Vous avez entré : " + choice);

	}

	return choice;
    }

    private static String doServiceChoicePrompt() {
	String choice = "42";
	while (!(choice.toUpperCase().equals("B".toUpperCase())) && !(choice.toUpperCase().equals("C".toUpperCase()))
		&& !(choice.toUpperCase().equals("D".toUpperCase()))) {
	    System.out.println("Que voulez vous faire ?");
	    Scanner myScanner = new Scanner(System.in);
	    System.out.println("Entrez 'B' pour Basique et 'C' pour Chainé. 'D' pour vous déconnecter.");
	    choice = myScanner.nextLine();
	    System.out.println("Vous avez entré : " + choice);

	}
	return choice;
    }

    private static String doChoicePrompt() {
	String choice = "42";
	Scanner myScanner = new Scanner(System.in);
	while (!(choice.toUpperCase().equals("A".toUpperCase())) && !(choice.toUpperCase().equals("U".toUpperCase()))
		&& !(choice.toUpperCase().equals("C"))) {

	    System.out.println("Entrez 'A' pour Admin, 'U' pour Utilisateur ou 'C' pour creer un compte.");
	    choice = myScanner.nextLine();
	    System.out.println("Vous avez entré : " + choice);

	}
	return choice;
    }

    private static String doLoginPrompt(int i) {
	Scanner myScanner = new Scanner(System.in);
	if (i == 0) {
	    System.out.println("Entrez votre login : (CLIENT2)");
	} else {
	    System.out.println("Entrez votre login : (ADMIN1)");
	}

	String login = myScanner.nextLine();

	System.out.println("Vous avez entré : " + login);
	return login;
    }

    private static String doSecretPrompt() {
	Scanner myScanner = new Scanner(System.in);
	System.out.println("Entrez votre mot de pase : (SECRET)");
	String secret = myScanner.nextLine();
	// System.out.println("Vous avez entré : " + secret);

	return secret;
    }

    private static AdvCompServer doLookup() throws NamingException {
	// Set des properties du context
	try {

	    AdvCompServer bean = (AdvCompServer) InitialContext.doLookup("edu.bd.advcomp.core.service.AdvCompServer");
	    return bean;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
