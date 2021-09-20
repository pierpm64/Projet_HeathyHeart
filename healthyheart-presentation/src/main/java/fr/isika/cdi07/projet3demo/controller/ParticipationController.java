package fr.isika.cdi07.projet3demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.isika.cdi07.projet3demo.model.DonMateriel;
import fr.isika.cdi07.projet3demo.model.DonMonetaire;
import fr.isika.cdi07.projet3demo.model.DonTemps;
import fr.isika.cdi07.projet3demo.model.ParticipationProjet;
import fr.isika.cdi07.projet3demo.model.StatutDon;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.services.DonMaterielService;
import fr.isika.cdi07.projet3demo.services.DonMonetaireService;
import fr.isika.cdi07.projet3demo.services.DonTempsService;
import fr.isika.cdi07.projet3demo.services.IParticipationProjetService;
import fr.isika.cdi07.projet3demo.services.UtilisateurService;

@Controller
public class ParticipationController {
	
	@Autowired
	private IParticipationProjetService participationService;
	
	@Autowired 
	private DonMaterielService donMaterielService;
	
	@Autowired
	private DonMonetaireService donMonetaireService;
	
	@Autowired
	private DonTempsService donTempsService;
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@GetMapping("/showAllEnAttenteDon")
	public String showAllEnAttenteDon(Model model) {
		List<DonMonetaire> listDonMonetaire = donMonetaireService.getListDonsByStatut(StatutDon.EN_ATTENTE);
		List<DonMateriel> listDonMateriel = donMaterielService.getListDonsByStatut(StatutDon.EN_ATTENTE);
		List<DonTemps> listDonTemps = donTempsService.getListDonsByStatut(StatutDon.EN_ATTENTE);
		
		model.addAttribute("monetairesList", listDonMonetaire);
		model.addAttribute("materielList", listDonMateriel);
		model.addAttribute("tempsList", listDonTemps);
		
		return "listeDonsEnAttente";
	}
	
	@GetMapping("/validateDonMonetaire")
	public String validateDonMonetaire(@RequestParam long id) {
		return validateOrRejectDon(id, StatutDon.APPROUVE);
	}
	
	@GetMapping("/rejectDonMonetaire")
	public String rejectDonMonetaire(@RequestParam long id) {
		return validateOrRejectDon(id, StatutDon.REJETE);
	}
	
	@GetMapping("/validateDonMateriel")
	public String validateDonMateriel(@RequestParam long id) {
		return validateOrRejectDon(id, StatutDon.APPROUVE);
	}
	
	@GetMapping("/rejectDonMateriel")
	public String rejectDonMateriel(@RequestParam long id) {
		return validateOrRejectDon(id, StatutDon.REJETE);
	}
	
	@GetMapping("/validateDonTemps")
	public String validateDonTemps(@RequestParam long id) {
		return validateOrRejectDon(id, StatutDon.APPROUVE);
	}
	
	@GetMapping("/rejectDonTemps")
	public String rejectDonTemps(@RequestParam long id) {
		return validateOrRejectDon(id, StatutDon.REJETE);
	}

	private String validateOrRejectDon(long id, StatutDon statutDon) {
		Optional<DonMonetaire> optDon = donMonetaireService.obtenirDonById(id);
		if(!optDon.isPresent())
			return "/";
		participationService.updateStatutDon(optDon.get().getParticipationProjet(), statutDon);
		
		return "redirect:/showAllEnAttenteDon";
	}

	@GetMapping("/showMyContributions")
	public String showMyContributions(Model model, HttpSession session) {
		String userEmail = (String)session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}
		Utilisateur user = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (user == null) {
			return "ErrorSite";
		}
		
		List<ParticipationProjet> participationsList = participationService.getAllApprovedParticipationFromUser(user);
		
		model.addAttribute("participations", participationsList);
		
		return "mes_contributions";
	}
	
}
