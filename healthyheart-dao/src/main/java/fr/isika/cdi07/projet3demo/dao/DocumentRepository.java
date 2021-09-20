package fr.isika.cdi07.projet3demo.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Document;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.TypeLibelleDoc;

public interface DocumentRepository extends JpaRepository<Document, Long>{

	List<Document> findAllByProjet(Optional<Projet> projet);
	
	List<Document> getByProjet(Projet projet);

	Optional<Document> findByProjetAndLibelle(Projet projet, TypeLibelleDoc libelle);

}
