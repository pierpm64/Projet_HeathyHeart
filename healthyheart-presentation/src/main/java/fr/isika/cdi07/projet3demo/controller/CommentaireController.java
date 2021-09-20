package fr.isika.cdi07.projet3demo.controller;

import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.isika.cdi07.projet3demo.model.Commentaire;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.modelform.CommentaireForm;
import fr.isika.cdi07.projet3demo.services.ICommentaireService;
import fr.isika.cdi07.projet3demo.services.ProjetService;
import fr.isika.cdi07.projet3demo.services.RoleService;

@Controller
public class CommentaireController {
	
	private static final Logger logger = Logger.getLogger(DonController.class.getSimpleName());
	
	@Autowired
	private ICommentaireService commentaireService;
	
	@Autowired
	private ProjetService projetService;
	
	@Autowired
	private RoleService roleService;
	
	
	@GetMapping("/afficherListeCommentaires/projet")
	public String afficherListeCommentaires(@RequestParam long id, Model model, HttpSession session) {		
		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
				
		Optional<Projet> projet = projetService.getProjetById(id);
		if(!projet.isPresent()) 		
			return "noFoundProjet";

		CommentaireForm commentaireForm = new CommentaireForm(id);

		if(userEmail == null) {
			model.addAttribute("listeComm", commentaireService.getCommentairesList(projet.get()));
			model.addAttribute("commentaireForm", commentaireForm);
			return "liste_commentaires";
		}		
		
		commentaireForm.setAllowedToComment(commentaireService.hasRoleToComment(projet.get(), userEmail));
		if(commentaireForm.isAllowedToComment()) 
			commentaireForm.setRole(roleService.getRoleOfUserForProjet(projet.get(), userEmail));

		model.addAttribute("listeComm", commentaireService.getCommentairesList(projet.get()));
		model.addAttribute("commentaireForm", commentaireForm);
		
		return "liste_commentaires";
	}
	
	@PostMapping("/ajouterCommentaire")
	public String ajouterCommentaire(
			@ModelAttribute("commentaireForm") CommentaireForm commentaireForm) {
		Optional<Projet> projet = projetService.getProjetById(commentaireForm.getIdProjet());

		Commentaire newCommentaire = new Commentaire()
				.withLibelle(commentaireForm.getLibelle())
				.withMessage(commentaireForm.getMessage())
				.withRole(commentaireForm.getRole())
				.withProjet(projet.get());
		
		commentaireService.saveCommentaire(newCommentaire);
		return "redirect:/afficherListeCommentaires/projet?id=" + commentaireForm.getIdProjet();
	}
	
}
