package fr.isika.cdi07.projet3demo.services;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.ParticipationProjetRepository;
import fr.isika.cdi07.projet3demo.model.DonMonetaire;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class ParticipationProjetService implements IParticipationProjetService {

	@Autowired
	private ParticipationProjetRepository participationRepo;
	
	@Autowired
	private DonMonetaireService donMonetaireService;
	
	@Override
	public ParticipationProjet saveParticipation(ParticipationProjet participationProjet) {
		participationProjet.setDate(Date.from(Instant.now()));
		return participationRepo.save(participationProjet);
	}
	
	@Override
	public Optional<ParticipationProjet> getParticipationById(long id) {
		return participationRepo.findById(id);
	}
	
	@Override
	public Optional<ParticipationProjet> getParticipationByProjetAndUser(Projet projet, Utilisateur user) {
		return getAllParticipationsByProjet(projet, StatutDon.APPROUVE)
				.stream()
				.filter(pp -> pp.getRole().getUtilisateur().equals(user))
				.findFirst();
	}
	
	@Override
	public List<ParticipationProjet> getAllParticipationsByStatutDon(StatutDon statutDon) {
		return participationRepo.findAll()
				.stream()
				.filter(pp -> pp.getStatutDon().equals(statutDon))
				.collect(Collectors.toList());
	}
	
	@Override
	public int countParticipationsByProjet(Projet projet) {
		return getAllParticipationsByProjet(projet, StatutDon.APPROUVE).size();
	}

	@Override
	public List<ParticipationProjet> getAllParticipationsByProjet(Projet projet, StatutDon statutDon) {
		return participationRepo.findAllByProjetAndStatutDon(projet, statutDon);
	}

	@Override
	public List<ParticipationProjet> getAllApprovedParticipationFromUser(Utilisateur user) {
		return participationRepo.findAll().stream()
				.filter(pp -> pp.getRole().getUtilisateur().equals(user))
				.filter(pp -> pp.getStatutDon().equals(StatutDon.APPROUVE))
				.collect(Collectors.toList());
	}

	@Override
	public void updateStatutDon(ParticipationProjet participationProjet, StatutDon statutDon) {
		participationProjet.setStatutDon(statutDon);
		saveParticipation(participationProjet);
		Optional<DonMonetaire> opt = getDonFromParticipation(participationProjet);
		if(opt.isPresent() && statutDon.equals(StatutDon.APPROUVE)) {
			donMonetaireService.addMontantDansCollecteProjet(opt.get(), participationProjet);
		}
			
	}

	@Override
	public boolean checkIfAnonyme(Projet projet, Utilisateur user) {
		Optional<ParticipationProjet> optParticipation = getAllApprovedParticipationFromUser(user)
				.stream()
				.filter(pp -> pp.isAnonyme() && pp.getProjet().equals(projet))
				.findFirst();
		return optParticipation.isPresent();
	}

	private Optional<DonMonetaire> getDonFromParticipation(ParticipationProjet participation) {
		return donMonetaireService.afficherDons()
				.stream()
				.filter(d -> d.getParticipationProjet().equals(participation))
				.findFirst();
	}



	

	
	

}
