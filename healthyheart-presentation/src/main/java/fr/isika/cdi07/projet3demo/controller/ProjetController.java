package fr.isika.cdi07.projet3demo.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.isika.cdi07.projet3demo.model.Categorie;
import fr.isika.cdi07.projet3demo.model.Document;
import fr.isika.cdi07.projet3demo.model.Favori;
import fr.isika.cdi07.projet3demo.model.Historique;
import fr.isika.cdi07.projet3demo.model.MessageInterne;
import fr.isika.cdi07.projet3demo.model.MessageRecu;
import fr.isika.cdi07.projet3demo.model.PortefeuilleProjet;
import fr.isika.cdi07.projet3demo.model.PorteurProjet;
import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;
import fr.isika.cdi07.projet3demo.model.StatutProjet;
import fr.isika.cdi07.projet3demo.model.Territoire;
import fr.isika.cdi07.projet3demo.model.TypeLibelleDoc;
import fr.isika.cdi07.projet3demo.model.TypePorteur;
import fr.isika.cdi07.projet3demo.model.TypeProjet;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.modelform.ListProjetForm;
import fr.isika.cdi07.projet3demo.modelform.PresentationProjetForm;
import fr.isika.cdi07.projet3demo.modelform.ProjetDocumentForm;
import fr.isika.cdi07.projet3demo.modelform.ProjetForm;
import fr.isika.cdi07.projet3demo.modelform.RechercheMulticriteresForm;
import fr.isika.cdi07.projet3demo.modelform.RechercheParTitreForm;
import fr.isika.cdi07.projet3demo.services.CategorieService;
import fr.isika.cdi07.projet3demo.services.DocumentService;
import fr.isika.cdi07.projet3demo.services.FavoriService;
import fr.isika.cdi07.projet3demo.services.HistoriqueService;
import fr.isika.cdi07.projet3demo.services.MessageRecuService;
import fr.isika.cdi07.projet3demo.services.MessageService;
import fr.isika.cdi07.projet3demo.services.PortefeuilleService;
import fr.isika.cdi07.projet3demo.services.PorteurProjetService;
import fr.isika.cdi07.projet3demo.services.ProjetService;
import fr.isika.cdi07.projet3demo.services.RoleService;
import fr.isika.cdi07.projet3demo.services.TerritoireService;
import fr.isika.cdi07.projet3demo.services.TypeProjetService;
import fr.isika.cdi07.projet3demo.services.UtilisateurService;

@Controller
public class ProjetController {

	private static final Logger LOGGER = Logger.getLogger(ProjetController.class.getSimpleName());

	@Autowired
	private ProjetService projetService;

	@Autowired
	private TerritoireService territoireService;

	@Autowired
	private TypeProjetService typeProjetService;

	@Autowired
	private CategorieService categorieService;

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PorteurProjetService porteurProjetService;

	@Autowired
	private PortefeuilleService portefeuilleService;

	@Autowired
	private DocumentService documentService;

	@Autowired 
	private HistoriqueService histoService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageRecuService messageRecuService;

	@Autowired
	private FavoriService favoriService;


	//SHOW ALL PROJECT
	@GetMapping("/ShowAllProjetList")
	public String showAllProjetList(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "ErrorSite";
		}
		// Role >>>> ADMIN?
		Optional<Role> role = roleService.testIsAdmin(user);
		if(!role.isPresent())
			return "noFoundListProjet";

		ListProjetForm lpf = new ListProjetForm();
		lpf.setCritere("all");
		lpf.setProjets(projetService.afficherAllProjet());

		model.addAttribute("listCritere", lpf);

