package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class PorteurProjet {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_porteur_projet")
	private Long idPorteurProjet;
	
	@Column(nullable = false)
	private String libelle;
	
	@Column(nullable = false)
	private String iban;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name="type_porteur_projet")
	private TypePorteur typePorteur;
	
	
	@ManyToOne
	private Role role;
	

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public TypePorteur getTypePorteur() {
		return typePorteur;
	}

	public void setTypePorteur(TypePorteur typePorteur) {
		this.typePorteur = typePorteur;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public void setIdPorteurProjet(Long idPorteurProjet) {
		this.idPorteurProjet = idPorteurProjet;
	}

	public Long getIdPorteurProjet() {
		return idPorteurProjet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PorteurProjet [idPorteurProjet=");
		builder.append(idPorteurProjet);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", iban=");
		builder.append(iban);
		builder.append(", typePorteur=");
		builder.append(typePorteur);
		builder.append("]");
		return builder.toString();
	}
	
	
	


}
