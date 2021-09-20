package fr.isika.cdi07.projet3demo.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.isika.cdi07.projet3demo.model.MessageInterne;
import fr.isika.cdi07.projet3demo.model.MessageRecu;
import fr.isika.cdi07.projet3demo.model.TypeRole;
import fr.isika.cdi07.projet3demo.model.Utilisateur;
import fr.isika.cdi07.projet3demo.modelform.LstMessageForm;
import fr.isika.cdi07.projet3demo.modelform.MessagerieForm;
import fr.isika.cdi07.projet3demo.services.MessageRecuService;
import fr.isika.cdi07.projet3demo.services.MessageService;
import fr.isika.cdi07.projet3demo.services.RoleService;
import fr.isika.cdi07.projet3demo.services.UtilisateurService;

@Controller
public class MessagerieController {

	private static final Logger LOGGER = Logger.getLogger(MessagerieController.class.getSimpleName());

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageRecuService messageRecuService;

	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	private RoleService roleService;



	@GetMapping("/afficherListeUserMessages")
	public String afficherListeUserMessage(Model model, HttpSession session) {
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (tstUser == null) {
			return "Error";
		}
		

		
		
		List<MessageRecu> userMessagesRef = messageRecuService.afficherUserMessagesJPA(tstUser);
				
		// si admin, rajou email à ADMIN
		if (roleService.chkRole(tstUser, TypeRole.ADMINISTRATEUR)) {
				List<MessageRecu> userMessagesAdm = messageRecuService.afficherUserMessagesJPA(chkADM());
				// LOGGER.info("User ="+ chkADM() + "/ taille lstMsg :"+ userMessagesAdm.size());
				if (userMessagesAdm.size() > 0) {
					userMessagesRef.addAll(userMessagesAdm);
				}	
		}
				
		List<MessageRecu> userMessagesJPA = userMessagesRef.stream().
				sorted(Comparator.comparingLong(MessageRecu::getIdMessageRecu).reversed()).collect(Collectors.toList());

		
		
		LstMessageForm lstMessageForm = new LstMessageForm();
		lstMessageForm.setLstMsgRecu(userMessagesJPA);
		lstMessageForm.setCritere("0");
		lstMessageForm.setEtatMsg("tout");
		
		model.addAttribute("count", userMessagesJPA.size());
		model.addAttribute("messagesform", lstMessageForm);
		return "listeUserMessages";
	}

	@GetMapping("/selectionStatMsg")
	public String retourLstMsg() {
		return "redirect:/afficherListeUserMessages";
	}
	
	@GetMapping("/afficherListeMessages")
	public String afficherListeMessage(Model model, HttpSession session) {
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (tstUser == null) {
			return "Error";
		}

		if (!roleService.chkRole(tstUser, TypeRole.ADMINISTRATEUR)) {
			return "Error";
		}
		
		List<MessageInterne> maListe = messageService.afficherMessages().stream().
				sorted(Comparator.comparingLong(MessageInterne::getIdMessage).reversed()).collect(Collectors.toList());
		
		model.addAttribute("count", messageService.compterMessages());
		model.addAttribute("messagesList", maListe);
		return "listeMessages";
	}

	@GetMapping("/ajouterUserMessage")
	public String ajouterNouveauUserMessage(Model model, HttpSession session) {
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (tstUser == null) {
			return "ErrorSite";
		}

		MessageInterne messageTst = new MessageInterne();
		messageTst.setEmetteur(tstUser);
		MessagerieForm nouveauMessage = new MessagerieForm();
		nouveauMessage.setMessage(messageTst);
		model.addAttribute("messagerieForm", nouveauMessage);
		return "nouveau_user_message";
	}

	@GetMapping("/ajouterMessage")
	public String ajouterNouveauMessage(Model model, HttpSession session) {
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (tstUser == null) {
			return "Error";
		}

		if (!roleService.chkRole(tstUser, TypeRole.ADMINISTRATEUR)) {
			return "Error";
		}
		
		MessagerieForm nouveauMessage = new MessagerieForm();
		model.addAttribute("messagerieForm", nouveauMessage);
		return "nouveau_message";
	}
	
	@GetMapping("/VisualierMessage/{idMsg}")
	public String VisualierMessage(@PathVariable String idMsg, Model model) {
		MessageInterne message = messageService.getMessageById(Long.valueOf(idMsg));
		List<MessageRecu> lstMessagesRecu = messageRecuService.getMessageRecuByMessage(Long.valueOf(idMsg));
		// LOGGER.info("Taille liste messages recus :" + lstMessagesRecu.size());
		//for (MessageRecu monMsg : lstMessagesRecu) {
		//	LOGGER.info("Utilisateur :" + monMsg.getUtilisateur());
		// }
		model.addAttribute("message", message);
		model.addAttribute("lstMessagesRecu", lstMessagesRecu);
		return "Visualiser_Message";
	}

