package fr.isika.cdi07.projet3demo.main;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import fr.isika.cdi07.projet3demo.model.Appreciation;
import fr.isika.cdi07.projet3demo.model.Categorie;
import fr.isika.cdi07.projet3demo.model.Historique;
import fr.isika.cdi07.projet3demo.model.MessageInterne;
import fr.isika.cdi07.projet3demo.model.MessageRecu;
import fr.isika.cdi07.projet3demo.model.Notification;
import fr.isika.cdi07.projet3demo.model.PortefeuilleProjet;
import fr.isika.cdi07.projet3demo.model.PorteurProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;
import fr.isika.cdi07.projet3demo.model.StatutProjet;
import fr.isika.cdi07.projet3demo.model.Territoire;
import fr.isika.cdi07.projet3demo.model.TypePorteur;
import fr.isika.cdi07.projet3demo.model.TypeProjet;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.repository.DalProjet;
import fr.isika.cdi07.projet3demo.utils.HibernateUtil;

public class MainProjet3Demo {

	
	
	public static void main(String[] args) {
		
		DalProjet dalProjet = new DalProjet();
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom("gné");
		utilisateur.setPrenom("gno");
		utilisateur.setDateMaj(Date.from(Instant.now()));
		utilisateur.setEmail("gno@gni.com");
		utilisateur.setMdp("gnoki");
		
		dalProjet.persisterUtilisateur(utilisateur);
		
		Utilisateur utilisateurDemo = new Utilisateur();
		utilisateurDemo.setNom("PM");
		utilisateurDemo.setPrenom("Pierre");
		utilisateurDemo.setDateMaj(Date.from(Instant.now()));
		utilisateurDemo.setEmail("pierpm@gmail.com");
		utilisateurDemo.setMdp("motdePasse@Isika92");
		
		dalProjet.persisterUtilisateur(utilisateurDemo);
		
		Utilisateur utilisateurDest = new Utilisateur();
		utilisateurDest.setNom("Demo");
		utilisateurDest.setPrenom("Pierre");
		utilisateurDest.setDateMaj(Date.from(Instant.now()));
		utilisateurDest.setEmail("demo@pierpm.fr");
		utilisateurDest.setMdp("DemoPsw");
		
		dalProjet.persisterUtilisateur(utilisateurDest);
		
		MessageInterne messageTest = new MessageInterne();
		messageTest.setDate(Date.from(Instant.now()));
		messageTest.setContenu("Ceci est un message\nsur plusieurs lignes\npour tests");
		messageTest.setTitre("Message de test");
		messageTest.setEmetteur(utilisateur);
		dalProjet.persisterMessageInterne(messageTest);
		
		MessageRecu messageRecuTest = new MessageRecu();
		messageRecuTest.setMessageInterne(messageTest);
		messageRecuTest.setUtilisateur(utilisateurDemo);
		messageRecuTest.setDateHeure(Date.from(Instant.now()));
		dalProjet.persisterMessageRecu(messageRecuTest);
		
		MessageRecu messageRecuTest2 = new MessageRecu();
		messageRecuTest2.setMessageInterne(messageTest);
		messageRecuTest2.setUtilisateur(utilisateurDest);
		messageRecuTest2.setDateHeure(Date.from(Instant.now()));
		dalProjet.persisterMessageRecu(messageRecuTest2);
		
		MessageInterne messageSecond = new MessageInterne();
		messageSecond.setDate(Date.from(Instant.now()));
		messageSecond.setContenu("Ceci est un second message\nsur plusieurs lignes\npour tests");
		messageSecond.setTitre("deuxieme Message de test");
		messageSecond.setEmetteur(utilisateur);
		dalProjet.persisterMessageInterne(messageSecond);
		
		MessageRecu messageRecuSecond = new MessageRecu();
		messageRecuSecond.setMessageInterne(messageSecond);
		messageRecuSecond.setUtilisateur(utilisateurDemo);
		messageRecuSecond.setDateHeure(Date.from(Instant.now()));
		dalProjet.persisterMessageRecu(messageRecuSecond);
		
		
		Role role = new Role();
		role.setTypeRole(TypeRole.PORTEURPROJET);
		role.setUtilisateur(utilisateur);
		dalProjet.persisterRole(role);
		
		PorteurProjet porteurprojet = new PorteurProjet();
		porteurprojet.setRole(role);
		porteurprojet.setTypePorteur(TypePorteur.PRIVE);
		porteurprojet.setIban("FR76IHHOIHFDQ986986");
		porteurprojet.setLibelle("Projet privé");
		
		dalProjet.persisterPorteurProjet(porteurprojet);
		
		PortefeuilleProjet portefeuilleprojet = new PortefeuilleProjet();
		portefeuilleprojet.setLibelle("Defaut");
		portefeuilleprojet.setPorteurprojet(porteurprojet);
		dalProjet.persisterPortefeuilleProjet(portefeuilleprojet);
		
		TypeProjet typeprojet = new TypeProjet();
		typeprojet.setLibelle("Assistance médicale Test");
		typeprojet.setDate(Date.from(Instant.now()));
		dalProjet.persisterTypeProjet(typeprojet);
		
		Territoire territoire = new Territoire();
		territoire.setLibelle("France");
		territoire.setDate(Date.from(Instant.now()));
		dalProjet.persisterTerritoire(territoire);
		
		Categorie categorie = new Categorie();
		categorie.setTerritoire(territoire);
		categorie.setTypeProjet(typeprojet);
		dalProjet.persisterCategorie(categorie);
		
		Projet projet = new Projet();
		projet.setTitre("Test");
		projet.setDescriptionCourte("testtest");
		projet.setDescriptionLongue("Testtestestest");
		projet.setDateFin(Date.valueOf(LocalDate.now()));
		projet.setDateMaj(Date.from(Instant.now()));
		projet.setDonMateriel(false);
		projet.setDonTemps(false);
		projet.setMontantAttendu(2000.0);
		projet.setMontantCollecte(0.0);
		projet.setStatutDuProjet(StatutProjet.CREE);
		projet.setPortefeuilleprojet(portefeuilleprojet);
		projet.setCategorie(categorie);
		
		dalProjet.persister(projet);
		
		Notification notification = new Notification();
		notification.setLibelle("test");
		notification.setDate(Date.from(Instant.now()));
		
		dalProjet.persisterNotif(notification);
		

		
		
		Historique historique = new Historique();
		historique.setActeur(utilisateur);
		historique.setDateHeure(Date.from(Instant.now()));
		historique.setEtatProjet(StatutProjet.CREE);
		historique.setEvenement("Creation");
		historique.setLibelle("Historique");
		historique.setNotification(notification);
		historique.setProjet(projet);
		
		dalProjet.persisterHisto(historique);
		// Role role = new Role(TypeRole.ADMINISTRATEUR, utilisateur);
		// dalProjet.persisterRole(role);
		
		Appreciation appreciation = new Appreciation();
		appreciation.setRole(role);
		appreciation.setDate(Date.valueOf(LocalDate.now()));
		appreciation.setNote(5);
		appreciation.setProjet(projet);
		
		dalProjet.persisterAppreciation(appreciation);		
		
		List<Projet> listeprojet = dalProjet.findAllProject();
		for(Projet projetTotaux : listeprojet) {
			System.out.println(projetTotaux);
		}
		
		
		projet.setDescriptionCourte("Cmodifier");
		dalProjet.modifierProjet(projet);
		

		for(Projet projetTotaux : listeprojet) {
			System.out.println(projetTotaux);
		}
		
		
		// Relations !!
		
		
		// Persistance des données
		
		
		

		
		// Fermer les ressources
		HibernateUtil.closeAll();
	}
}
