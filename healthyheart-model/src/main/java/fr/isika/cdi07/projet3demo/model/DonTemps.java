package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity 
@PrimaryKeyJoinColumn(name = "id_don")
public class DonTemps extends Don {
	
	@Column(nullable = false, name = "nb_heures")
	private Integer nbHeures;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DonTemps [nbHeures=");
		builder.append(nbHeures);
		builder.append("]");
		return builder.toString();
	}

	public Integer getNbHeures() {
		return nbHeures;
	}

	public void setNbHeures(Integer nbHeures) {
		this.nbHeures = nbHeures;
	}
	
	
}
