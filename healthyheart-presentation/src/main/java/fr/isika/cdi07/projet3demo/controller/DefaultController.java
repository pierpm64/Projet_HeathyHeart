package fr.isika.cdi07.projet3demo.controller;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.services.MessageRecuService;
import fr.isika.cdi07.projet3demo.services.RoleService;
import fr.isika.cdi07.projet3demo.services.UtilisateurService;

@Controller
public class DefaultController {
	
	private static final Logger LOGGER = Logger.getLogger(MessagerieController.class.getSimpleName());
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	private MessageRecuService messageRecuService;
	
	@Autowired
	private RoleService roleService;


	@GetMapping("/error")
	public String afficherMessageErreur(Model model,HttpSession session) {

		return "error";
	}
	
	
	@GetMapping("/")
	public String afficherPageAccueil(Model model,HttpSession session) {
		boolean isConnected = false;
		Utilisateur monUtilisateur = null;
		Utilisateur admAll = chkADM();
		Long nbMsg = -1L;
		boolean isAdmin = false;
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		String variableRetour = "index";
		if (userEmail != null) {
			monUtilisateur = utilisateurService.chercherUtilisateurParEmail(userEmail);
			nbMsg = messageRecuService.getNbNotReadMsg(monUtilisateur);
			if (roleService.chkRole(monUtilisateur, TypeRole.ADMINISTRATEUR)) {
				isAdmin = true;
				nbMsg += messageRecuService.getNbNotReadMsg(admAll);
			}
			isConnected = true;
		} else {
			variableRetour = "indexold";
		}
		model.addAttribute("isConnected", isConnected);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("nbMsg", nbMsg);
		model.addAttribute("Utilisateur", monUtilisateur);
		
		return variableRetour;
	}
	
	@GetMapping("/accueil")
	public String afficherPageAccueilDemo(Model model,HttpSession session) {
		boolean isConnected = false;
		Utilisateur monUtilisateur = null;
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail != null) {
			monUtilisateur = utilisateurService.chercherUtilisateurParEmail(userEmail);
			isConnected = true;
			LOGGER.info("Utilisateur connecte : " + monUtilisateur);
		}
		
		
		
		model.addAttribute("isConnected", isConnected);
		model.addAttribute("Utilisateur", monUtilisateur);
		

		return "indexold";
	}
	
	private Utilisateur chkADM() {
		// check user "ADMIN" existe en base, sinon création
		Utilisateur userAdm = utilisateurService.chercherUtilisateurParEmail("Admin");
		if (userAdm == null) {
			userAdm = new Utilisateur();
			userAdm.setDateMaj((Date.from(Instant.now())));
			userAdm.setEmail("Admin");
			userAdm.setNom("HearlthyHeart");
			userAdm.setPrenom("Administrateur Principal");
			UUID uuid = UUID.randomUUID();
			userAdm.setMdp(uuid.toString());
			return utilisateurService.ajouterUtilisateur(userAdm);
		}
		return userAdm;
	}
	
}
	