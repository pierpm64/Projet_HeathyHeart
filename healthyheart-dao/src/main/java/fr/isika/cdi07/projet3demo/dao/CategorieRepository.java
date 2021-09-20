package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Categorie;
import fr.isika.cdi07.projet3demo.model.Territoire;
import fr.isika.cdi07.projet3demo.model.TypeProjet;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{

	Categorie findByIdCategorie(Long id);

}
