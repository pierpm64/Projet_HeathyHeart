package fr.isika.cdi07.projet3demo.services;

import java.util.List;
import java.util.Optional;

import fr.isika.cdi07.projet3demo.model.Don;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

public interface IDonService<T extends Don> {

	List<T> afficherDons();
	Long compterDons();
	StatutDon enregistrerDansLaBase(T don, ParticipationProjet participationProjet, Utilisateur user);
	Optional<T> obtenirDonById(long id);
	void supprimerDonById(long id);
	List<T> getListDonsByStatut(StatutDon statutDon);
}
