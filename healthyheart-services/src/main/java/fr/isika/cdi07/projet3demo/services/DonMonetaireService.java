package fr.isika.cdi07.projet3demo.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.DonMonetaireRepository;
import fr.isika.cdi07.projet3demo.dao.ParticipationProjetRepository;
import fr.isika.cdi07.projet3demo.dao.ProjetRepository;
import fr.isika.cdi07.projet3demo.model.DonMonetaire;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.TypeParticipation;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class DonMonetaireService implements IDonService<DonMonetaire>{
	
	@Autowired
	private DonMonetaireRepository donMonetaireRepo;
	
	@Autowired
	private IParticipationProjetService participationProjetService;
	
	@Autowired
	private ProjetRepository projetRepo;
	
	@Autowired
	private RoleService roleService;
	
	//NB : surement mettre en type optional pour avoir possibilite de vue dans le controller
	public Optional<Projet> getProjet(long id) {
		return projetRepo.findById(id);
	}

	@Override
	public List<DonMonetaire> afficherDons() {
		return donMonetaireRepo.findAll();
	}

	@Override
	public Long compterDons() {
		return donMonetaireRepo.count();
	}
	
	@Override
	public StatutDon enregistrerDansLaBase(DonMonetaire don, ParticipationProjet participationProjet, Utilisateur user) {
		participationProjet.withTypeParticipation(TypeParticipation.MONETAIRE)
						.withRole(roleService.hasRole(user, TypeRole.DONATEUR));
		checkAndSaveIfSeuilReached(don, participationProjet, user);
		return participationProjet.getStatutDon();
	}

	private void checkAndSaveIfSeuilReached(DonMonetaire don, ParticipationProjet participationProjet, Utilisateur user) {
		if(don.getMontant() > 2000) {
			participationProjet.withStatutDon(StatutDon.EN_ATTENTE);
			participationProjetService.saveParticipation(participationProjet);
			
			saveDonInDB(don, participationProjet);
		}else {
			participationProjet.withStatutDon(StatutDon.APPROUVE);
			participationProjetService.saveParticipation(participationProjet);
			
			addMontantDansCollecteProjet(don, participationProjet);
			
			saveDonInDB(don, participationProjet);
		}
	}

	@Override
	public Optional<DonMonetaire> obtenirDonById(long id) {
		return donMonetaireRepo.findById(id);
	}

	@Override
	public void supprimerDonById(long id) {
		donMonetaireRepo.deleteById(id);
	}	

	public void addMontantDansCollecteProjet(DonMonetaire don, ParticipationProjet participationProjet) {
		Optional<Projet> projetToDonate = projetRepo.findById(participationProjet.getProjet().getIdProjet());
		double newMontantCollecte = projetToDonate.get().getMontantCollecte() + don.getMontant();
		projetToDonate.get().setMontantCollecte(newMontantCollecte);
		projetRepo.save(projetToDonate.get());
	}

	private void saveDonInDB(DonMonetaire don, ParticipationProjet participationProjet) {
		don.withDate(Date.valueOf(LocalDate.now()))
			.withParticipationProjet(participationProjet);
		donMonetaireRepo.save(don);
	}

	@Override
	public List<DonMonetaire> getListDonsByStatut(StatutDon statutDon) {
		return afficherDons()
				.stream()
				.filter(d -> d.getParticipationProjet().getStatutDon().equals(statutDon))
				.collect(Collectors.toList());
	}


}