		return "listProjet";
	}

	@PostMapping("/selectionProjet")
	public String selectionProjet(@ModelAttribute("listCritere") ListProjetForm lpf, Model model) {

		List<Projet> listeProjets = projetService.listProjetInfo(lpf.getCritere());
		// LOGGER.info("taille liste" + listeProjets.size());

		ListProjetForm monLpf = new ListProjetForm();


		if(!listeProjets.isEmpty()) {
			monLpf.setProjets(listeProjets);
			monLpf.setCritere(lpf.getCritere());
			model.addAttribute("listCritere", monLpf );		
			return "listProjet";
		}
		return "redirect:/ShowAllProjetList";
	}

	@GetMapping("/selectionProjet")
	public String redirectListProjet() {


		return "redirect:/ShowAllProjetList";
	}

	//SHOW PROJECT WITH STATUS "PUBLIE"
	@GetMapping("/ShowProjetListPublie")
	public String showProjetListPublie(Model model, HttpSession session) {

		String emailUserConnecte = (String) session.getAttribute(UtilisateurController.EMAIL_UTILISATEUR_CONNECTE);

		List<Projet> allProjetPublie = projetService.afficherProjetPublie();
		
		Instant start = Instant.now();
    



		// LOGGER.info("Construction catalogue Debut : " + Instant.now());

		//créer une form avec projet et document pour chopper les infos des images :)

		List<ProjetDocumentForm> pdf = new ArrayList<ProjetDocumentForm>();

		for(Projet monProjet : allProjetPublie) {
			ProjetDocumentForm monProjetform = new ProjetDocumentForm();
			monProjetform.setProjet(monProjet);

			//			allProjetPublie.forEach(projet -> projet.setFavori(favoriService.estFavori(projet.getIdProjet(), emailUserConnecte)));
			monProjet.setFavori(false);
			if(favoriService.estFavori(monProjet.getIdProjet(), emailUserConnecte)) {
				monProjet.setFavori(true);
			}


			Optional<Document> monDoc = Optional.empty();
			monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_PRINCIPALE);
//			if (!monDoc.isPresent()) {
//				monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_SECONDE);
//				if(!monDoc.isPresent()) {
//					monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_TROISIEME);
//				}
//			}
			if(!monDoc.isPresent()) {
				monProjetform.setIdImage(-1L);
			} else {
				monProjetform.setIdImage(monDoc.get().getIdDocument());
			}
			pdf.add(monProjetform);
		}
		
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();

		// LOGGER.info("Construction catalogue fin : " + Instant.now() + " / temps : " + timeElapsed);
		model.addAttribute("listProjetPublie", pdf);
		return "catalogue";
	}

	// SHOW FORM TO CREATE PROJECT
	@GetMapping("/ShowNewProjetForm")
	public String showNewProjetForm(Model model, HttpSession session) {

		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "ErrorSite";
		}

		Projet projet = new Projet();
		ProjetForm monForm = new ProjetForm();
		PorteurProjet porteurProjet = new PorteurProjet();
		monForm.setTypePorteur("0");

		Role roleOfUser = roleService.hasRoleNoSave(user, TypeRole.PORTEURPROJET);
		if(roleOfUser.getIdRole() != null) {
			porteurProjet = porteurProjetService.isPresent(roleOfUser);
			if(porteurProjet.getTypePorteur() != null) {
				if(porteurProjet.getTypePorteur().equals(TypePorteur.PRIVE)) {
					monForm.setTypePorteur("1");
				}else if(porteurProjet.getTypePorteur().equals(TypePorteur.HOPITAL)) {
					monForm.setTypePorteur("2");
				} else if(porteurProjet.getTypePorteur().equals(TypePorteur.ASSOCIATION)) {
					monForm.setTypePorteur("3");
				} else if(porteurProjet.getTypePorteur().equals(TypePorteur.ENTREPRISE)) {
					monForm.setTypePorteur("4");
				}
			}
		}


		//		LOGGER.info("role dans le get :" + roleOfUser2);
		monForm.setUtilisateur(user);
		//		monForm.setRole(roleOfUser2);
		monForm.setRole(null);
		monForm.setPorteurProjet(porteurProjet);
		monForm.setProjet(projet);

		// LOGGER.info("monForm" + monForm);

		Territoire territoire = new Territoire();
		TypeProjet typeProjet = new TypeProjet();
		Categorie categorie = new Categorie();
		//		List<TypePorteur> listTypePorteurProjet = new ArrayList<TypePorteur>();
		//		listTypePorteurProjet = porteurProjetService.afficherAllTypePorteurProjet();

		List<Territoire> listeTriee = territoireService.afficherAllTerritoire();
		listeTriee.sort((t1,t2) -> t1.getLibelle().compareTo(t2.getLibelle()));
		
		model.addAttribute("monForm", monForm);
		model.addAttribute("categorie", categorie);
		model.addAttribute("typeProjet", typeProjet);
		model.addAttribute("territoire", territoire);
		model.addAttribute("listTerritoire", listeTriee);
		model.addAttribute("listTypeProjet", typeProjetService.afficherAllTypeProjet());
		//		model.addAttribute("listTPP", listTypePorteurProjet);

		return "newProjet";
	}


	//WRITE IN DB PROJECT WITH COMPONENT (CATEGORIE, TERRITOIRE, TYPEPROJET, PP, PORTEFEUILLE, ROLE)
	@PostMapping("/saveProjet")
	public String saveProjet(@ModelAttribute("monForm") ProjetForm monForm, @ModelAttribute("typeProjet") TypeProjet typeProjet, 
			@ModelAttribute("territoire") Territoire territoire, HttpSession session) {
		// LOGGER.info("TYPE PORTEUR " + monForm.getPorteurProjet().getTypePorteur());
		// LOGGER.info("Selected data : idTypeProjet : " + typeProjet.getIdTypeProjet() + " idTerritoire " + territoire.getIdTerritoire());
		// LOGGER.info("roleee post" + monForm.getRole());
		// LOGGER.info("monForm POST " + monForm);

		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "ErrorSite";
		}
		
		monForm.setRole(roleService.hasRole(user, TypeRole.PORTEURPROJET)); 
		// LOGGER.info("role POST ?" + monForm.getRole());

		monForm.getPorteurProjet().setRole(monForm.getRole());

		switch (monForm.getTypePorteur()) {
		case "1":
			monForm.getPorteurProjet().setTypePorteur(TypePorteur.PRIVE);
			break;
		case "2":
			monForm.getPorteurProjet().setTypePorteur(TypePorteur.HOPITAL);			
			break;

		case "3":
			monForm.getPorteurProjet().setTypePorteur(TypePorteur.ASSOCIATION);			
			break;

		case "4":
			monForm.getPorteurProjet().setTypePorteur(TypePorteur.ENTREPRISE);			
			break;
		default:
			session.setAttribute("erreurSelection", "vous n'avez pas selectionné");
			return "redirect:/ShowNewProjetForm/";
		}
		// LOGGER.info("porteurProjet dans le post nouvelle version" + monForm.getPorteurProjet());
		PorteurProjet monPorteur = porteurProjetService.ajoutPorteur(monForm.getPorteurProjet());


		if(typeProjet.getIdTypeProjet().equals(0L) || territoire.getIdTerritoire().equals(0L)) {
			return "redirect:/ShowNewProjetForm";
		}

		Categorie categorieToUse = null;
		Optional<Categorie> categorie = categorieService.getCategorieByTerritoireAndType(territoire, typeProjet);
		if(categorie.isPresent()) {
			categorieToUse = categorie.get();
		} else {
			Categorie withTypeProjet = new Categorie().withTerritoire(territoire).withTypeProjet(typeProjet);
			categorieToUse = categorieService.ajoutCategorie(withTypeProjet);
		}


		monForm.getProjet().setCategorie(categorieToUse);




		PortefeuilleProjet portefeuille = portefeuilleService.isPresent(monPorteur, "Defaut");
		portefeuilleService.ajoutPortefeuille(portefeuille);

		PortefeuilleProjet addPortefeuille =  portefeuilleService.ajoutPortefeuille(portefeuille);

		monForm.getProjet().setPortefeuilleprojet(addPortefeuille);
		projetService.ajoutProjet(monForm.getProjet());

		// enregistrement dans l'historique
		Historique monHisto = new Historique();
		monHisto.setActeur(user);
		monHisto.setDateHeure(monForm.getProjet().getDateMaj());
		monHisto.setEtatProjet(StatutProjet.CREE);
		monHisto.setEvenement("Création Projet par utilisateur");
		monHisto.setProjet(monForm.getProjet());
		monHisto.setLibelle("Création Projet par utilisateur");
		histoService.ajout(monHisto);

		return "redirect:/NewPictureForm/"+monForm.getProjet().getIdProjet();
	}

	//SHOW FORM TO UPDATE PROJECT
	@GetMapping("/showUpdateForm/{id}")
	public String showUpdateProjetForm(@PathVariable (value = "id") Long id, Model model, HttpSession session) {

		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "ErrorSite";
		}

		Projet projet = projetService.getProjetByIdNoOptional(id);
		
		Role role = projet.getPortefeuilleprojet().getPorteurprojet().getRole();
		if(role.getIdRole() == null)
			return "error";

		ProjetForm monForm = new ProjetForm();

		PorteurProjet porteurProjet = porteurProjetService.isPresent(role);

		// LOGGER.info("*************PORTEUR PROJET : " + porteurProjet);
		monForm.setRole(role);
		monForm.setPorteurProjet(porteurProjet);
		monForm.setProjet(projet);

		Territoire territoire = projet.getCategorie().getTerritoire();
		TypeProjet typeProjet = projet.getCategorie().getTypeProjet();

		monForm.setTerritoire(territoire);
		monForm.setTypeProjet(typeProjet);

		model.addAttribute("monForm", monForm);

		model.addAttribute("listTerritoire", territoireService.afficherAllTerritoire());
		model.addAttribute("listTypeProjet", typeProjetService.afficherAllTypeProjet());

		return "updateProjet";
	}


	@PostMapping("/updateProjet")
	public String updateProjet(@ModelAttribute("monForm") ProjetForm monForm, HttpSession session) {

		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "error";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "Error";
		}

		// LOGGER.info("Selected data : idTypeProjet : " + monForm.getTypeProjet().getIdTypeProjet() + " idTerritoire " + monForm.getTerritoire().getIdTerritoire());

		if(monForm.getTypeProjet().getIdTypeProjet().equals(0L) || monForm.getTerritoire().getIdTerritoire().equals(0L)) {
			return "redirect:/ShowNewProjetForm";
		}

		Categorie categorieToUse = null;
		Optional<Categorie> categorie = categorieService.getCategorieByTerritoireAndType(monForm.getTerritoire(), monForm.getTypeProjet());
		if(categorie.isPresent()) {
			categorieToUse = categorie.get();
		} else {
			Categorie withTypeProjet = new Categorie().withTerritoire(monForm.getTerritoire()).withTypeProjet(monForm.getTypeProjet());
			categorieToUse = categorieService.ajoutCategorie(withTypeProjet);
		}
		monForm.getProjet().setCategorie(categorieToUse);

		// LOGGER.info("PORTEURprojet POSTTTT : " + monForm.getPorteurProjet());
		PorteurProjet monPorteur = porteurProjetService.ajoutPorteur(monForm.getPorteurProjet());

		// LOGGER.info("PORTEFEUILLE ************************* POSTTTT : "+monPorteur);


		PortefeuilleProjet portefeuille = portefeuilleService.isPresent(monPorteur, "defaut");
		PortefeuilleProjet addPortefeuille =  portefeuilleService.ajoutPortefeuille(portefeuille);

		monForm.getProjet().setPortefeuilleprojet(addPortefeuille);
		monForm.getProjet().setDateMaj(Date.from(Instant.now()));
		projetService.saveProjet(monForm.getProjet());

		// enregistrement dans l'historique
		Historique monHisto = new Historique();
		monHisto.setActeur(user);
		monHisto.setDateHeure(monForm.getProjet().getDateMaj());
		monHisto.setEtatProjet(monForm.getProjet().getStatutDuProjet());
		monHisto.setEvenement("Modification Projet");
		monHisto.setProjet(monForm.getProjet());
		monHisto.setLibelle("Modification projet");
		histoService.ajout(monHisto);

		return "redirect:/NewPictureForm/"+monForm.getProjet().getIdProjet();
	}


	// SHOW LIST PROJECT BY USER PP
	@GetMapping("/showListProjetByUser")
	public String showListProjetByUser(Model model, HttpSession session) {

		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "Error";
		}

		// Role >>>> PorteurProjet?
		Optional<Role> role = roleService.testIsPorteurProjet(user);
		if(!role.isPresent())
			return "redirect:/";

		//OUI >>>> chercher les portefeuilles lies au role
		List<Projet> projetRecup = projetService.getListProjet(role.get());
		//		List<Projet> projetActifForUser = new ArrayList<Projet>();
		List<ProjetDocumentForm> pdf = new ArrayList<ProjetDocumentForm>();

		for(Projet monProjet : projetRecup) {
			if(monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
				continue;
			}
			//			projetActifForUser.add(projet);

			ProjetDocumentForm monProjetform = new ProjetDocumentForm();
			monProjetform.setProjet(monProjet);

			//				allProjetPublie.forEach(projet -> projet.setFavori(favoriService.estFavori(projet.getIdProjet(), emailUserConnecte)));
			monProjet.setFavori(false);
			if(favoriService.estFavori(monProjet.getIdProjet(), userEmail)) {
				monProjet.setFavori(true);
			}


			Optional<Document> monDoc = Optional.empty();
			monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_PRINCIPALE);
