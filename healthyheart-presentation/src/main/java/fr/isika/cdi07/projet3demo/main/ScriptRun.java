package fr.isika.cdi07.projet3demo.main;



import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

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
public class ScriptRun implements CommandLineRunner{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PersisterEnBaseProjet persisterEnBaseProjet;

	public static void main(String[] args) {
		SpringApplication.run(ScriptRun.class,args);

	}

	@Override
	public void run(String... args) throws Exception {
		Utilisateur monutilisateur = new Utilisateur();
		monutilisateur.setEmail("rom@gmail.com");
		monutilisateur.setMdp("blabla");
		monutilisateur.setNom("k");
		monutilisateur.setPrenom("r");
		monutilisateur.setDateMaj(Date.from(Instant.now()));
		
		persisterEnBaseProjet.persisterUtilisateur(monutilisateur);
		
		Role monRole = new Role();
		monRole.setTypeRole(TypeRole.PORTEURPROJET);
		monRole.setUtilisateur(monutilisateur);
		
		persisterEnBaseProjet.persisterRole(monRole);
		PorteurProjet monPorteurProjet = new PorteurProjet();
		monPorteurProjet.setLibelle("yolo");
		monPorteurProjet.setIban("FR1234567890");
		monPorteurProjet.setRole(monRole);
		monPorteurProjet.setTypePorteur(TypePorteur.PRIVE);
		
		persisterEnBaseProjet.persisterPorteurProjet(monPorteurProjet);
		PortefeuilleProjet monPortefeuilleProjet = new PortefeuilleProjet();
		monPortefeuilleProjet.setLibelle("mathune");
		monPortefeuilleProjet.setPorteurprojet(monPorteurProjet);
		
		persisterEnBaseProjet.persisterPfP(monPortefeuilleProjet);
		Territoire monTerritoire = new Territoire();
		monTerritoire.setLibelle("monterritoire");
		monTerritoire.setDate(Date.from(Instant.now()));
		
		persisterEnBaseProjet.persisterTerritoire(monTerritoire);
		TypeProjet monTypeProjet = new TypeProjet();
		monTypeProjet.setLibelle("blabla");
		monTypeProjet.setDate(Date.from(Instant.now()));
		
		persisterEnBaseProjet.persisterTypeProjet(monTypeProjet);
		Categorie maCategorie = new Categorie();
		maCategorie.setTerritoire(monTerritoire);;
		maCategorie.setTypeProjet(monTypeProjet);
		
		persisterEnBaseProjet.persisterCategorie(maCategorie);
		Projet monProjet = new Projet();
		monProjet.setTitre("test");
		monProjet.setCategorie(maCategorie);;
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
		monProjet2.setCategorie(maCategorie);;
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
		monProjet3.setCategorie(maCategorie);;
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
		
	}

}