	@GetMapping("/VisualierUserMessage/{idMsg}")
	public String VisualierUserMessage(@PathVariable Long idMsg, Model model) {
		MessageRecu messageRecu = messageRecuService.getMessageById(idMsg);
		MessageInterne message = messageRecu.getMessageInterne();
		model.addAttribute("message", message);
		String infoLu = "Non lu";
		if (messageRecu.isIsRead()) {
			SimpleDateFormat dateForm = new SimpleDateFormat("EEEEE dd/MM/yyyy à HH:mm:ss");
			infoLu = dateForm.format(messageRecu.getDateHeure());
		}
		model.addAttribute("infoLu", infoLu);
		model.addAttribute("idMsgRecu", idMsg);
		
		// update info lu
		if (!messageRecu.isIsRead()) {
			messageRecu.setisRead(true);
			messageRecu.setDateHeure(Date.from(Instant.now()));
			messageRecuService.ajout(messageRecu);
		}
		return "Visualiser_User_Message";
	}
	
	@GetMapping("/ReponseUserMessage/{idMsg}")
	public String ReponseUserMessage(@PathVariable Long idMsg, Model model, HttpSession session) {
		MessageInterne message = messageService.getMessageById(idMsg);
		
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (tstUser == null) {
			return "Error";
		}
		
		SimpleDateFormat dateForm = new SimpleDateFormat("EEEEE dd/MM/yyyy à HH:mm:ss");
		String dateEnvoi = dateForm.format(message.getDate());

		MessageInterne messageTst = new MessageInterne();
		messageTst.setEmetteur(tstUser);
		messageTst.setTitre("RE: " + message.getTitre());
		messageTst.setContenu("\n\n\nRéponse message ci dessous (emis le " + dateEnvoi +
								")\n-------------------\n\n " + message.getContenu());
		MessagerieForm nouveauMessage = new MessagerieForm();
		nouveauMessage.setMessage(messageTst);
		nouveauMessage.setListeDest(message.getEmetteur().getEmail());
		model.addAttribute("messagerieForm", nouveauMessage);
		return "nouveau_user_message";
	
	}
	
	@RequestMapping("/SupprimerUserMessage/{idMsg}")
	public String supprimerUserMessage(@PathVariable Long idMsg, Model model) {
		MessageRecu messageRecu = messageRecuService.getMessageById(Long.valueOf(idMsg));
		messageRecuService.delete(messageRecu);
		return "redirect:/afficherListeUserMessages";
	}
	

	// @RequestMapping(value = "/sauvegarderMessage", method = RequestMethod.POST)
	@PostMapping(value = "/sauvegarderMessage")
	public String sauvegarderMessage(@ModelAttribute("messagerieForm") MessagerieForm messagerieForm, Model model) {
		// traitement emetteur
		String emetteur = messagerieForm.getMessage().getEmetteur().getEmail();
		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(emetteur);
		if (tstUser == null) {
			String errMsg = "L'emetteur avec l'email " + emetteur + " est inconnu dans notre référentiel";
			// LOGGER.info(errMsg);
			model.addAttribute("msgerr", errMsg);
			return "nouveau_message";
		}

		// traitement des déstinataires

		String[] tabDest = messagerieForm.getListeDest().replace(',', ';').trim().split(";");

		if (tabDest.length == 0) {
			String errMsg = "Il n'y aucun destinataire saisi";
			model.addAttribute("msgerr", errMsg);
			return "nouveau_message";
		}

		HashSet<Utilisateur> lstUserDest = new HashSet<Utilisateur>();

		for (String monDest : tabDest) {
			String email = monDest.trim();
			Utilisateur tstDest = utilisateurService.chercherUtilisateurParEmail(email);
			if (tstDest == null) {
				String errMsg = "Le destinataire avec l'email " + email + " est inconnu dans notre référentiel";
				// LOGGER.info(errMsg);
				model.addAttribute("msgerr", errMsg);
				return "nouveau_message";
			}
			if (!lstUserDest.add(tstDest)) {
				String errMsg = "Le destinataire avec l'email " + email + " est en double dans les destinataire";
				// LOGGER.info(errMsg);
				model.addAttribute("msgerr", errMsg);
				return "nouveau_message";
			}
		}

		// insert message
		Date dateMsg = Date.from(Instant.now());
		messagerieForm.getMessage().setDate(dateMsg);
		messageService.ajout(messagerieForm.getMessage());

		for (Utilisateur userDest : lstUserDest) {
			MessageRecu monDest = new MessageRecu();
			monDest.setDateHeure(dateMsg);
			monDest.setMessageInterne(messagerieForm.getMessage());
			monDest.setisRead(false);
			monDest.setUtilisateur(userDest);

			messageRecuService.ajout(monDest);
		}

		return "redirect:/afficherListeMessages";
	}

