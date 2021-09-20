package fr.isika.cdi07.projet3demo.modelform;

import fr.isika.cdi07.projet3demo.model.Projet;

public class ProjetDocumentForm {

	private Projet projet;
	private Long idImage;
	
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public Long getIdImage() {
		return idImage;
	}
	public void setIdImage(Long idImage) {
		this.idImage = idImage;
	}
	
	
}
