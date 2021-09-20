package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity 
@PrimaryKeyJoinColumn(name = "id_don")
public class DonMonetaire extends Don {
	@Column(nullable = false)
	private Double montant;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DonMonetaire [montant=");
		builder.append(montant);
		builder.append("]");
		return builder.toString();
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	
}
