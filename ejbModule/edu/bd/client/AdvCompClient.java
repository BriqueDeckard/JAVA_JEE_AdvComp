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

import edu.bd.advcomp.AdvCompException;
import edu.bd.advcomp.DEV_CONFIG;
import edu.bd.advcomp.admin.entity.ConnexionAttempt;
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

    public static void main(String args[]) throws AdvCompException {
	System.out.println(bigSeparator);
	System.out.println("Bienvenu dans AdvComp.");
	System.out.println(midSeparator);
	System.out.println("Chargement du serveur ...");
	AdvCompServer serveur;
	try {
	    serveur = doLookup();
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new AdvCompException(e);
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
     * @throws AdvCompException
     */
    private static void prompt(AdvCompServer serveur) throws AdvCompException {
	AdvCompService service = null;
	AdvCompAdminService admin = null;
	String login = "";

	while (service == null && admin == null) {
	    String choice = doChoicePrompt();
	    System.out.println("CHOICE : " + choice);

	    if (choice.toLowerCase().equals("u".toLowerCase())) {
		login = doLoginPrompt(0);
		if (login.equalsIgnoreCase("A")) {
		    prompt(serveur);
		}
		String secret = doSecretPrompt();
		service = serveur.connexion(login, secret);
		if (service == null) {
		    System.out.println("La connexion a échoué.");
		    break;
		} else {
		    userProcess(serveur, service);
		}
	    } else if (choice.toLowerCase().equals("c".toLowerCase())) {
		createAccount(serveur);
	    } else {
		login = doLoginPrompt(1);
		if (login.equalsIgnoreCase("A")) {
		    prompt(serveur);
		}
		String secret = doSecretPrompt();
		admin = serveur.connexionAsAdmin(login, secret);
		if (admin == null) {
		    System.out.println("La connexion a échoué.");
		    break;
		} else {
		    adminProcess(serveur, admin);
		}
	    }
	    if (login.equalsIgnoreCase("A")) {
		break;
	    }
	}

    }

    /**
     * createAccount
     *
     * TODO : Fill method utility
     * 
     * @param serveur
     * @throws AdvCompException
     */
    private static void createAccount(AdvCompServer serveur) throws AdvCompException {
	System.out.println("CREATE ACCOUNT");
	String login = doCreateLoginPrompt();
	if (login.equalsIgnoreCase("A")) {
	    System.out.println("Annulation");
	    return;
	}
	String secret = doCreateSecretPrompt();
	String nom = doCreateNamePrompt();
	String adresse = doCreateAddressPrompt();
	serveur.creerCompte(nom, login, secret, adresse);
	System.out.println("ACCOUNT CREATED");
    }

    /**
     * processUser
     *
     * TODO : Fill method utility
     * 
     * @param serveur
     * @param service
     * @throws AdvCompException
     */
    private static void userProcess(AdvCompServer serveur, AdvCompService service) throws AdvCompException {
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
		try {
		    Double resultat = service.faireOperationBasique(facteur1, facteur2, operateur);
		    System.out.println(facteur1 + " " + operateur + " " + facteur2 + " = " + resultat);
		} catch (AdvCompException e2) {
		    System.out.println("ECHEC CALCUL : " + e2.getMessage());
		}

		break;
	    case "C":
		System.out.println("Calcul chainé :");
		facteur1 = doFacteurPrompt();
		facteur2 = doFacteurPrompt();
		operateur = doOperateurPrompt();
		Double resultat = 0d;
		try {
		    service.commencerOperationChainee(facteur1, facteur2, operateur);
		} catch (AdvCompException e) {
		    System.out.println("ECHEC CALCUL : " + e.getMessage());
		    break;
		}

		String poursuivre = "";
		Scanner myScanner = new Scanner(System.in);
		while (!(poursuivre.toUpperCase().equals("N"))) {
		    System.out.println("Continuer ? (O/N)");
		    poursuivre = doContinueChoice();
		    System.out.println("Vous avez entré : " + poursuivre);
		    if ((poursuivre.toUpperCase().equals("N"))) {
			System.out.println("Arrêt");
			try {
			    resultat = service.acheverOperationChainee();
			} catch (Exception e) {
			    System.out.println("ECHEC CALCUL : " + e.getMessage());
			}

			break;
		    } else {
			System.out.println("Poursuite");
			System.out.println("Resultat intermédiaire : " + service.afficherResultatIntermediaire());
			facteur2 = doFacteurPrompt();
			operateur = doOperateurPrompt();
			try {
			    service.poursuivreOperationChainee(facteur2, operateur);
			} catch (AdvCompException e) {
			    System.out.println("ECHEC CALCUL : " + e.getMessage());
			}

		    }

		}
		try {
		    System.out.println(service.afficherResultatFinal());
		} catch (AdvCompException e) {
		    System.out.println("ECHEC CALCUL : " + e.getMessage());
		}

		break;

	    default:
		break;
	    }
	}
	System.out.println("Deconnecté.");
	prompt(serveur);
    }

    /**
     * adminProcess
     *
     * TODO : Fill method utility
     * 
     * @param serveur
     * @param admin
     * @throws AdvCompException
     */
    private static void adminProcess(AdvCompServer serveur, AdvCompAdminService admin) throws AdvCompException {
	System.out.println("Connecté en tant qu'administrateur");
	System.out.println("UI non implémentée.");

	String serviceChoice = "42";

	while (!serviceChoice.toUpperCase().equals("D".toUpperCase())) {
	    serviceChoice = doAdminServiceChoicePrompt();
	    Scanner myScanner = new Scanner(System.in);
	    String login = "";
	    String numero = null;

	    Utilisateur user = null;
	    String facture = null;
	    String rib = null;
	    switch (serviceChoice.toUpperCase()) {
	    case "R":
		System.out.println("Regler facture");
		myScanner = new Scanner(System.in);
		while (numero == null) {
		    System.out.println("Entrez un numéro ou 'A' pour annuler : \n");
		    numero = myScanner.nextLine();
		}
		if (numero.toUpperCase().equals("A")) {
		    break;
		}
		while (rib == null) {
		    System.out.println("Entrez un rib ou 'A' pour annuler : \n");
		    rib = myScanner.nextLine();
		}
		if (rib.toUpperCase().equals("A")) {
		    break;
		}

		if (numero != null && rib != null) {
		    try {
			admin.reglerFacture(numero, rib);
		    } catch (Exception e) {
			System.out.println(e.getMessage());
		    }
		    System.out.println("Facture réglée.");

		}
		break;

	    case "V":
		System.out.println("VALIDER");
		myScanner = new Scanner(System.in);

		user = null;
		while (user == null && !login.toUpperCase().equals("A")) {
		    System.out.println("Entrez un login ou 'A' pour annuler: \n");
		    login = myScanner.nextLine();
		    if (login.toUpperCase() == "A") {
			break;
		    }
		    user = admin.findUser(login);
		    if (user != null) {
			System.out.println("User : " + user.getIsActive());

			admin.validerCompteUtilisateur(user.getId());
			user = admin.findUser(login);
			System.out.println("User : " + user.getIsActive());
			break;
		    }

		}

		break;
	    case "I":
		System.out.println("INVALIDER");
		myScanner = new Scanner(System.in);
		login = "";
		user = null;
		while (user == null && !login.toUpperCase().equals("A")) {

		    System.out.println("Entrez un login ou 'A' pour annuler : \n");
		    login = myScanner.nextLine();
		    if (login.toUpperCase() == "A") {
			break;
		    }
		    user = admin.findUser(login);
		    if (user != null) {
			System.out.println("User : " + user.getIsActive());

			admin.desactiverCompteUtilisateur(user.getId());
			user = admin.findUser(login);
			System.out.println("User : " + user.getIsActive());
			break;
		    }

		}

		break;
	    case "S":
		System.out.println("SUPPRIMER");
		myScanner = new Scanner(System.in);
		login = "";
		user = null;
		while (user == null && !login.toUpperCase().equals("A")) {

		    System.out.println("Entrez un login ou 'A' pour annuler : ");
		    login = myScanner.nextLine();
		    if (login.toUpperCase() == "A") {
			break;
		    }
		    user = admin.findUser(login);
		    if (user != null) {
			admin.supprimerCompteUtilisateur(user);
			break;
		    }

		}
		break;
	    case "G":
		System.out.println("GENERER FACTURE : ");

		int anneeDebutG = 0;
		String anneeDebutStrG;

		int moisDebutG = 0;
		String moisDebutStrG;

		int jourDebutG = 0;
		String jourDebutStrG;

		int anneeFinG = 0;
		String anneeFinStrG;

		int moisFinG = 0;
		String moisFinStrG;

		int jourFinG = 0;
		String jourFinStrG;

		String answerG = "";
		while (!answerG.equalsIgnoreCase("A")) {

		    // ANNEE DE DEBUT
		    while ((anneeDebutG <= 1980 || anneeDebutG >= 2021)) {
			System.out.println("Entrez l'année de début ou 'A' pour annuler : \n");

			try {
			    answerG = myScanner.nextLine();
			    if (answerG.equalsIgnoreCase("A")) {
				break;
			    }
			    anneeDebutG = Integer.parseInt(answerG);
			} catch (Exception e) {
			    System.out.println("Vous devez entrer une année valide.");
			}
		    }
		    if (answerG.equalsIgnoreCase("A")) {
			break;
		    } else {
			anneeDebutStrG = Integer.toString(anneeDebutG);
		    }

		    // MOIS DE DEBUT
		    while (moisDebutG <= 0 || moisDebutG > 12) {
			System.out.println("Entrez le mois de début ou 'A' pour annuler : \n");

			try {
			    answerG = myScanner.nextLine();
			    if (answerG.equalsIgnoreCase("A")) {
				break;
			    }
			    moisDebutG = Integer.parseInt(answerG);
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un mois  valide.");
			}
		    }
		    if (answerG.equalsIgnoreCase("A")) {
			break;
		    } else {
			moisDebutStrG = Integer.toString(moisDebutG);
		    }

		    // JOUR DE DEBUT
		    while (jourDebutG <= 0 || jourDebutG > 30) {
			System.out.println("Entrez le jour de début ou 'A' pour annuler : \n");

			try {
			    answerG = myScanner.nextLine();
			    if (answerG.equalsIgnoreCase("A")) {
				break;
			    }
			    jourDebutG = Integer.parseInt(answerG);
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un jour  valide.");
			}
		    }
		    if (answerG.equalsIgnoreCase("A")) {
			break;
		    } else {
			jourDebutStrG = Integer.toString(jourDebutG);
		    }

		    // ANNEE FIN
		    while (anneeFinG <= 1980 || anneeFinG > 2021) {
			System.out.println("Entrez l'année de fin ou 'A' pour annuler : \n");

			try {
			    answerG = myScanner.nextLine();
			    if (answerG.equalsIgnoreCase("A")) {
				break;
			    }
			    anneeFinG = Integer.parseInt(answerG);
			} catch (Exception e) {
			    System.out.println("Vous devez entrer une année valide.");
			}
		    }
		    if (answerG.equalsIgnoreCase("A")) {
			break;
		    } else {
			anneeFinStrG = Integer.toString(anneeFinG);
		    }

		    // MOIS FIN

		    while (moisFinG <= 0 || moisFinG > 12) {
			System.out.println("Entrez le mois de fin ou 'A' pour annuler :\n");

			try {
			    answerG = myScanner.nextLine();
			    if (answerG.equalsIgnoreCase("A")) {
				break;
			    }
			    moisFinG = Integer.parseInt(answerG);
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un mois  valide.");
			}
		    }
		    if (answerG.equalsIgnoreCase("A")) {
			break;
		    } else {
			moisFinStrG = Integer.toString(moisFinG);
		    }

		    // JOUR FIN

		    while (jourFinG <= 0 || jourFinG > 30) {
			System.out.println("Entrez le jour de fin ou 'A' pour annuler : \n");
			try {
			    answerG = myScanner.nextLine();
			    if (answerG.equalsIgnoreCase("A")) {
				break;
			    }
			    jourFinG = Integer.parseInt(answerG);
			} catch (Exception e) {
			    System.out.println("Vous devez entrer un jour valide.");
			}
		    }
		    if (answerG.equalsIgnoreCase("A")) {
			break;
		    } else {
			jourFinStrG = Integer.toString(jourFinG);
		    }

		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		    System.out.println(anneeDebutStrG + "/" + moisDebutStrG + "/" + jourDebutStrG + " -> "
			    + anneeFinStrG + "/" + moisFinStrG + "/" + jourFinStrG);
		    try {
			Date dateDebut = formatter.parse(anneeDebutStrG + "/" + moisDebutG + "/" + jourDebutStrG);
			Date dateFin = formatter.parse(anneeFinStrG + "/" + moisFinStrG + "/" + jourFinStrG);

			admin.genererFacture(dateDebut, dateFin);
			System.out.println("Facture générée.");
			break;
		    } catch (Exception e) {
			throw new AdvCompException(e);
		    }
		}

		break;
	    case "C":
		System.out.println("CONSULTER FACTURE");
		myScanner = new Scanner(System.in);
		numero = "";
		facture = null;
		while (facture == null) {

		    System.out.println("Entrez un numéro ou 'A' pour annuler: ");
		    try {
			numero = myScanner.nextLine();
			if (numero.equalsIgnoreCase("A")) {
			    break;
			}
			facture = admin.consulterFacture(numero);
			System.out.println("Facture : \n" + facture);
		    } catch (Exception e) {
			System.out.println("Erreur : " + e.getStackTrace());
		    }

		}
		if (numero.equalsIgnoreCase("A")) {
		    break;
		}

		admin.supprimerCompteUtilisateur(user);
		break;
	    case "CO":
		System.out.println("Liste connexions");

		for (ConnexionAttempt connexion : admin.getAllTheConnexionAttempt()) {
		    System.out.println("Connexion : " + connexion.toString());
		}
		break;
	    case "UI":
		System.out.println("Utilisateur inactifs");

		for (Utilisateur tempUser : admin.retrouverUtilisateursInactifs()) {
		    System.out.println("Utilisateur : " + tempUser.getId());
		}
		break;
	    case "NB":
		String answerNB = "";
		System.out.println("Nombre de connexions");

		// ANNEE DEBUT
		int anneeDebutNB = 0;
		while (anneeDebutNB < 1990 || anneeDebutNB >= 2022) {
		    System.out.println("Entrez l'année de début ou 'A' pour annuler : \n");

		    try {
			answerNB = myScanner.nextLine();
			if (answerNB.equalsIgnoreCase("A")) {
			    break;
			}
			anneeDebutNB = Integer.parseInt(answerNB);
		    } catch (Exception e) {
			System.out.println("Vous devez entrer une année valide.");
		    }
		}
		if (answerNB.equalsIgnoreCase("A")) {
		    break;
		}
		String anneeDebutStrNB = Integer.toString(anneeDebutNB);

		// MOIS DEBUT
		int moisDebutNB = 0;
		while (moisDebutNB <= 0 || moisDebutNB > 12) {
		    System.out.println("Entrez le mois de début ou 'A' pour annuler : \n");
		    try {
			answerNB = myScanner.nextLine();
			if (answerNB.equalsIgnoreCase("A")) {
			    break;
			}
			moisDebutNB = Integer.parseInt(answerNB);
		    } catch (Exception e) {
			System.out.println("Vous devez entrer un mois  valide.");
		    }
		}
		if (answerNB.equalsIgnoreCase("A")) {
		    break;
		}
		String moisDebutStrNB = Integer.toString(moisDebutNB);

		// JOUR DEBUT
		int jourDebutNB = 0;
		while (jourDebutNB <= 0 || jourDebutNB > 30) {
		    System.out.println("Entrez le jour de début ou 'A' pour annuler : \n");
		    try {
			answerNB = myScanner.nextLine();
			if (answerNB.equalsIgnoreCase("A")) {
			    break;
			}
			jourDebutNB = Integer.parseInt(answerNB);
		    } catch (Exception e) {
			System.out.println("Vous devez entrer un jour  valide.");
		    }
		}
		if (answerNB.equalsIgnoreCase("A")) {
		    break;
		}
		String jourDebutStrNB = Integer.toString(jourDebutNB);

		// ANNEE FIN
		int anneeFinNB = 0;
		while (anneeFinNB <= 1990 || anneeFinNB > 2022) {
		    System.out.println("Entrez l'année de fin ou 'A' pour annuler : \n");
		    try {
			answerNB = myScanner.nextLine();
			if (answerNB.equalsIgnoreCase("A")) {
			    break;
			}
			anneeFinNB = Integer.parseInt(answerNB);
		    } catch (Exception e) {
			System.out.println("Vous devez entrer une année valide.");
		    }
		}
		if (answerNB.equalsIgnoreCase("A")) {
		    break;
		}
		String anneeFinStrNB = Integer.toString(anneeFinNB);

		// MOIS FIN
		int moisFinNB = 0;
		while (moisFinNB <= 0 || moisFinNB > 12) {
		    System.out.println("Entrez le mois de fin ou 'A' pour annuler : \n");
		    try {
			answerNB = myScanner.nextLine();
			if (answerNB.equalsIgnoreCase("A")) {
			    break;
			}
			moisFinNB = Integer.parseInt(answerNB);
		    } catch (Exception e) {
			System.out.println("Vous devez entrer un mois  valide.");
		    }
		}
		if (answerNB.equalsIgnoreCase("A")) {
		    break;
		}
		String moisFinStrNB = Integer.toString(moisFinNB);

		// JOUR FIN
		int jourFinNB = 0;
		while (jourFinNB <= 0 || jourFinNB > 30) {
		    System.out.println("Entrez le jour de fin ou 'A' pour annuler : \n");
		    try {
			answerNB = myScanner.nextLine();
			if (answerNB.equalsIgnoreCase("A")) {
			    break;
			}
			jourFinNB = Integer.parseInt(answerNB);
		    } catch (Exception e) {
			System.out.println("Vous devez entrer un jour valide.");
		    }
		}
		if (answerNB.equalsIgnoreCase("A")) {
		    break;
		}
		String jourFinStrNB = Integer.toString(jourFinNB);

		SimpleDateFormat formatterNB = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println(anneeDebutStrNB + "/" + moisDebutStrNB + "/" + jourDebutStrNB + " -> "
			+ anneeFinStrNB + " / " + moisFinStrNB + " / " + jourFinStrNB);
		try {
		    Date dateDebut = formatterNB.parse(anneeDebutStrNB + "/" + moisDebutNB + "/" + jourDebutStrNB);
		    Date dateFin = formatterNB.parse(anneeFinStrNB + "/" + moisFinStrNB + "/" + jourFinStrNB);

		    System.out.println("Nombre de connexions : " + admin.getNumberOfConnexionFor(dateDebut, dateFin));
		} catch (Exception e) {
		    throw new AdvCompException(e);
		}
		break;
	    case "F":
		System.out.println("Toutes les factures");
		try {
		    for (Facture tempFacture : admin.getAllTheFacture()) {
			System.out.println("Facture : " + tempFacture.toString());
		    }
		} catch (Exception e) {
		    throw new AdvCompException(e);
		}
		break;

	    case "U":
		System.out.println("TROUVER UTILISATEUR");
		myScanner = new Scanner(System.in);
		login = "";
		user = null;
		while (user == null) {

		    System.out.println("Entrez un login ou 'A' pour annuler : ");
		    login = myScanner.nextLine();
		    if (login.equalsIgnoreCase("A")) {
			break;
		    }
		    user = admin.findUser(login);
		    if (user != null) {
			System.out.println("Client : " + user.toString());
		    }

		}

		break;

	    default:
		break;
	    }

	}
	System.out.println("Deconnecté.");
	prompt(serveur);
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

	    System.out.println("Entrez un mot de passe (au moins 5 caractères) : \n");
	    secret = myScanner.nextLine();
	}

	return secret;
    }

    private static String doCreateLoginPrompt() {
	Scanner myScanner = new Scanner(System.in);
	String login = " ";
	while (login.length() < 5 && !login.equalsIgnoreCase("A")) {
	    System.out.println("Entrez un login (au moins 5 caractères) ou 'A' pour annuler : \n");
	    login = myScanner.nextLine();
	}

	return login;
    }

    private static String doContinueChoice() {
	Scanner myScanner = new Scanner(System.in);
	String poursuivre = "";
	while (!(poursuivre.toUpperCase().equals("O")) && !(poursuivre.toUpperCase().equals("N"))) {

	    System.out.println("Voulez vous continuer ? (0/N) : \n");
	    poursuivre = myScanner.nextLine();
	}

	return poursuivre;
    }

    private static String doOperateurPrompt() {

	Scanner myScanner = new Scanner(System.in);
	String operateur = "";
	while (!(operateur.equals("*")) && !(operateur.equals("/")) && !(operateur.equals("+"))
		&& !(operateur.equals("-"))) {
	    System.out.println("Entrez un operateur : \n");
	    operateur = myScanner.nextLine();
	}

	return operateur;
    }

    private static Double doFacteurPrompt() {
	System.out.println("Entrez une valeur (Double) : \n");
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
		&& !(choice.toUpperCase().equals("CO".toUpperCase()))
		&& !(choice.toUpperCase().equals("NB".toUpperCase()))
		&& !(choice.toUpperCase().equals("U".toUpperCase()))
		&& !(choice.toUpperCase().equals("F".toUpperCase()))
		&& !(choice.toUpperCase().equals("UI".toUpperCase()))
		&& !(choice.toUpperCase().equals("D".toUpperCase()))
		&& !(choice.toUpperCase().equals("R".toUpperCase()))) {
	    System.out.println("Que voulez vous faire ?\n");
	    Scanner myScanner = new Scanner(System.in);
	    System.out.println("" + "#\t'V' pour valider un compte\n" //
		    + "#\t'I' pour invalider un compte\n" //
		    + "#\t'S' pour supprimer un compte \n" //
		    + "#\t'G' pour générer une facture \n" //
		    + "#\t'C' pour Consulter une facture \n" //
		    + "#\t'CO' pour avoir la liste des connexions \n" //
		    + "#\t'NB' pour avoir le nombre de connexion entre deux dates \n" //
		    + "#\t'U' pour trouver un utilisateur \n" //
		    + "#\t'F' pour consulter toutes les factures \n" //
		    + "#\t'UI' pour trouver tous les utilisateurs inactifs \n"//
		    + "#\t'R' pour régler une facture \n" + "#\t'D' pour vous déconnecter.\n");
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
	    System.out.println(
		    "" + "#\t'B' pour Calcul basique \n" + "#\t'C' pour Chainé \n" + "#\t'D' pour vous déconnecter.\n");
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

	    System.out.println(
		    "" + "#\t'A' pour Admin \n" + "#\t'U' pour Utilisateur \n" + "#\t'C' pour creer un compte.\n");
	    choice = myScanner.nextLine();
	    System.out.println("Vous avez entré : " + choice);

	}
	return choice;
    }

    private static String doLoginPrompt(int i) {
	Scanner myScanner = new Scanner(System.in);
	if (i == 0) {
	    System.out.println("Entrez votre login ou 'A' pour annuler : (CLIENT2)\n");
	} else {
	    System.out.println("Entrez votre login ou 'A' pour annuler : (ADMIN1)\n");
	}

	String login = myScanner.nextLine();

	System.out.println("Vous avez entré : " + login);
	return login;
    }

    private static String doSecretPrompt() {
	Scanner myScanner = new Scanner(System.in);
	System.out.println("Entrez votre mot de pase : (SECRET)\n");
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
