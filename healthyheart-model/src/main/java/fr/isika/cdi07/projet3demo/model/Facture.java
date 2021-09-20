package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Facture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_facture")
	private Long idFacture;
	
	@Column(nullable = false)
	private String libelle;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	
	@OneToOne
	private ParticipationProjet participationProjet;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Facture [idFacture=");
		builder.append(idFacture);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
	public Facture withLibelle(String libelle) {
		this.libelle = libelle;
		return this;
	}
	
	public Facture withDate(Date date) {
		this.date = date;
		return this;
	}
	
	public Facture withParticipationProjet(ParticipationProjet pp) {
		this.participationProjet = pp;
		return this;
	}

	public Long getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(Long idFacture) {
		this.idFacture = idFacture;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ParticipationProjet getParticipationProjet() {
		return participationProjet;
	}

	public void setParticipationProjet(ParticipationProjet participationProjet) {
		this.participationProjet = participationProjet;
	}

}
