package fr.isika.cdi07.projet3demo.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.UtilisateurRepository;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class UtilisateurService {
	
	@Autowired
	private UtilisateurRepository utilisateurRepo;
	
	public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
		utilisateur.setDateMaj(Date.from(Instant.now()));
		return utilisateurRepo.save(utilisateur);
	}

	public Optional<Utilisateur> chercherUtilisateurParEmailEtMdp(String email, String mdp) {
		return Optional.ofNullable(utilisateurRepo.findUtilisateurByEmailAndMdp(email, mdp));
	}
	
	public List<Utilisateur> retournerTousLesUtilisateur(){
		return utilisateurRepo.findAll();
	}
	
	public Utilisateur chercherUtilisateurParEmail(String email) {
		return utilisateurRepo.findUtilisateurByEmail(email);
	}
	
	public Optional<Utilisateur> chercherOptionalUtilisateurParEmail(String email) {
		return Optional.ofNullable(utilisateurRepo.findUtilisateurByEmail(email));
	}

	public boolean isUtilisateurConnecte(String email){
		return utilisateurRepo.existsById(email);
	}

}
