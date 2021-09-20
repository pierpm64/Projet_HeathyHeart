package fr.isika.cdi07.projet3demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity			
public class Categorie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_categorie")
	private Long idCategorie;
	
	@ManyToOne
	@JoinColumn(name="id_territoire")
	private Territoire territoire;
	
	@ManyToOne
	@JoinColumn(name="id_type_projet")
	private TypeProjet typeProjet;

	public Categorie() {
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

	public Territoire getTerritoire() {
		return territoire;
	}

	public void setTerritoire(Territoire territoire) {
		this.territoire = territoire;
	}

	public TypeProjet getTypeProjet() {
		return typeProjet;
	}

	public void setTypeProjet(TypeProjet typeProjet) {
		this.typeProjet = typeProjet;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [idCategorie=");
		builder.append(idCategorie);
		builder.append(", territoire=");
		builder.append(territoire.getLibelle());
		builder.append(", typeProjet=");
		builder.append(typeProjet.getLibelle());
		builder.append("]");
		return builder.toString();
	}

	public Categorie withTerritoire(Territoire territoire2) {
		territoire = territoire2;
		return this;
	}

	public Categorie withTypeProjet(TypeProjet typeProjet2) {
		typeProjet = typeProjet2;
		return this;
	}

	
	
	
	
}
