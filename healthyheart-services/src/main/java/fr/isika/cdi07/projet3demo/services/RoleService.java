package fr.isika.cdi07.projet3demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import fr.isika.cdi07.projet3demo.dao.RoleRepository;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private IParticipationProjetService participationService;
	
	@Autowired
	private UtilisateurService utilisateurService;

//	public Optional<Role> hasRole(Utilisateur user, TypeRole typeRole) {
//
//		List<Role> roles = roleRepo.findAll();
//		return roles.stream().filter(r -> r.getUtilisateur().equals(user) && r.getTypeRole().equals(typeRole)).findFirst();
//		
//	}
	
	public Role hasRole(Utilisateur user, TypeRole typeRole) {

		List<Role> roles = roleRepo.findAll();
		Optional<Role> optRole = roles.stream().filter(r -> r.getUtilisateur().equals(user) && r.getTypeRole().equals(typeRole)).findFirst();
		if(optRole.isPresent())
			return optRole.get();
		
		Role newRole = new Role();
		newRole.setTypeRole(typeRole);
		newRole.setUtilisateur(user);
		
		return roleRepo.save(newRole);
	}
	
	public Role hasRoleNoSave(Utilisateur user, TypeRole typeRole) {

		List<Role> roles = roleRepo.findAll();
		Optional<Role> optRole = roles.stream().filter(r -> r.getUtilisateur().equals(user) && r.getTypeRole().equals(typeRole)).findFirst();
		if(optRole.isPresent())
			return optRole.get();
		
		Role newRole = new Role();
		newRole.setTypeRole(typeRole);
		newRole.setUtilisateur(user);
		
		return newRole;
		// return roleRepo.save(newRole);
	}
	
	public Optional<Role> testIsPorteurProjet(Utilisateur user) {
		List<Role> listeRolePorteurProjet = roleRepo.findAllByTypeRole(TypeRole.PORTEURPROJET);
		return listeRolePorteurProjet.stream()
				.filter(r -> r.getUtilisateur()
				.equals(user)).findFirst();
			
	}
	
	public Optional<Role> testIsAdmin(Utilisateur user) {
		List<Role> listeRoleAdmin = roleRepo.findAllByTypeRole(TypeRole.ADMINISTRATEUR);
		return listeRoleAdmin.stream()
				.filter(r -> r.getUtilisateur()
						.equals(user)).findFirst();
		
	}

	public boolean chkRole(Utilisateur user, TypeRole typeRole) {
		List<Role> roles = roleRepo.findAll();
		Optional<Role> optRole = roles.stream().filter(r -> r.getUtilisateur().equals(user) && r.getTypeRole().equals(typeRole)).findFirst();
		if(optRole.isPresent())
			return true;
		
		return false;
	}
	
	public List<Role> getAllRolesOfUser(String email){
		return roleRepo.findAll().stream()
				.filter(r -> r.getUtilisateur().getEmail().equalsIgnoreCase(email))
				.collect(Collectors.toList());
	}

	public Role getRoleOfUserForProjet(Projet projet, String userEmail){
		Role rolePorteur = projet.getPortefeuilleprojet().getPorteurprojet()
				.getRole();
		String porteurEmail = rolePorteur.getUtilisateur().getEmail();
		if(userEmail.equalsIgnoreCase(porteurEmail))
			return rolePorteur;
		
		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		Optional<ParticipationProjet> ppFound = participationService
									.getParticipationByProjetAndUser(projet, user);	
		
		return ppFound.isPresent() ? 
				ppFound.get().getRole() : null;

	}
	
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}
	
	
	
}
