package fr.isika.cdi07.projet3demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.StatutDon;

public interface ParticipationProjetRepository extends JpaRepository<ParticipationProjet, Long>{
	
	List<ParticipationProjet> findAllByProjet(Projet projet);
	List<ParticipationProjet> findAllByProjetAndStatutDon(Projet projet, StatutDon statutDon);

	
}
