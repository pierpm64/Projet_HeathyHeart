package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Projet;

public interface ProjetRepository extends JpaRepository<Projet, Long>{

	Projet findByIdProjet(Long id);
}
