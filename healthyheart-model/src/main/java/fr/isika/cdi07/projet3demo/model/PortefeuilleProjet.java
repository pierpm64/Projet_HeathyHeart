package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class PortefeuilleProjet {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_portefeuille")
	private Long idPorteFeuille;
	
	@Column(nullable = false)
	private String libelle;
	
	@ManyToOne
	private PorteurProjet porteurprojet;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public PorteurProjet getPorteurprojet() {
		return porteurprojet;
	}

	public void setPorteurprojet(PorteurProjet porteurprojet) {
		this.porteurprojet = porteurprojet;
	}

	public Long getIdPorteFeuille() {
		return idPorteFeuille;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PortefeuilleProjet [idPorteFeuille=");
		builder.append(idPorteFeuille);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", porteurprojet=");
		builder.append(porteurprojet.getRole().getUtilisateur().getEmail());
		builder.append("]");
		return builder.toString();
	}
	
}
