package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Favori;

public interface FavoriRepository extends JpaRepository<Favori, Long> {

}