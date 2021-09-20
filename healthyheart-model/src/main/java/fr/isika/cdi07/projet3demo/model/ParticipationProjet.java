package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class ParticipationProjet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_participation")
	private Long idParticipation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name="type_participation")
	private TypeParticipation typeParticipation;
	
	@Column(name="is_anonyme")
	private boolean isAnonyme;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatutDon statutDon;
	
	@ManyToOne
	private Role role;
	
	@ManyToOne
	private Projet projet;
	
	@OneToOne
	private Facture facture;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TypeParticipation getTypeParticipation() {
		return typeParticipation;
	}

	public void setTypeParticipation(TypeParticipation typeParticipation) {
		this.typeParticipation = typeParticipation;
	}

	public boolean isAnonyme() {
		return isAnonyme;
	}

	public void setIdParticipation(Long idParticipation) {
		this.idParticipation = idParticipation;
	}

	public void setAnonyme(boolean isAnonyme) {
		this.isAnonyme = isAnonyme;
	}

	public StatutDon getStatutDon() {
		return statutDon;
	}

	public void setStatutDon(StatutDon statutDon) {
		this.statutDon = statutDon;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Long getIdParticipation() {
		return idParticipation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ParticipationProjet [idParticipation=");
		builder.append(idParticipation);
		builder.append(", date=");
		builder.append(date);
		builder.append(", typeParticipation=");
		builder.append(typeParticipation);
		builder.append(", isAnonyme=");
		builder.append(isAnonyme);
		builder.append(", statutDon=");
		builder.append(statutDon);
		builder.append(", role=");
		builder.append(role.getTypeRole());
		builder.append(", projet=");
		builder.append(projet.getTitre());
		builder.append(", facture=");
		builder.append(facture.getLibelle());
		builder.append("]");
		return builder.toString();
	}
	
	public ParticipationProjet withDate(Date date) {
		this.date = date;
		return this;
	}
	
	public ParticipationProjet withTypeParticipation(TypeParticipation type) {
		this.typeParticipation = type;
		return this;
	}
	
	public ParticipationProjet withIsAnonyme(boolean bool) {
		this.isAnonyme = bool;
		return this;
	}
	
	public ParticipationProjet withStatutDon(StatutDon statutDon) {
		this.statutDon = statutDon;
		return this;
	}
	
	public ParticipationProjet withFacture(Facture facture) {
		this.facture = facture;
		return this;
	}
	
	public ParticipationProjet withRole(Role role) {
		this.role = role;
		return this;
	}
	
	
}