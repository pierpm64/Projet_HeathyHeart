package fr.isika.cdi07.projet3demo.modelform;


import fr.isika.cdi07.projet3demo.model.Projet;

public class PresentationProjetForm {

	private Projet projet;
	
	private Long image1;
	
	private Long image2;
	
	private Long image3;
	
	private String choixUser;
	
	private String action;

	public String getChoixUser() {
		return choixUser;
	}

	public void setChoixUser(String choixUser) {
		this.choixUser = choixUser;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getImage1() {
		return image1;
	}

	public void setImage1(Long image1) {
		this.image1 = image1;
	}

	public Long getImage2() {
		return image2;
	}

	public void setImage2(Long image2) {
		this.image2 = image2;
	}

	public Long getImage3() {
		return image3;
	}

	public void setImage3(Long image3) {
		this.image3 = image3;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	
}
