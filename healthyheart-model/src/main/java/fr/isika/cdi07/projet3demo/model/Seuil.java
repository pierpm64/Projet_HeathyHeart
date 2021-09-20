package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seuil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_seuil")
	private Long idSeuil;
	
	@Column(nullable = false)
	private Double montant;
	
	@Column(nullable = false)
	private Long quantite;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeParticipation typeParticipation;
	
	public Seuil() {
		this.montant = 0.0;
		this.quantite = 0L;
		
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

	public TypeParticipation getTypeParticipation() {
		return typeParticipation;
	}

	public void setTypeParticipation(TypeParticipation typeParticipation) {
		this.typeParticipation = typeParticipation;
	}

	public Long getIdSeuil() {
		return idSeuil;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seuil [idSeuil=");
		builder.append(idSeuil);
		builder.append(", montant=");
		builder.append(montant);
		builder.append(", quantite=");
		builder.append(quantite);
		builder.append(", typeParticipation=");
		builder.append(typeParticipation);
		builder.append("]");
		return builder.toString();
	}
	
	

}
