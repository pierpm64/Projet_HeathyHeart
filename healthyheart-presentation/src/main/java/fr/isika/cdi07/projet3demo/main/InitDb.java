package fr.isika.cdi07.projet3demo.main;



import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

import fr.isika.cdi07.projet3demo.controller.ProjetController;
import fr.isika.cdi07.projet3demo.model.Categorie;
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
import fr.isika.cdi07.projet3demo.services.PersisterEnBaseProjet;

@ComponentScan(basePackages = "fr.isika.cdi07.projet3demo")
public class InitDb implements CommandLineRunner{
	
	private static final Logger LOGGER = Logger.getLogger(ProjetController.class.getSimpleName());
	
	@Autowired
	private PersisterEnBaseProjet persisterEnBaseProjet;

	public static void main(String[] args) {
		SpringApplication.run(InitDb.class,args);

	}

	@Override
	public void run(String... args) throws Exception {

		Utilisateur monAdmin = new Utilisateur();
		monAdmin.setEmail("admin@healthyheart.com");
		monAdmin.setMdp("azerty");
		monAdmin.setNom("Cole");
		monAdmin.setPrenom("Chris");
		monAdmin.setDateMaj(Date.from(Instant.now()));
		
		Utilisateur monPorteurProjet = new Utilisateur();
		monPorteurProjet.setEmail("alyssaorano@asso-aidesonprochain.com");
		monPorteurProjet.setMdp("azerty");
		monPorteurProjet.setNom("Orano");
		monPorteurProjet.setPrenom("Alyssa");
		monPorteurProjet.setDateMaj(Date.from(Instant.now()));
		
		Utilisateur monDonateur = new Utilisateur();
		monDonateur.setEmail("billgates@microsoft.com");
		monDonateur.setMdp("azerty");
		monDonateur.setNom("Gates");
		monDonateur.setPrenom("Bill");
		monDonateur.setDateMaj(Date.from(Instant.now()));
		
		
		persisterEnBaseProjet.persisterUtilisateur(monAdmin);
		persisterEnBaseProjet.persisterUtilisateur(monPorteurProjet);
		persisterEnBaseProjet.persisterUtilisateur(monDonateur);
		
		Role monRoleAdmin = new Role();
		monRoleAdmin.setTypeRole(TypeRole.ADMINISTRATEUR);
		monRoleAdmin.setUtilisateur(monAdmin);
		Role monRolePP = new Role();
		monRolePP.setTypeRole(TypeRole.PORTEURPROJET);
		monRolePP.setUtilisateur(monPorteurProjet);
		Role monRoleDonateur = new Role();
		monRoleDonateur.setTypeRole(TypeRole.DONATEUR);
		monRoleDonateur.setUtilisateur(monDonateur);
		
		persisterEnBaseProjet.persisterRole(monRoleAdmin);
		persisterEnBaseProjet.persisterRole(monRolePP);
		persisterEnBaseProjet.persisterRole(monRoleDonateur);
		
		PorteurProjet monPorteur = new PorteurProjet();
		monPorteur.setLibelle("Association Aide Son Prochain");
		monPorteur.setIban("FR39 3940 3905 3900 678");
		monPorteur.setRole(monRolePP);
		monPorteur.setTypePorteur(TypePorteur.ASSOCIATION);
		
		persisterEnBaseProjet.persisterPorteurProjet(monPorteur);
		
		PortefeuilleProjet monPortefeuilleProjet = new PortefeuilleProjet();
		monPortefeuilleProjet.setLibelle("projet Aide Grégoire");
		monPortefeuilleProjet.setPorteurprojet(monPorteur);
		
		persisterEnBaseProjet.persisterPfP(monPortefeuilleProjet);
		
		Territoire france = new Territoire();
		france.setLibelle("France");
		france.setDate(Date.from(Instant.now()));
		
		Territoire belgique = new Territoire();
		belgique.setLibelle("Belgique");
		belgique.setDate(Date.from(Instant.now()));
		
		Territoire suisse = new Territoire();
		suisse.setLibelle("Suisse");
		suisse.setDate(Date.from(Instant.now()));
		
		Territoire espagne = new Territoire();
		espagne.setLibelle("Espagne");
		espagne.setDate(Date.from(Instant.now()));
		
		Territoire portugal = new Territoire();
		portugal.setLibelle("Portugal");
		portugal.setDate(Date.from(Instant.now()));
		
		Territoire angleterre = new Territoire();
		angleterre.setLibelle("Angleterre");
		angleterre.setDate(Date.from(Instant.now()));
		
		persisterEnBaseProjet.persisterTerritoire(france);
		persisterEnBaseProjet.persisterTerritoire(belgique);
		persisterEnBaseProjet.persisterTerritoire(suisse);
		persisterEnBaseProjet.persisterTerritoire(espagne);
		persisterEnBaseProjet.persisterTerritoire(portugal);
		persisterEnBaseProjet.persisterTerritoire(angleterre);
		
		TypeProjet humanitaire = new TypeProjet();
		humanitaire.setLibelle("Humanitaire");
		humanitaire.setDate(Date.from(Instant.now()));
		
		TypeProjet hospitalier = new TypeProjet();
		hospitalier.setLibelle("Hospitalier");
		hospitalier.setDate(Date.from(Instant.now()));
		
		TypeProjet materiel = new TypeProjet();
		materiel.setLibelle("Matériel");
		materiel.setDate(Date.from(Instant.now()));
		
		TypeProjet soin = new TypeProjet();
		soin.setLibelle("Soin");
		soin.setDate(Date.from(Instant.now()));
		
		TypeProjet aideAutonomie = new TypeProjet();
		aideAutonomie.setLibelle("Aide à l'autonomie");
		aideAutonomie.setDate(Date.from(Instant.now()));
		
		TypeProjet autre = new TypeProjet();
		autre.setLibelle("Autre collectif ou individuel");
		autre.setDate(Date.from(Instant.now()));
		
		persisterEnBaseProjet.persisterTypeProjet(humanitaire);
		persisterEnBaseProjet.persisterTypeProjet(hospitalier);
		persisterEnBaseProjet.persisterTypeProjet(materiel);
		persisterEnBaseProjet.persisterTypeProjet(soin);
		persisterEnBaseProjet.persisterTypeProjet(aideAutonomie);
		persisterEnBaseProjet.persisterTypeProjet(autre);
		
		
		Categorie categorie1 = new Categorie();
		categorie1.setTerritoire(france);;
		categorie1.setTypeProjet(aideAutonomie);
		
		Categorie categorie2 = new Categorie();
		categorie2.setTerritoire(angleterre);;
		categorie2.setTypeProjet(materiel);
		
		Categorie categorie3 = new Categorie();
		categorie3.setTerritoire(france);;
		categorie3.setTypeProjet(hospitalier);
		
		Categorie categorie4 = new Categorie();
		categorie4.setTerritoire(france);;
		categorie4.setTypeProjet(aideAutonomie);
		
		Categorie categorie5 = new Categorie();
		categorie5.setTerritoire(france);;
		categorie5.setTypeProjet(autre);
		
		Categorie categorie6 = new Categorie();
		categorie6.setTerritoire(france);;
		categorie6.setTypeProjet(aideAutonomie);
		
		persisterEnBaseProjet.persisterCategorie(categorie1);
		persisterEnBaseProjet.persisterCategorie(categorie2);
		persisterEnBaseProjet.persisterCategorie(categorie3);
		persisterEnBaseProjet.persisterCategorie(categorie4);
		persisterEnBaseProjet.persisterCategorie(categorie5);
		persisterEnBaseProjet.persisterCategorie(categorie6);
		
		
		Projet monProjet = new Projet();
		monProjet.setTitre("test");
		monProjet.setCategorie(categorie1);;
		monProjet.setDateFin(new Date());
		monProjet.setDateMaj(Date.from(Instant.now()));
		monProjet.setDescriptionCourte("testestet");
		monProjet.setDescriptionLongue("jndjdjdjdjd");
		monProjet.setDonMateriel(true);
		monProjet.setDonTemps(true);
		monProjet.setMontantAttendu(156762.0);
		monProjet.setMontantCollecte(0.0);
		monProjet.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet.setStatutDuProjet(StatutProjet.CREE);
		persisterEnBaseProjet.persisterProjet(monProjet);
		
		Projet monProjet2 = new Projet();
		monProjet2.setTitre("test2");
		monProjet2.setCategorie(categorie2);;
		monProjet2.setDateFin(new Date());
		monProjet2.setDateMaj(Date.from(Instant.now()));
		monProjet2.setDescriptionCourte("testestet");
		monProjet2.setDescriptionLongue("jndjdjdjdjd");
		monProjet2.setDonMateriel(true);
		monProjet2.setDonTemps(true);
		monProjet2.setMontantAttendu(156762.0);
		monProjet2.setMontantCollecte(0.0);
		monProjet2.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet2.setStatutDuProjet(StatutProjet.APPROUVE);
		persisterEnBaseProjet.persisterProjet(monProjet2);
		
		Projet monProjet3 = new Projet();
		monProjet3.setTitre("test3");
		monProjet3.setCategorie(categorie3);;
		monProjet3.setDateFin(new Date());
		monProjet3.setDateMaj(Date.from(Instant.now()));
		monProjet3.setDescriptionCourte("testestet");
		monProjet3.setDescriptionLongue("jndjdjdjdjd");
		monProjet3.setDonMateriel(true);
		monProjet3.setDonTemps(true);
		monProjet3.setMontantAttendu(156762.0);
		monProjet3.setMontantCollecte(0.0);
		monProjet3.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet3.setStatutDuProjet(StatutProjet.PUBLIE);
		persisterEnBaseProjet.persisterProjet(monProjet3);
		
		Projet monProjet4 = new Projet();
		monProjet4.setTitre("test3");
		monProjet4.setCategorie(categorie4);;
		monProjet4.setDateFin(new Date());
		monProjet4.setDateMaj(Date.from(Instant.now()));
		monProjet4.setDescriptionCourte("testestet");
		monProjet4.setDescriptionLongue("jndjdjdjdjd");
		monProjet4.setDonMateriel(true);
		monProjet4.setDonTemps(true);
		monProjet4.setMontantAttendu(156762.0);
		monProjet4.setMontantCollecte(0.0);
		monProjet4.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet4.setStatutDuProjet(StatutProjet.PUBLIE);
		persisterEnBaseProjet.persisterProjet(monProjet4);
		
		Projet monProjet5 = new Projet();
		monProjet5.setTitre("test3");
		monProjet5.setCategorie(categorie5);;
		monProjet5.setDateFin(new Date());
		monProjet5.setDateMaj(Date.from(Instant.now()));
		monProjet5.setDescriptionCourte("testestet");
		monProjet5.setDescriptionLongue("jndjdjdjdjd");
		monProjet5.setDonMateriel(true);
		monProjet5.setDonTemps(true);
		monProjet5.setMontantAttendu(156762.0);
		monProjet5.setMontantCollecte(0.0);
		monProjet5.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet5.setStatutDuProjet(StatutProjet.PUBLIE);
		persisterEnBaseProjet.persisterProjet(monProjet5);
		
		Projet monProjet6 = new Projet();
		monProjet6.setTitre("test3");
		monProjet6.setCategorie(categorie6);;
		monProjet6.setDateFin(new Date());
		monProjet6.setDateMaj(Date.from(Instant.now()));
		monProjet6.setDescriptionCourte("testestet");
		monProjet6.setDescriptionLongue("jndjdjdjdjd");
		monProjet6.setDonMateriel(true);
		monProjet6.setDonTemps(true);
		monProjet6.setMontantAttendu(156762.0);
		monProjet6.setMontantCollecte(0.0);
		monProjet6.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet6.setStatutDuProjet(StatutProjet.PUBLIE);
		persisterEnBaseProjet.persisterProjet(monProjet6);
		
		Projet monProjet7 = new Projet();
		monProjet7.setTitre("test3");
		monProjet7.setCategorie(categorie6);;
		monProjet7.setDateFin(new Date());
		monProjet7.setDateMaj(Date.from(Instant.now()));
		monProjet7.setDescriptionCourte("testestet");
		monProjet7.setDescriptionLongue("jndjdjdjdjd");
		monProjet7.setDonMateriel(true);
		monProjet7.setDonTemps(true);
		monProjet7.setMontantAttendu(156762.0);
		monProjet7.setMontantCollecte(0.0);
		monProjet7.setPortefeuilleprojet(monPortefeuilleProjet);
		monProjet7.setStatutDuProjet(StatutProjet.REJETE);
		persisterEnBaseProjet.persisterProjet(monProjet6);
		
	}

}
