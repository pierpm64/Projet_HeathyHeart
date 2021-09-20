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
public class Favori {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_favori")
	private Long idFavori;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet projet;
	
	@ManyToOne
	@JoinColumn(name="email")
	private Utilisateur utilisateur;

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

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Long getIdFavori() {
		return idFavori;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Favori [idFavori=");
		builder.append(idFavori);
		builder.append(", date=");
		builder.append(date);
		builder.append(", projet=");
		builder.append(projet.getTitre());
		builder.append(", utilisateur=");
		builder.append(utilisateur.getEmail());
		builder.append("]");
		return builder.toString();
	}
	

	
	
}