	@PostMapping(value = "/sauvegarderUserMessage")
	public String sauvegarderUserMessage(@ModelAttribute("messagerieForm") MessagerieForm messagerieForm, Model model) {

		// traitement des déstinataires

		System.out.println("infos listDestinataire :" + messagerieForm.getListeDest());
		String[] tabDest = messagerieForm.getListeDest().replace(',', ';').trim().split(";");

		if (tabDest.length == 0) {
			String errMsg = "Il n'y aucun destinataire saisi";
			// LOGGER.info(errMsg);
			model.addAttribute("msgerr", errMsg);
			return "nouveau_user_message";
		}

		HashSet<Utilisateur> lstUserDest = new HashSet<Utilisateur>();

		for (String monDest : tabDest) {
			String email = monDest.trim();
			Utilisateur tstDest = utilisateurService.chercherUtilisateurParEmail(email);
			if (tstDest == null) {
				String errMsg = "Le destinataire avec l'email " + email + " est inconnu dans notre référentiel";
				// LOGGER.info(errMsg);
				model.addAttribute("msgerr", errMsg);
				return "nouveau_user_message";
			}
			if (!lstUserDest.add(tstDest)) {
				String errMsg = "Le destinataire avec l'email " + email + " est en double dans les destinataires";
				// LOGGER.info(errMsg);
				model.addAttribute("msgerr", errMsg);
				return "nouveau_user_message";
			}
		}

		// insert message
		Date dateMsg = Date.from(Instant.now());
		messagerieForm.getMessage().setDate(dateMsg);
		messageService.ajout(messagerieForm.getMessage());

		for (Utilisateur userDest : lstUserDest) {
			MessageRecu monDest = new MessageRecu();
			monDest.setDateHeure(dateMsg);
			monDest.setMessageInterne(messagerieForm.getMessage());
			monDest.setisRead(false);
			monDest.setUtilisateur(userDest);

			messageRecuService.ajout(monDest);
		}

		return "redirect:/afficherListeUserMessages";
	}
	
	@PostMapping("/selectionStatMsg")
	public String LstUserMessagebyStatus(@ModelAttribute("messageForm") LstMessageForm lstMessageForm, Model model, HttpSession session) {
		
		String critere = lstMessageForm.getCritere();
		if (critere.equalsIgnoreCase("0"))
			return "redirect:/afficherListeUserMessages";
		
		String userEmail = (String) session.getAttribute("emailUtilisateurConnecte");
		if (userEmail == null) {
			return "redirect:/showConnexionForm";
		}

		Utilisateur tstUser = utilisateurService.chercherUtilisateurParEmail(userEmail);
		if (tstUser == null) {
			return "Error";
		}
		
		String libSel = "tout";
		List<MessageRecu> userMessagesJPA = messageRecuService.afficherUserMessagesJPA(tstUser);
		
		// si admin, rajout email à ADMIN
		if (roleService.chkRole(tstUser, TypeRole.ADMINISTRATEUR)) {
				List<MessageRecu> userMessagesAdm = messageRecuService.afficherUserMessagesJPA(chkADM());
				// LOGGER.info("User ="+ chkADM() + "/ taille lstMsg :"+ userMessagesAdm.size());
				if (userMessagesAdm.size() > 0) {
					userMessagesJPA.addAll(userMessagesAdm);
				}	
		}
		
		List<MessageRecu> userMessageSel;
		switch (critere) {
			case "1" : userMessageSel = userMessagesJPA.stream().filter(m-> m.getisRead()== false).
				sorted(Comparator.comparingLong(MessageRecu::getIdMessageRecu).reversed()).
				collect(Collectors.toList());
				libSel = "non lus";
				break;
			case "2" : userMessageSel = userMessagesJPA.stream().filter(m-> m.getisRead()== true).
				sorted(Comparator.comparingLong(MessageRecu::getIdMessageRecu).reversed()).
				collect(Collectors.toList());
				libSel = "lus";
				break;
			default : return "redirect:/afficherListeUserMessages";
		}
		
		
		LstMessageForm newLstMessageForm = new LstMessageForm();
		newLstMessageForm.setLstMsgRecu(userMessageSel);
		newLstMessageForm.setCritere(critere);
		newLstMessageForm.setEtatMsg(libSel);
		
		model.addAttribute("count", userMessageSel.size());
		model.addAttribute("messagesform", newLstMessageForm);
		return "listeUserMessages";
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
