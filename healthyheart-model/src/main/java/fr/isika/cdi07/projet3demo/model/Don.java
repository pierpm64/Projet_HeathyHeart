package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Don  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_don")
	protected Long idDon;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	protected Date date;
	
	@OneToOne
	protected ParticipationProjet participationProjet;

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

	public Long getIdDon() {
		return idDon;
	}

	public void setIdDon(Long idDon) {
		this.idDon = idDon;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Don [idDon=");
		builder.append(idDon);
		builder.append(", date=");
		builder.append(date);
		builder.append(", participationProjet=");
		builder.append(participationProjet.getTypeParticipation());
		builder.append("]");
		return builder.toString();
	}
	
	public Don withDate(Date date) {
		this.date = date;
		return this;
	}
	
	public Don withParticipationProjet(ParticipationProjet pp) {
		this.participationProjet = pp;
		return this;
	}


	
}
