package fr.isika.cdi07.projet3demo.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.ProjetRepository;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;
import fr.isika.cdi07.projet3demo.model.StatutProjet;

@Service
public class ProjetService {

	private static final Logger LOGGER = Logger.getLogger(ProjetService.class.getSimpleName());


	@Autowired
	private ProjetRepository projetRepo;


	public Projet ajoutProjet(Projet projet) {	
		projet.setDateMaj(Date.from(Instant.now()));
		projet.setMontantCollecte(Double.valueOf(0));
		projet.setStatutDuProjet(StatutProjet.CREE);

		return projetRepo.save(projet);
	}

	public Projet saveProjet(Projet projet) {	
		return projetRepo.save(projet);
	}

	public List<Projet> afficherAllProjet() {

		return projetRepo.findAll();
	}



	public List<Projet> listProjetInfo(String etat) {
		List<Projet> projets = projetRepo.findAll();
		List<Projet> projetsSelection = new ArrayList<Projet>();

		LOGGER.info("etat demandé :" +etat);

		for(Projet monProjet : projets) {
			if(etat.equalsIgnoreCase("create")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.CREE)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("approved")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.APPROUVE)) {
					continue;
				}
			}			
			if(etat.equalsIgnoreCase("wait")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.EN_ATTENTE)) {
					continue;
				}
			}

			if(etat.equalsIgnoreCase("reject")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.REJETE)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("published")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("completed")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.TERMINE)) {
					continue;
				}
			}
			if (etat.equalsIgnoreCase("actif")) {
				if(monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("deleted")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
					continue;
				}
			}

			projetsSelection.add(monProjet);
		}

		return projetsSelection;
	}


	public List<Projet> listProjetInfoForUser(String etat, Role role) {
		List<Projet> projets = getListProjet(role);
		List<Projet> projetsSelection = new ArrayList<Projet>();

		LOGGER.info("etat demandé :" +etat);

		for(Projet monProjet : projets) {
			if(etat.equalsIgnoreCase("create")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.CREE)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("approved")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.APPROUVE)) {
					continue;
				}
			}			
			if(etat.equalsIgnoreCase("wait")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.EN_ATTENTE)) {
					continue;
				}
			}

			if(etat.equalsIgnoreCase("reject")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.REJETE)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("published")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("completed")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.TERMINE)) {
					continue;
				}
			}
			if (etat.equalsIgnoreCase("actif")) {
				if(monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
					continue;
				}
			}
			if(etat.equalsIgnoreCase("deleted")) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
					continue;
				}
			}

			projetsSelection.add(monProjet);
		}

		return projetsSelection;
	}

	public List<Projet> afficherProjetPublie() {
		List<Projet> projets = projetRepo.findAll();
		List<Projet> projetsPublies = new ArrayList<Projet>();
		for(Projet projet : projets) {
			if(projet.getStatutDuProjet().equals(StatutProjet.PUBLIE))
				projetsPublies.add(projet);
		}
		return projetsPublies;
	}

	public Optional<Projet> getProjetById(long id) {
		return projetRepo.findById(id);
	}

	public Projet getProjetByIdNoOptional(long id) {
		return projetRepo.findByIdProjet(id);
	}



	public List<Projet> getListProjet(Role role) {
		List<Projet> allProjet = projetRepo.findAll();
		List<Projet> selectionProjet = new ArrayList<Projet>();

		allProjet.stream()
		.filter(p -> p.getPortefeuilleprojet().getPorteurprojet().getRole().equals(role))
		.forEach(p -> selectionProjet.add(p));



		return selectionProjet;
	}


	public List<Projet> rechercherProjetParTitre(String titre){
		return projetRepo.findAll().stream()
				.filter(p -> p.getTitre().toUpperCase().contains(titre.toUpperCase()))
				.collect(Collectors.toList());
	}

	public List<Projet> rechercherProjetParCriteres(String titre, Long idTypeProjet, Long idTerritoire,
			boolean donMateriel, boolean donTemps) {
		if(!donMateriel && !donTemps)
			return filterProjetWithoutBool(titre, idTypeProjet, idTerritoire);
		if(!donMateriel && donTemps)
			return filterProjetWithoutBool(titre, idTypeProjet, idTerritoire)
					.stream()
					.filter(p -> p.isDonTemps())
					.collect(Collectors.toList());
		if(donMateriel && !donTemps)
			return filterProjetWithoutBool(titre, idTypeProjet, idTerritoire)
					.stream()
					.filter(p -> p.isDonMateriel())
					.collect(Collectors.toList());
		else
			return filterProjetWithoutBool(titre, idTypeProjet, idTerritoire)
					.stream()
					.filter(p -> p.isDonMateriel() && p.isDonTemps())
					.collect(Collectors.toList());
	}


	private List<Projet> filterProjetWithoutBool(String titre, Long idTypeProjet, Long idTerritoire) {
		return projetRepo.findAll().stream()
				.filter(p -> p.getTitre().toUpperCase().contains(titre.toUpperCase()))
				.filter(p->p.getCategorie().getTypeProjet().getIdTypeProjet().equals(idTypeProjet) || idTypeProjet.equals(0L))
				.filter(p->p.getCategorie().getTerritoire().getIdTerritoire().equals(idTerritoire) || idTerritoire.equals(0L))
				.collect(Collectors.toList());
	}

	//	public Projet getProjetById(long id) {
	//		Optional<Projet> optionalP = projetRepo.findById(id);
	//		Projet projet = null;
	//		if (optionalP.isPresent()) {
	//			projet = optionalP.get();
	//		} else {
	//			throw new RuntimeException(" Projet not found for id : " + id);
	//		}
	//		return projet;
	//	}





}
