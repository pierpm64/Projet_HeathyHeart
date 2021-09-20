package fr.isika.cdi07.projet3demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.CategorieRepository;
import fr.isika.cdi07.projet3demo.dao.PortefeuilleProjetRepository;
import fr.isika.cdi07.projet3demo.dao.PorteurProjetRepository;
import fr.isika.cdi07.projet3demo.dao.ProjetRepository;
import fr.isika.cdi07.projet3demo.dao.RoleRepository;
import fr.isika.cdi07.projet3demo.dao.TerritoireRepository;
import fr.isika.cdi07.projet3demo.dao.TypeProjetRepository;
import fr.isika.cdi07.projet3demo.dao.UtilisateurRepository;
import fr.isika.cdi07.projet3demo.model.Categorie;
import fr.isika.cdi07.projet3demo.model.PortefeuilleProjet;
import fr.isika.cdi07.projet3demo.model.PorteurProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;
import fr.isika.cdi07.projet3demo.model.Territoire;
import fr.isika.cdi07.projet3demo.model.TypeProjet;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class PersisterEnBaseProjet {

	@Autowired
	private UtilisateurRepository utilisateurRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PorteurProjetRepository porteurProjetRepo;
	@Autowired
	private PortefeuilleProjetRepository pfPRepo;
	@Autowired
	private TerritoireRepository territoireRepo;
	@Autowired
	private TypeProjetRepository typeProjRepo;
	@Autowired
	private CategorieRepository categorieRepo;
	@Autowired
	private ProjetRepository projetRepo;
	
	
	public Utilisateur persisterUtilisateur(Utilisateur utilisateur) {
		return utilisateurRepo.save(utilisateur);
	}
	
	public Role persisterRole(Role role) {
		return roleRepo.save(role);
	}
	
	public PorteurProjet persisterPorteurProjet(PorteurProjet porteurProjet) {
		return porteurProjetRepo.save(porteurProjet);
	}
	
	public PortefeuilleProjet persisterPfP (PortefeuilleProjet pfP) {
		return pfPRepo.save(pfP);
	}
	
	public Territoire persisterTerritoire(Territoire territoire){
		return territoireRepo.save(territoire);
	}
	
	public TypeProjet persisterTypeProjet(TypeProjet tp){
		return typeProjRepo.save(tp);
	}
	
	public Categorie persisterCategorie(Categorie categorie){
		return categorieRepo.save(categorie);
	}
	
	public Projet persisterProjet(Projet projet){
		return projetRepo.save(projet);
	}	

}
