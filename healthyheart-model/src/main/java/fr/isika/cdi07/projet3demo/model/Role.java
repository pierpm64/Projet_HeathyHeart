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
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_role")
	private Long idRole;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name="type_role")
	private TypeRole typeRole;
	
	@ManyToOne
	private Utilisateur utilisateur;

	public Role() {
	}

	public Role(TypeRole typeRole, Utilisateur utilisateur) {
		super();
		this.typeRole = typeRole;
		this.utilisateur = utilisateur;
	}

	public TypeRole getTypeRole() {
		return typeRole;
	}


	public void setTypeRole(TypeRole typeRole) {
		this.typeRole = typeRole;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Long getIdRole() {
		return idRole;
	}


	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [idRole=");
		builder.append(idRole);
		builder.append(", typeRole=");
		builder.append(typeRole);
		builder.append(", utilisateur=");
		builder.append(utilisateur.getEmail());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}
