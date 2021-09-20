//package fr.isika.cdi07.projet3demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import fr.isika.cdi07.projet3demo.model.DonMonetaire;
//import fr.isika.cdi07.projet3demo.services.DonMonetaireService;
//
//@Controller
//public class TestController {
//	
//	@Autowired 
//	private DonMonetaireService donService;
//
//	@GetMapping("/afficherListeDesDon")
//	public String afficherListeDesDon(Model model) {
//		model.addAttribute("count", donService.compterDons());
//		model.addAttribute("donMonetaireList", donService.afficherDons());
//		return "listeDons";
//	}
//	
//	@GetMapping("/ajouterNouveauDon")
//	public String ajouterNouveauDon(Model model) {
//		DonMonetaire nouveauDon = new DonMonetaire();
//		model.addAttribute("donAAjouterOuModifier", nouveauDon);
//		return "nouveau_don";
//	}
//	
//	@PostMapping("/sauvegarderDon")
//	public String sauvegarderDon(@ModelAttribute("donAAjouterOuModifier") DonMonetaire don) {
//		//donService.enregistrerDansLaBase(don);
//		return "redirect:/afficherListeDesDon";
//	}
//	
//	@GetMapping("/modifierDon/{id}")
//	public String modifierDon(@PathVariable(value="id") long id, Model model) {
//		DonMonetaire don = donService.obtenirDonById(id);
//		model.addAttribute("donAAjouterOuModifier", don);
//		return "modifier_don";
//	}
//	
//	@GetMapping("/supprimerDon/{id}")
//	public String supprimerDon(@PathVariable(value="id") long id) {
//		donService.supprimerDonById(id);
//		return "redirect:/afficherListeDesDon";
//	}
//}
