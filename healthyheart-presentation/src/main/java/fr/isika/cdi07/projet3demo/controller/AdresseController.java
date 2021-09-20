package fr.isika.cdi07.projet3demo.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.isika.cdi07.projet3demo.model.Adresse;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.services.AdresseService;
import fr.isika.cdi07.projet3demo.services.UtilisateurService;



@Controller
public class AdresseController {

	private static final Logger LOGGER = Logger.getLogger(ProjetController.class.getSimpleName());

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private AdresseService adresseService;

	// afficher création adresse
	@GetMapping("/ShowNewAdresseForm")
	public String showNewProjetForm(Model model, Utilisateur utilisateur2) {

		System.out.println("****************EMAIL******************* " + utilisateur2.getEmail());
		//String emailOfUser = session.getId();
//		Utilisateur utilisateur = null;
//		if(emailOfUser != null)
//			utilisateur = utilisateurService.chercherUtilisateurParEmail(emailOfUser);
		
		Utilisateur utilisateur = utilisateurService.chercherUtilisateurParEmail(utilisateur2.getEmail());
		
		Adresse adresse = new Adresse();

		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("adresse", adresse);


		return "newAdresse";
	}


	@PostMapping("/enregistrerAdresse")
	public String creationAdresse(@ModelAttribute("utilisateur") Utilisateur utilisateur, 
			@ModelAttribute("adresse") Adresse adresse) {
		LOGGER.info("email récupération : " + utilisateur.getEmail());
		
		Utilisateur uTrouve = utilisateurService.chercherUtilisateurParEmail(utilisateur.getEmail());
		
		adresse.setUtilisateur(uTrouve);
		adresseService.ajoutAdresse(adresse);
		return "index";
	}

//	//Update
//	@GetMapping("/showUpdateForm/{id}")
//	public String showUpdateProjetForm(@PathVariable (value = "id") Long id, Model model) {
//
//		// get Projet from the service
//		Optional<Projet> optProjet = projetService.getProjetById(id);
//		if(!optProjet.isPresent())
//			return "noFoundProjet";
//		Optional<Categorie> categorie = categorieService.getCategorieById(id);
//		Optional<Territoire> territoire = territoireService.getTerritoireById(id);
//		Optional<TypeProjet> typeProjet = typeProjetService.getTerritoireById(id);		
//
//		// set Projet as a model attribute to pre-populate the form
//		model.addAttribute("projet", optProjet);
//		model.addAttribute("categorie", categorie);
//		model.addAttribute("typeProjet", typeProjet);
//		model.addAttribute("territoire", territoire);
//		model.addAttribute("listTerritoire", territoireService.afficherAllTerritoire());
//		model.addAttribute("listTypeProjet", typeProjetService.afficherAllTypeProjet());
//
//		return "updateProjet";
//	}


}
