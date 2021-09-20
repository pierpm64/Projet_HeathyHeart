package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Territoire;

public interface TerritoireReposistory  extends JpaRepository<Territoire, Long> {

	Territoire findByIdTerritoire(Long id);

}
