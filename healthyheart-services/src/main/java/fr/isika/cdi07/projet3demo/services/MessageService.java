package fr.isika.cdi07.projet3demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.MessageInterneRepository;
import fr.isika.cdi07.projet3demo.model.MessageInterne;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class MessageService {
	
	@Autowired
	private MessageInterneRepository messageRepo;
	
	public List<MessageInterne> afficherMessages() {
		return messageRepo.findAll();
	}
	
	public Long compterMessages() {
		return messageRepo.count();
	}
	
	public MessageInterne ajout(MessageInterne msg) {
		return messageRepo.save(msg);
	}

	public MessageInterne getMessageById(Long msgId) {
		return messageRepo.getOne(msgId);
	}

	public List<MessageInterne> afficherUserMessages(Utilisateur utilisateur) {
		List<MessageInterne> lstMsgs = messageRepo.findAll();
		List<MessageInterne> lstMsgSel = new ArrayList<MessageInterne>();
		lstMsgs.stream().filter(m -> m.getEmetteur() == utilisateur).
		forEach(m -> lstMsgSel.add(m));
		return lstMsgSel;
	}

}
