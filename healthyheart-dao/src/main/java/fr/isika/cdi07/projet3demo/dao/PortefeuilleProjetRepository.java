package fr.isika.cdi07.projet3demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.PortefeuilleProjet;

public interface PortefeuilleProjetRepository extends JpaRepository<PortefeuilleProjet, Long>{

//	List<PorteurProjet> findAllByPorteurProjet(PorteurProjet porteurProjet);
}
