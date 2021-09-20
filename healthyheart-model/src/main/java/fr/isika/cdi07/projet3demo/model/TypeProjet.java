package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class TypeProjet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_type_projet")
	private Long idTypeProjet;
	
	@Column(nullable = false)
	private String libelle;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TypeProjet [idTypeProjet=");
		builder.append(idTypeProjet);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
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


	public Long getIdTypeProjet() {
		return idTypeProjet;
	}
	
	public void setIdTypeProjet(Long idTypeProjet) {
		this.idTypeProjet = idTypeProjet;
	}
	
}
