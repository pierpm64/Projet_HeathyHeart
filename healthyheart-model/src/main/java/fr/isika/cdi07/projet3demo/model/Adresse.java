package fr.isika.cdi07.projet3demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Adresse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_adresse")
	private Long idAdresse;
	
	@Column(nullable = false)
	private String rue;
	
	@Column(name="code_postal", nullable = false)
	private String codePostal;
	
	@Column(nullable = false)
	private String pays;
	
	@Column(nullable = false)
	private String ville;
	
	@Column(nullable = false)
	private String libelle;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="email")
	private Utilisateur utilisateur;
	

	public Adresse() {
	}


	public String getRue() {
		return rue;
	}


	public void setRue(String rue) {
		this.rue = rue;
	}


	public String getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Long getIdAdresse() {
		return idAdresse;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adresse [idAdresse=");
		builder.append(idAdresse);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", codePostal=");
		builder.append(codePostal);
		builder.append(", pays=");
		builder.append(pays);
		builder.append(", ville=");
		builder.append(ville);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", utilisateur=");
		builder.append(utilisateur.getEmail());
		builder.append("]");
		return builder.toString();
	}
	
	

	


}
