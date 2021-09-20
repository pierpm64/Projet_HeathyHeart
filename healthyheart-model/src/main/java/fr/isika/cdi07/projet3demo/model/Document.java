package fr.isika.cdi07.projet3demo.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_document")
	private Long idDocument;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeDocument typeDocument;
	
	@Lob
	@Column(nullable = false)
	private byte[] fichier;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeLibelleDoc libelle;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	
	
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet projet;


	public TypeDocument getTypeDocument() {
		return typeDocument;
	}


	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}


	public byte[] getFichier() {
		return fichier;
	}


	public void setFichier(byte[] fichier) {
		this.fichier = fichier;
	}


	public TypeLibelleDoc getLibelle() {
		return libelle;
	}


	public void setLibelle(TypeLibelleDoc libelle) {
		this.libelle = libelle;
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


	public Long getIdDocument() {
		return idDocument;
	}
	


	public void setIdDocument(Long idDocument) {
		this.idDocument = idDocument;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Document [idDocument=");
		builder.append(idDocument);
		builder.append(", typeDocument=");
		builder.append(typeDocument);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", date=");
		builder.append(date);
		builder.append(", projet=");
		builder.append(projet.getTitre());
		builder.append("]");
		return builder.toString();
	}
	

	
}