//			if (!monDoc.isPresent()) {
//				monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_SECONDE);
//				if(!monDoc.isPresent()) {
//					monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_TROISIEME);
//				}
//			}
			if(!monDoc.isPresent()) {
				monProjetform.setIdImage(-1L);
			} else {
				// LOGGER.info("image : " + monDoc.get());
				monProjetform.setIdImage(monDoc.get().getIdDocument());
			}
			pdf.add(monProjetform);
		}

		model.addAttribute("listProjetForUser", pdf);

		return "listProjetByUser";
	}

	//Recherche 
	//Form pour une recherche simple
	@GetMapping("/showSearchBox")
	public String saisirRechercheProjetParTitre(Model model) {
		RechercheParTitreForm rechercheParTitreForm = new RechercheParTitreForm();
		model.addAttribute("rechercheParTitreForm", rechercheParTitreForm);
		return "recherche_titre";
	}
	//Recherche par titre
	@PostMapping("/rechercherProjetParTitre")
	public String rechercherProjetParTitre(@ModelAttribute("rechercheParTitreForm") RechercheParTitreForm rechercheParTitreForm,
			Model model) {
		List<Projet> listeProjets = projetService.rechercherProjetParTitre(rechercheParTitreForm.getTitre());
		if(!listeProjets.isEmpty()) {
			model.addAttribute("listeProjetTrouveParTitre", listeProjets);		
			return "listeProjets_recherche";
		}
		return "redirect:/showSearchBox";
	}
	//Form pour une recherche multicritères
	@GetMapping("/showSearchBoxMulticriteres")
	public String saisirRechercheProjetMulticriteres(Model model) {
		RechercheMulticriteresForm rechercheMultiForm = new RechercheMulticriteresForm();

		model.addAttribute("listTerritoire", territoireService.afficherAllTerritoire());
		System.out.println("Liste territoire : " + territoireService.afficherAllTerritoire());
		model.addAttribute("listTypeProjet", typeProjetService.afficherAllTypeProjet());
		model.addAttribute("rechercheMultiForm", rechercheMultiForm);
		return "recherche_multicriteres";
	}

	//Recherche multicritères
	@PostMapping("/rechercherProjetMulticriteres")
	public String rechercherProjetMulticriteres(@ModelAttribute("rechercheMultiForm") RechercheMulticriteresForm rechercheMultiForm,
			Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		List<Projet> listeProjetsmulti = projetService.rechercherProjetParCriteres(rechercheMultiForm.getTitre(),
				rechercheMultiForm.getTypeProjet().getIdTypeProjet(), rechercheMultiForm.getTerritoire().getIdTerritoire(),
				rechercheMultiForm.isDonMateriel(), rechercheMultiForm.isDonTemps());

		if(!listeProjetsmulti.isEmpty()) {
			model.addAttribute("listeProjetsRechercheMulticriteres", listeProjetsmulti);

			List<ProjetDocumentForm> pdf = new ArrayList<ProjetDocumentForm>();
			for(Projet monProjet : listeProjetsmulti) {
				if(!monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE)) {
					continue;
				}
				ProjetDocumentForm monProjetform = new ProjetDocumentForm();
				monProjetform.setProjet(monProjet);
				//			allProjetPublie.forEach(projet -> projet.setFavori(favoriService.estFavori(projet.getIdProjet(), emailUserConnecte)));
				monProjet.setFavori(false);
				if(favoriService.estFavori(monProjet.getIdProjet(), userEmail)) {
					monProjet.setFavori(true);
				}
				Optional<Document> monDoc = Optional.empty();
				monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_PRINCIPALE);
//				if (!monDoc.isPresent()) {
//					monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_SECONDE);
//					if(!monDoc.isPresent()) {
//						monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_TROISIEME);
//					}
//				}
				if(!monDoc.isPresent()) {
					monProjetform.setIdImage(-1L);
				} else {
					// LOGGER.info("image : " + monDoc.get());
					monProjetform.setIdImage(monDoc.get().getIdDocument());
				}
				pdf.add(monProjetform);
			}
			model.addAttribute("listProjetPublie", pdf);
			return "listeProjets_rechercheMulti";
		}
		return "redirect:/showSearchBoxMulticriteres";
	}


	//Pierre ok

	@GetMapping("/showProjet/{id}/{act}")
	public String showProjet(@PathVariable(value = "id") Long id,@PathVariable(value = "act") String act, Model model,HttpSession session) {


		Optional<Projet> projet = projetService.getProjetById(id);

		if (!projet.isPresent())
			return "error";

		Projet monProjet = projet.get();

		Utilisateur user = null;


		if(!act.equalsIgnoreCase("DON")) {
			String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
			if (userEmail == null) {
				return "redirect:/showConnexionForm";
			}

			user = utilisateurService.chercherUtilisateurParEmail(userEmail);
			if (user == null) {
				return "Error";
			} else {

			}
		}
		switch (act) {
		case "ADMU" :
			if (!monProjet.getPortefeuilleprojet().getPorteurprojet().getRole().getUtilisateur().equals(user)) {
				return "Error";
			}
			if (monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
				return "Error";
			}
			break;
		case "ADMF" : 
			if (!roleService.chkRole(user, TypeRole.ADMINISTRATEUR))
				return "Error";
			break;
		case "DON" : if (!monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE))
			return "Error";
		break;
		default : return "Error";
		}


		PresentationProjetForm maPresProj = new PresentationProjetForm();
		maPresProj.setProjet(monProjet);

		Optional<Document> monDoc = null;
		monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_PRINCIPALE);
		if (monDoc.isPresent())
			maPresProj.setImage1(monDoc.get().getIdDocument());
		else
			maPresProj.setImage1(-1L);

		monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_SECONDE);
		if (monDoc.isPresent())
			maPresProj.setImage2(monDoc.get().getIdDocument());
		else
			maPresProj.setImage2(-1L);

		monDoc = documentService.findbyProjetAndLibelle(monProjet,TypeLibelleDoc.IMAGE_TROISIEME);
		if (monDoc.isPresent())
			maPresProj.setImage3(monDoc.get().getIdDocument());
		else
			maPresProj.setImage3(-1L);

		// detemrination action par rapport au status et profil accés
		String chxSel = "";
		if (act.equalsIgnoreCase("ADMU")) {
			if (monProjet.getStatutDuProjet().equals(StatutProjet.CREE) ||
					monProjet.getStatutDuProjet().equals(StatutProjet.REJETE)) {
				chxSel = "USRMAN";
			}
			else if  (monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE)) {
				chxSel = "USRDEL";
			}
			else if  (monProjet.getStatutDuProjet().equals(StatutProjet.EN_ATTENTE)) {
				chxSel = "USRCAN";
			}
			else {
				chxSel = "USRMOD";
			}
		}
		else if (act.equalsIgnoreCase("ADMF")) {
			if (monProjet.getStatutDuProjet().equals(StatutProjet.EN_ATTENTE)) {
				chxSel = "ADMMAN";
			}
			else if (monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
				chxSel = "ADMRES";
			}
			else {
				chxSel = "ADMDEL";
			}

		}
		else if (act.equalsIgnoreCase("DON")) {
			if (monProjet.getStatutDuProjet().equals(StatutProjet.PUBLIE)) {
				chxSel = "DONDON";
			}

		}

		maPresProj.setChoixUser(chxSel);
		maPresProj.setAction(act);

		model.addAttribute("infoproj", maPresProj);

		return "vueProjet";
	}

	@RequestMapping("/ModifierStatProjet/{id}/{stat}/{orig}")
	public String modifStatProj(@PathVariable(value = "id") Long id,@PathVariable(value = "stat") String stat,
			@PathVariable(value = "orig") String orig, Model model,HttpSession session) {
		String retUrl = "redirect:/ShowAllProjetList";;

		if (orig.equalsIgnoreCase("ADMU"))
			retUrl = "redirect:/showListProjetByUser";

		// LOGGER.info("Pojet id="+id +" / NewStatus=" + stat + "/ Orig :" + orig);

		Optional<Projet> projet = projetService.getProjetById(id);

		if (!projet.isPresent())
			return retUrl;

		Projet monProjet = projet.get();

		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return retUrl;
		}

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
			utilisateurService.ajouterUtilisateur(userAdm);
		}

		MessageInterne msg = new MessageInterne();
		Date datEvent = Date.from(Instant.now());
		msg.setDate(datEvent);
		Utilisateur userDest = null;

		String libEvent = "";
		StatutProjet monStatut = null;

		String saveStat = monProjet.getStatutDuProjet().toString();

		switch (stat) {
		case "ASKAPPR" : monStatut = StatutProjet.EN_ATTENTE;
		libEvent="Demande d'approbation par porteur projet";
		msg.setTitre("le projet '" + monProjet.getTitre() + "' est en attente d'approbation");
		msg.setContenu("le projet '" + monProjet.getTitre() + "' est en attente d'approbation");
		msg.setEmetteur(user);
		userDest = userAdm;
		break;

		case "APPROVE" : monStatut = StatutProjet.APPROUVE;
		libEvent="Demande d'approbation par porteur projet";
		msg.setTitre("le projet '" + monProjet.getTitre() + "' a été approuvé");
		msg.setContenu("le projet '" + monProjet.getTitre() + "' a été approuvé par un administrateur");
		msg.setEmetteur(user);
		userDest = monProjet.getPortefeuilleprojet().getPorteurprojet().getRole().getUtilisateur();
		libEvent="Approbation projet par un administrateur";
		break;

		case "PUBLIER" : monStatut = StatutProjet.PUBLIE;
		msg.setTitre("le projet '" + monProjet.getTitre() + "' a été publié");
		msg.setContenu("le projet '" + monProjet.getTitre() + "' a été publié par un administrateur");
		msg.setEmetteur(user);
		userDest = monProjet.getPortefeuilleprojet().getPorteurprojet().getRole().getUtilisateur();
		libEvent="Publication du projet par un Administrateur";
		break;

		case "SUPPRIMER" : monStatut = StatutProjet.SUPPRIME;
		libEvent="Suppression par le porteur projet";
		if (orig.equalsIgnoreCase("ADMF")) {
			libEvent="Suppression par un administrateur";
			msg.setTitre("le projet '" + monProjet.getTitre() + "' a été supprimé");
			msg.setContenu("le projet '" + monProjet.getTitre() + "' a été supprimé par un administrateur");
			msg.setEmetteur(user);
			userDest = monProjet.getPortefeuilleprojet().getPorteurprojet().getRole().getUtilisateur();
		} else if (orig.equalsIgnoreCase("ADMU")) {
			libEvent="Suppression par le porteur de projet";
			msg.setTitre("le projet '" + monProjet.getTitre() + "' a été supprimé");
			msg.setContenu("le projet '" + monProjet.getTitre() + "' a été supprimé le porteur de projet");
			msg.setEmetteur(user);
			userDest = userAdm;
		}
		break;

		case "REJETER" : monStatut = StatutProjet.REJETE;
		libEvent="Rejet du projet par un Administrateur";
		msg.setTitre("le projet '" + monProjet.getTitre() + "' a été rejeté");
		msg.setContenu("le projet '" + monProjet.getTitre() + "' a été rejeté par un administrateur");
		msg.setEmetteur(user);
		userDest = monProjet.getPortefeuilleprojet().getPorteurprojet().getRole().getUtilisateur();
		break;

		case "RESTAURER" :
			if (monProjet.getStatutDuProjet().equals(StatutProjet.SUPPRIME)) {
				monStatut = StatutProjet.CREE;
				libEvent="Restauration (suite suppression) du projet par un Administrateur";
				msg.setTitre("le projet '" + monProjet.getTitre() + "' a été restauré");
				msg.setContenu("le projet '" + monProjet.getTitre() + 
						"' a été restauré (suite suppresion) par un administrateur");
				msg.setEmetteur(user);
				userDest = monProjet.getPortefeuilleprojet().getPorteurprojet().getRole().getUtilisateur();
			} else {
				return retUrl;
			}
			break;

		case "CANASKAPPR" : 			
			if (monProjet.getStatutDuProjet().equals(StatutProjet.EN_ATTENTE)) {
				monStatut = StatutProjet.CREE;
				libEvent="Annulation demande d'approbation par porteur de projet";
				msg.setTitre("Demande d'approbation du projet '" + monProjet.getTitre() + "' annulée");
				msg.setContenu("La emande d'approbation du projet '" + monProjet.getTitre() + "' a été annulée");
				msg.setEmetteur(user);
				userDest = userAdm;
			} else {
				return retUrl;
			}
			break;

		default : return retUrl;
		}

		// modification du projet
		monProjet.setStatutDuProjet(monStatut);
		projetService.saveProjet(monProjet);


		// enregistrement dans l'historique
		Historique monHisto = new Historique();
		monHisto.setActeur(user);
		monHisto.setDateHeure(datEvent);
		monHisto.setEtatProjet(monStatut);
		monHisto.setEvenement("Modification état de '" + saveStat +  "' à '" + monStatut.toString() +
				"', via l'evenement " + stat);
		monHisto.setProjet(monProjet);
		monHisto.setLibelle(libEvent);
		histoService.ajout(monHisto);

		// envoi de l'email, si besoin
		if (userDest != null ) {
			MessageInterne msgNew = messageService.ajout(msg);
			MessageRecu msgRecu = new MessageRecu();
			msgRecu.setDateHeure(datEvent);
			msgRecu.setisRead(false);
			msgRecu.setMessageInterne(msgNew);
			msgRecu.setUtilisateur(userDest);
			messageRecuService.ajout(msgRecu);
		}

		return retUrl;
	}


	@GetMapping("/gererFavori/{idProjet}")
	public String gererFavori(HttpSession session, @PathVariable (value = "idProjet") Long idProjet) {
		String emailUserConnecte = (String) session.getAttribute(UtilisateurController.EMAIL_UTILISATEUR_CONNECTE);
		if(emailUserConnecte == null) {
			return "redirect:/showConnexionForm";
		}
		Utilisateur utilisateur = utilisateurService.chercherUtilisateurParEmail(emailUserConnecte);

		Optional<Projet> projet = projetService.getProjetById(idProjet);		
		Favori favori = new Favori();

		if(!favoriService.estFavori(idProjet, emailUserConnecte)) {
			favori.setProjet(projet.get());
			favori.setUtilisateur(utilisateur);
			favoriService.ajouterFavori(favori);			
			return "redirect:/ShowProjetListPublie";	
		} else {
			Optional<Favori> favoriAsupprimer = favoriService.chercherFavoriParIdprojetEtEmailUtilisateur(idProjet, emailUserConnecte);
			if(favoriAsupprimer.isPresent()) {
				favoriService.retirerFavori(favoriAsupprimer.get());
			}
			return "redirect:/ShowProjetListPublie";
		}		
	}


		@GetMapping("/showProjetHisto/{id}/{act}")
		public String showProjetHisto(@PathVariable(value = "id") Long id,@PathVariable(value = "act") String act, Model model,HttpSession session) {


			Optional<Projet> projet = projetService.getProjetById(id);

			if (!projet.isPresent())
				return "error";

			Projet monProjet = projet.get();
			
			List<Historique> histoListe = histoService.afficherHistoriqueByProjet(monProjet);
			// LOGGER.info("Taille liste histo : " + histoListe.size());
			
			model.addAttribute("histoListe", histoListe);
			model.addAttribute("action", act);
			model.addAttribute("idProj", id);
			
			return "listHistoProjet";

		}


}

