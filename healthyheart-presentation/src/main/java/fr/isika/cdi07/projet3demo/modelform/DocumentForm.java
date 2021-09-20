package fr.isika.cdi07.projet3demo.modelform;

import org.springframework.web.multipart.MultipartFile;

import fr.isika.cdi07.projet3demo.model.Document;
import fr.isika.cdi07.projet3demo.model.Projet;

public class DocumentForm {
	
	private Document document;
	
	private Projet projet;
	
	private MultipartFile  image;
	
	private String libelImage;
	
	private Long image1;
	
	private Long image2;
	
	private Long image3;
	

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

	public String getLibelImage() {
		return libelImage;
	}

	public void setLibelImage(String libelImage) {
		this.libelImage = libelImage;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	
	
}
