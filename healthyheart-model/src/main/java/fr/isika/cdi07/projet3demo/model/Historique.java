package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
public class Historique {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String evenement;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name="etat_projet")
	private StatutProjet etatProjet; 
	
	@Column(nullable = false)
	private String libelle;
	
	@ManyToOne
	@JoinColumn(name="email")
	private Utilisateur acteur;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name="date_heure")
	private Date dateHeure;
	
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet projet;
	
	@OneToOne
	private Notification notification;
	
	
	public Historique() {
		
	}


	
	public String getEvenement() {
		return evenement;
	}



	public void setEvenement(String evenement) {
		this.evenement = evenement;
	}



	public StatutProjet getEtatProjet() {
		return etatProjet;
	}



	public void setEtatProjet(StatutProjet etatProjet) {
		this.etatProjet = etatProjet;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	public Utilisateur getActeur() {
		return acteur;
	}



	public void setActeur(Utilisateur acteur) {
		this.acteur = acteur;
	}



	public Date getDateHeure() {
		return dateHeure;
	}



	public void setDateHeure(Date dateHeure) {
		this.dateHeure = dateHeure;
	}



	public Projet getProjet() {
		return projet;
	}



	public void setProjet(Projet projet) {
		this.projet = projet;
	}



	public Notification getNotification() {
		return notification;
	}



	public void setNotification(Notification notification) {
		this.notification = notification;
	}



	public Long getId() {
		return id;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Historique [id=");
		builder.append(id);
		builder.append(", evenement=");
		builder.append(evenement);
		builder.append(", etatProjet=");
		builder.append(etatProjet);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", acteur=");
		builder.append(acteur);
		builder.append(", dateHeure=");
		builder.append(dateHeure);
		builder.append(", projet=");
		builder.append(projet.getTitre());
		builder.append(", notification=");
		builder.append(notification.getLibelle());
		builder.append("]");
		return builder.toString();
	}

	
}
