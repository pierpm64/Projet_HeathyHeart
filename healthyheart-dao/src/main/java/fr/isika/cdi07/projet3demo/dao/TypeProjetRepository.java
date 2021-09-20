package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.TypeProjet;

public interface TypeProjetRepository extends JpaRepository<TypeProjet, Long>{

	TypeProjet findByIdTypeProjet(Long id);

}
