package fr.isika.cdi07.projet3demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.MessageRecuRepository;
import fr.isika.cdi07.projet3demo.model.MessageRecu;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

@Service
public class MessageRecuService {
	
	@Autowired
	private MessageRecuRepository messageRecuRepo;
	
	public List<MessageRecu> afficherMessagesRecu() {
		return messageRecuRepo.findAll();
	}
	
	public Long compterMessages() {
		return messageRecuRepo.count();
	}
	
	public MessageRecu ajout(MessageRecu msgrecu) {
		return messageRecuRepo.save(msgrecu);
	}

	public MessageRecu getMessageById(Long msgId) {
		return messageRecuRepo.getOne(msgId);
	}

	public List<MessageRecu> getMessageRecuByMessage(Long idMsg) {
		return messageRecuRepo.findAll().stream().
				filter(p-> p.getMessageInterne().getIdMessage() == idMsg).collect(Collectors.toList());
	}

	public List<MessageRecu> afficherUserMessages(Utilisateur utilisateur) {
		return messageRecuRepo.findAll().
				stream().filter(p-> p.getUtilisateur().equals(utilisateur)).collect(Collectors.toList());
	}
	
	public List<MessageRecu> afficherUserMessagesJPA(Utilisateur utilisateur) {
		return messageRecuRepo.getByUtilisateur(utilisateur);
	}
	
	public Long getNbNotReadMsg(Utilisateur utilisateur) {
		return afficherUserMessagesJPA(utilisateur).stream().filter(m-> m.getisRead()== false).count();
	}
	
	public void delete(MessageRecu msgrecu) {
		messageRecuRepo.delete(msgrecu);
	}
}
