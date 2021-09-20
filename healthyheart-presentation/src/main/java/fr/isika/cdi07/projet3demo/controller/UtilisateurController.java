package fr.isika.cdi07.projet3demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.isika.cdi07.projet3demo.model.Document;
import fr.isika.cdi07.projet3demo.model.Favori;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.StatutProjet;
import fr.isika.cdi07.projet3demo.model.TypeLibelleDoc;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.modelform.LoginForm;
import fr.isika.cdi07.projet3demo.modelform.ProjetDocumentForm;
import fr.isika.cdi07.projet3demo.services.DocumentService;
import fr.isika.cdi07.projet3demo.services.FavoriService;
import fr.isika.cdi07.projet3demo.services.UtilisateurService;

@Controller
public class UtilisateurController {

	private static final String REDIRECT = "redirect:/";

	public static final String EMAIL_UTILISATEUR_CONNECTE = "emailUtilisateurConnecte";

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private AdresseController adresseController;

	@Autowired
	private FavoriService favoriService;
	
	@Autowired
	private DocumentService documentService;

	// Créer Nouvel Utilisateur
	@GetMapping("/CreerNouvelUtilsateur")
	public String creerNouvelUtilisateur(Model model) {
		Utilisateur utilisateur = new Utilisateur();
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("errmsg", "");
		return "newUtilisateur";
	}

	// Enregistrer un utilisateur
	@PostMapping("/enregistrerUtilisateur")
	public String enregistrerNouvelUtilisateur(@ModelAttribute("utilisateur") Utilisateur utilisateur, Model model,
			HttpSession session) {
		Optional<Utilisateur> tstUser = utilisateurService.chercherOptionalUtilisateurParEmail(utilisateur.getEmail());
		if (tstUser.isPresent()) {
			model.addAttribute("errmsg", "un utilisateur existe déjà avec ce email");
			return "newUtilisateur";
		}
		utilisateurService.ajouterUtilisateur(utilisateur);
		session.setAttribute(EMAIL_UTILISATEUR_CONNECTE, utilisateur.getEmail());
		return REDIRECT;
	}

	// Pour modifier Un utilisateur
	@GetMapping("/showUsersForUpdate/{email}")
	public String afficherLesUtilisateurPourModification(@PathVariable(value = "email") String email, Model model) {
		Optional<Utilisateur> utilisateur = utilisateurService.chercherOptionalUtilisateurParEmail(email);
		if (utilisateur.isPresent()) {
			model.addAttribute("utilisateur", utilisateur.get());
		}
		return "update_utilisateur";
	}

	// Afficher Tous les utilisateurs
	@GetMapping("/showAllUsers")
	public String afficherTousUtilisateurs(Model model) {
		model.addAttribute("listeUtilisateurs", utilisateurService.retournerTousLesUtilisateur());
		return "listeUtilisateurs";
	}

	// login success
	@GetMapping("/utilisateurConnecte")
	public String utilisateurConnecte(Model model, HttpSession session) {
		model.addAttribute(EMAIL_UTILISATEUR_CONNECTE, session.getAttribute(EMAIL_UTILISATEUR_CONNECTE));
		String message = "Bonjour, ";
		model.addAttribute("message", message);
		model.addAttribute("prenomUtilisateur", session.getAttribute("prenomUtilisateur"));

		return "login_success";
	}

	// Se déconnecter
	@GetMapping("/deconnexion")
	public String deconnecter(HttpSession session) {

		session.removeAttribute(EMAIL_UTILISATEUR_CONNECTE);
		session.removeAttribute("prenomUtilisateur");
		return "redirect:/accueil";
	}

	// show connexion Form
	@GetMapping("/showConnexionForm")
	public String saisirInformationUtilisateur(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "seConnecter";
	}

	// Connexion utilisateur
	@PostMapping("/connecterUtilisateur")
	public String connecterUtilisateur(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session) {
		// TODO valider le contenu
		Optional<Utilisateur> optionalUtilisateur = utilisateurService
				.chercherUtilisateurParEmailEtMdp(loginForm.getEmail(), loginForm.getPassword());

		if (optionalUtilisateur.isPresent()) {
			session.setAttribute(EMAIL_UTILISATEUR_CONNECTE, optionalUtilisateur.get().getEmail());
			session.setAttribute("prenomUtilisateur", optionalUtilisateur.get().getPrenom());
			return REDIRECT;

		}
		return REDIRECT + "showConnexionForm";

	}

	// Afficher les favoris d'un utilisateur connecté
	@GetMapping("/ShowMyfavoriteProjects")
	public String afficherMesFavoris(Model model, HttpSession session) {
		String emailUserConnecte = (String) session.getAttribute(UtilisateurController.EMAIL_UTILISATEUR_CONNECTE);
		if (emailUserConnecte == null) {
			return "redirect:/showConnexionForm";
		}
		List<Favori> listeFavoris = favoriService.afficherMesFavoris(emailUserConnecte);
		List<Projet> maListeProjetsFavoris = new ArrayList<>();
		for (Favori fav : listeFavoris) {
			maListeProjetsFavoris.add(fav.getProjet());
		}
		if (!maListeProjetsFavoris.isEmpty()) {
			model.addAttribute("listeProjetsRechercheMulticriteres", maListeProjetsFavoris);
			List<ProjetDocumentForm> pdf = new ArrayList<ProjetDocumentForm>();
			for (Projet monProjet : maListeProjetsFavoris) {
				if (!monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE)) {
					continue;
				}
				ProjetDocumentForm monProjetform = new ProjetDocumentForm();
				monProjetform.setProjet(monProjet);
				// allProjetPublie.forEach(projet ->
				// projet.setFavori(favoriService.estFavori(projet.getIdProjet(),
				// emailUserConnecte)));
				monProjet.setFavori(true);
				Optional<Document> monDoc = Optional.empty();
				monDoc = documentService.findbyProjetAndLibelle(monProjet, TypeLibelleDoc.IMAGE_PRINCIPALE);
//				if (!monDoc.isPresent()) {
//					monDoc = documentService.findbyProjetAndLibelle(monProjet, TypeLibelleDoc.IMAGE_SECONDE);
//					if (!monDoc.isPresent()) {
//						monDoc = documentService.findbyProjetAndLibelle(monProjet, TypeLibelleDoc.IMAGE_TROISIEME);
//					}
//				}
				if (!monDoc.isPresent()) {
					monProjetform.setIdImage(-1L);
				} else {
					monProjetform.setIdImage(monDoc.get().getIdDocument());
				}
				pdf.add(monProjetform);
			}
			model.addAttribute("listProjetPublie", pdf);
			return "liste_mesFavoris";
		}
		return "pas_de_favori";
	}

}
