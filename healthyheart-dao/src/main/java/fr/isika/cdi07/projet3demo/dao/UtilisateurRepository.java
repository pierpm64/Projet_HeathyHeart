package fr.isika.cdi07.projet3demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String>{
	
	public Utilisateur findUtilisateurByEmail(String email);
	
	public Utilisateur findUtilisateurByEmailAndMdp(String email, String mdp);


}
