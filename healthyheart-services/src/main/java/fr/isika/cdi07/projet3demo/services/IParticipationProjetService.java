package fr.isika.cdi07.projet3demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public interface IParticipationProjetService {

	ParticipationProjet saveParticipation(ParticipationProjet participationProjet);
	int countParticipationsByProjet(Projet projet);
	Optional<ParticipationProjet> getParticipationById(long id);
	Optional<ParticipationProjet> getParticipationByProjetAndUser(Projet projet, Utilisateur user);
	List<ParticipationProjet> getAllParticipationsByProjet(Projet projet, StatutDon statutDon);
	List<ParticipationProjet> getAllApprovedParticipationFromUser(Utilisateur user);
	boolean checkIfAnonyme(Projet projet, Utilisateur user);
	void updateStatutDon(ParticipationProjet participationProjet, StatutDon statutDon);
	List<ParticipationProjet> getAllParticipationsByStatutDon(StatutDon statutDon);
}
