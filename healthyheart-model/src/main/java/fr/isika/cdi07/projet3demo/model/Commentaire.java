package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Commentaire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_commentaire")
	private Long idCommentaire;
	
	@Column(nullable = false)
	private String libelle;
	
	@Column(nullable = false)
	private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet projet;
	
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Commentaire [idCommentaire=");
		builder.append(idCommentaire);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", message=");
		builder.append(message);
		builder.append(", date=");
		builder.append(date);
		builder.append(", projet=");
		builder.append(projet.getTitre());
		builder.append(", role=");
		builder.append(role.getTypeRole());
		builder.append("]");
		return builder.toString();
	}
	
	public Commentaire withLibelle(String libelle) {
		this.libelle = libelle;
		return this;
	}
	
	public Commentaire withMessage(String message) {
		this.message = message;
		return this;
	}
	
	public Commentaire withProjet(Projet projet) {
		this.projet = projet;
		return this;
	}
	
	public Commentaire withRole(Role role) {
		this.role = role;
		return this;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getIdCommentaire() {
		return idCommentaire;
	}

	
}
