package fr.isika.cdi07.projet3demo.modelform;

import fr.isika.cdi07.projet3demo.model.MessageInterne;

public class MessagerieForm {

	
	private MessageInterne message;
	private String listeDest;
	
	public MessagerieForm() {
	}

	public MessageInterne getMessage() {
		return message;
	}

	public void setMessage(MessageInterne message) {
		this.message = message;
	}

	public String getListeDest() {
		return listeDest;
	}

	public void setListeDest(String listeDest) {
		this.listeDest = listeDest;
	}
	
	
}
