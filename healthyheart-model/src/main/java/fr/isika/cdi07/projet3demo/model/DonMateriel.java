package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity 
@PrimaryKeyJoinColumn(name = "id_don")
public class DonMateriel extends Don {
	
	@Column(nullable = false)
	private String libelle;
	
	@Column(nullable = false)
	private Double montant;
	
	@Column(nullable = false)
	private Long quantite;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DonMateriel [libelle=");
		builder.append(libelle);
		builder.append(", montant=");
		builder.append(montant);
		builder.append(", quantite=");
		builder.append(quantite);
		builder.append("]");
		return builder.toString();
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Long getQuantite() {
		return quantite;
	}

	public void setQuantite(Long quantite) {
		this.quantite = quantite;
	}
	
	

}
