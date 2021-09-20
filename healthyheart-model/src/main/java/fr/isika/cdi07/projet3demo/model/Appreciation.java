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
public class Appreciation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAppreciation;
	
	@Column(nullable = false)
	private Integer note;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet projet;
	
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;

	public Appreciation() {
	}
	
	

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
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

	public Long getIdAppreciation() {
		return idAppreciation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Appreciation [idAppreciation=");
		builder.append(idAppreciation);
		builder.append(", note=");
		builder.append(note);
		builder.append(", date=");
		builder.append(date);
		builder.append(", projet=");
		builder.append(projet.getTitre());
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}
