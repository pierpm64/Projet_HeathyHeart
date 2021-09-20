package fr.isika.cdi07.projet3demo.services;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import fr.isika.cdi07.projet3demo.dao.CommentaireRepository;
import fr.isika.cdi07.projet3demo.dao.ParticipationProjetRepository;
import fr.isika.cdi07.projet3demo.dao.RoleRepository;

import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.model.Commentaire;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class CommentaireService implements ICommentaireService{

	@Autowired
	private CommentaireRepository commentaireRepo;
	
	@Autowired
	private IParticipationProjetService participationService;
	
	@Autowired
	private UtilisateurService utilisateurService;

	
	@Override
	public Optional<Commentaire> getCommentaireById(long id) {
		return commentaireRepo.findById(id);	
	}

	@Override
	public Commentaire saveCommentaire(Commentaire commentaire) {
		commentaire.setDate(Date.from(Instant.now()));
		return commentaireRepo.save(commentaire);
	}

	@Override
	public void deleteCommentaireById(long id) {
		commentaireRepo.deleteById(id);
		
	}

	@Override
	public List<Commentaire> getCommentairesList(Projet projet) {
		return commentaireRepo.findAll().stream()
										.filter(c -> c.getProjet().equals(projet))
										.collect(Collectors.toList());
	}

	@Override
	public boolean hasRoleToComment(Projet projet, String userEmail) {		
		String porteurEmail = projet.getPortefeuilleprojet().getPorteurprojet()
									.getRole().getUtilisateur().getEmail();
		if(userEmail.equalsIgnoreCase(porteurEmail))
			return true;
		
		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		Optional<ParticipationProjet> ppFound = participationService
									.getParticipationByProjetAndUser(projet, user);
		
		return ppFound.isPresent();
	}

	@Override
	public boolean isAnonymeForComment(Projet projet, String userEmail) {
		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		return participationService.checkIfAnonyme(projet, user);

	}


}
