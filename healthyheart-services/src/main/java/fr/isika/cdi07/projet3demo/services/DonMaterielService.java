package fr.isika.cdi07.projet3demo.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.DonMaterielRepository;
import fr.isika.cdi07.projet3demo.dao.ParticipationProjetRepository;
import fr.isika.cdi07.projet3demo.model.DonMateriel;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.TypeParticipation;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class DonMaterielService implements  IDonService<DonMateriel>{

	@Autowired
	private DonMaterielRepository donMaterielRepo;
	
	@Autowired
	private IParticipationProjetService participationProjetService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public List<DonMateriel> afficherDons() {
		return donMaterielRepo.findAll();
	}

	@Override
	public Long compterDons() {
		return donMaterielRepo.count();
	}

	@Override
	public StatutDon enregistrerDansLaBase(DonMateriel don, ParticipationProjet participationProjet, Utilisateur user) {
		participationProjet.withTypeParticipation(TypeParticipation.MATERIEL)
						.withRole(roleService.hasRole(user, TypeRole.DONATEUR));
		checkAndSaveIfSeuilReached(don, participationProjet, user);
		return participationProjet.getStatutDon();
	}

	@Override
	public Optional<DonMateriel> obtenirDonById(long id) {
		return donMaterielRepo.findById(id);
	}

	@Override
	public void supprimerDonById(long id) {
		donMaterielRepo.deleteById(id);
		
	}
	
	private void checkAndSaveIfSeuilReached(DonMateriel don, ParticipationProjet participationProjet, Utilisateur user) {
		double totalMateriel = don.getMontant() * don.getQuantite();
		if(totalMateriel > 10000) 
			participationProjet.withStatutDon(StatutDon.EN_ATTENTE);
		else 
			participationProjet.withStatutDon(StatutDon.APPROUVE);
		
			participationProjetService.saveParticipation(participationProjet);			
			saveDonInDB(don, participationProjet);
		
	}

	private void saveDonInDB(DonMateriel don, ParticipationProjet participationProjet) {
		don.withDate(Date.valueOf(LocalDate.now()))
			.withParticipationProjet(participationProjet);
		donMaterielRepo.save(don);
	}

	@Override
	public List<DonMateriel> getListDonsByStatut(StatutDon statutDon) {
		return afficherDons()
				.stream()
				.filter(d -> d.getParticipationProjet().getStatutDon().equals(statutDon))
				.collect(Collectors.toList());
	}

}
