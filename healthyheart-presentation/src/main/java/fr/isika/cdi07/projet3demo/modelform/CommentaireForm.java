package fr.isika.cdi07.projet3demo.modelform;

import fr.isika.cdi07.projet3demo.model.Role;

public class CommentaireForm {
	
	private Long idProjet;	
	private Role role;
	private String libelle;	
	private String message;
	private boolean allowedToComment;
	
	
	public CommentaireForm() {
	}

	public CommentaireForm(Long idProjet) {
		this.idProjet = idProjet;
		this.allowedToComment = false;
		this.libelle = "default";
	}

	public Long getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(Long idProjet) {
		this.idProjet = idProjet;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAllowedToComment() {
		return allowedToComment;
	}

	public void setAllowedToComment(boolean allowedToComment) {
		this.allowedToComment = allowedToComment;
	}


}
