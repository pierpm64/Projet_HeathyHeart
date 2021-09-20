package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import fr.isika.cdi07.projet3demo.model.DonMonetaire;

@Repository
public interface DonMonetaireRepository extends JpaRepository<DonMonetaire, Long>{

}
