package fr.isika.cdi07.projet3demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.cdi07.projet3demo.model.MessageRecu;
import fr.isika.cdi07.projet3demo.model.Utilisateur;

public interface MessageRecuRepository extends JpaRepository<MessageRecu, Long>{

	public List<MessageRecu> getByMessageInterne(Long idMsg);
	
	public List<MessageRecu> getByUtilisateur(Utilisateur utilisateur);
	
}
