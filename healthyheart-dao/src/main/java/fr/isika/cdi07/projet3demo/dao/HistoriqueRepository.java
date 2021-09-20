package fr.isika.cdi07.projet3demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Historique;
import fr.isika.cdi07.projet3demo.model.Projet;

public interface HistoriqueRepository extends JpaRepository<Historique, Long>{

	public List<Historique> getByProjet(Projet projet);
}
